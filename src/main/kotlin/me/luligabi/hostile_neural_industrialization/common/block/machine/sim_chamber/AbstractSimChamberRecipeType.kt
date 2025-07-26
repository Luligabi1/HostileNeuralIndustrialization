package me.luligabi.hostile_neural_industrialization.common.block.machine.sim_chamber

import aztech.modern_industrialization.machines.recipe.MachineRecipe
import aztech.modern_industrialization.machines.recipe.ProxyableMachineRecipeType
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.shadowsoffire.hostilenetworks.Hostile
import dev.shadowsoffire.hostilenetworks.data.*
import dev.shadowsoffire.hostilenetworks.item.DataModelItem
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.misc.HNIIngredients
import me.luligabi.hostile_neural_industrialization.mixin.DataModelRegistryAccessor
import me.luligabi.hostile_neural_industrialization.mixin.ModelTierRegistryAccessor
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeHolder
import net.minecraft.world.level.Level
import net.neoforged.neoforge.common.crafting.ICustomIngredient
import java.util.stream.Stream
import kotlin.jvm.optionals.getOrNull

abstract class AbstractSimChamberRecipeType (id: ResourceLocation): ProxyableMachineRecipeType(id) {

    abstract val machineId: String

    abstract fun generate(id: ResourceLocation, instance: DataModelInstance, tier: ModelTier): RecipeHolder<MachineRecipe>

    abstract fun generatesRuntime(): Boolean

    fun getModelRecipes(): MutableList<RecipeHolder<MachineRecipe>> {
        if ((ModelTierRegistry.INSTANCE as ModelTierRegistryAccessor).sorted.isEmpty()) return mutableListOf()
        if ((DataModelRegistry.INSTANCE as DataModelRegistryAccessor).modelsByType.isEmpty()) return mutableListOf()

        val recipes = mutableListOf<RecipeHolder<MachineRecipe>>()

        for (model in DataModelRegistry.INSTANCE.values) {

            val entityId = BuiltInRegistries.ENTITY_TYPE.getKey(model.entity).let {
                "${it.namespace}/${it.path}"
            }

            for (tier in ModelTierRegistry.INSTANCE.values) {
                if (!tier.canSim) continue

                val stack = ItemStack(Hostile.Items.DATA_MODEL.value()).apply {
                    DataModelItem.setStoredModel(this, model)
                    DataModelItem.setData(this, tier.requiredData)
                }

                val tierId = ModelTierRegistry.INSTANCE.getKey(tier)!!.let {
                    "${it.namespace}/${it.path}"
                }

                recipes.add(
                    generate(
                        ResourceLocation.parse("${HNI.ID}:/${machineId}/$entityId/$tierId"),
                        DataModelInstance(stack, 0),
                        tier
                    )
                )

            }

        }
        return recipes
    }

    override fun fillRecipeList(level: Level, recipeList: MutableList<RecipeHolder<MachineRecipe>>) {
        recipeList.addAll(getManagerRecipes(level))
        if (generatesRuntime()) {
            recipeList.addAll(getModelRecipes())
        }
    }


    class DataModelIngredient(
        private val modelId: ResourceLocation,
        private val tierId: ResourceLocation,
        private val allowHigherTiers: Boolean = false
    ): ICustomIngredient {

        constructor(model: DataModel, tier: ModelTier, allowHigherTiers: Boolean = false): this(
            DataModelRegistry.INSTANCE.getKey(model) ?: throw IllegalArgumentException("Data Model $model not found!"),
            ModelTierRegistry.INSTANCE.getKey(tier) ?: throw IllegalArgumentException("Model Tier $tier not found!"),
            allowHigherTiers
        )

        private val model by lazy {
            DataModelRegistry.INSTANCE.getValue(modelId) ?: throw IllegalArgumentException("Data Model $modelId not found!")
        }

        private val tier by lazy {
            ModelTierRegistry.INSTANCE.getValue(tierId) ?: throw IllegalArgumentException("Model Tier $tierId not found!")
        }

        override fun test(stack: ItemStack): Boolean {
            val model = stack.get(Hostile.Components.DATA_MODEL)?.get() ?: return false
            if (model != this.model) return false

            val data = stack.get(Hostile.Components.DATA) ?: return false
            val tier = ModelTierRegistry.getByData(model, data).asHolder().optional.getOrNull() ?: return false

            if (!allowHigherTiers) {
                return tier == this.tier
            }

            return getTierIndex(tier) >= getTierIndex(this.tier)
        }

        private fun getTierIndex(tier: ModelTier): Int {
            return (ModelTierRegistry.INSTANCE as ModelTierRegistryAccessor).sorted.indexOf(tier)
        }

        override fun getItems(): Stream<ItemStack> {

            val modelStack = ItemStack(Hostile.Items.DATA_MODEL)
            modelStack.let {
                DataModelItem.setStoredModel(it, model)
                DataModelItem.setData(it, tier.requiredData)
            }

            return Stream.of(modelStack)
        }

        override fun isSimple() = false

        override fun getType() = HNIIngredients.DATA_MODEL.value()

        companion object {

            val CODEC: MapCodec<DataModelIngredient> = RecordCodecBuilder.mapCodec {
                it.group(
                    ResourceLocation.CODEC.fieldOf("model").forGetter(DataModelIngredient::modelId),
                    ResourceLocation.CODEC.fieldOf("tier").forGetter(DataModelIngredient::tierId),
                    Codec.BOOL.optionalFieldOf("allow_higher_tiers", false).forGetter(DataModelIngredient::allowHigherTiers),
                ).apply(it, ::DataModelIngredient)
            }

        }

    }

}