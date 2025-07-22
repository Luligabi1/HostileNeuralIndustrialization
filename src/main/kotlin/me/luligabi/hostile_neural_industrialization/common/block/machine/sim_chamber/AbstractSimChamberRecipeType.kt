package me.luligabi.hostile_neural_industrialization.common.block.machine.sim_chamber

import aztech.modern_industrialization.machines.recipe.MachineRecipe
import aztech.modern_industrialization.machines.recipe.ProxyableMachineRecipeType
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.shadowsoffire.hostilenetworks.Hostile
import dev.shadowsoffire.hostilenetworks.data.DataModel
import dev.shadowsoffire.hostilenetworks.data.DataModelInstance
import dev.shadowsoffire.hostilenetworks.data.DataModelRegistry
import dev.shadowsoffire.hostilenetworks.data.ModelTier
import dev.shadowsoffire.hostilenetworks.data.ModelTierRegistry
import dev.shadowsoffire.hostilenetworks.item.DataModelItem
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.mixin.DataModelRegistryAccessor
import me.luligabi.hostile_neural_industrialization.mixin.ModelTierRegistryAccessor
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeHolder
import net.minecraft.world.level.Level
import net.neoforged.neoforge.common.crafting.ICustomIngredient
import net.neoforged.neoforge.common.crafting.IngredientType
import java.util.stream.Stream
import kotlin.jvm.optionals.getOrNull

abstract class AbstractSimChamberRecipeType (id: ResourceLocation): ProxyableMachineRecipeType(id) {

    abstract val machineId: String

    abstract fun generate(id: ResourceLocation, instance: DataModelInstance, tier: ModelTier): RecipeHolder<MachineRecipe>

    abstract fun generatesRuntime(): Boolean

    private fun getModelRecipes(): MutableList<RecipeHolder<MachineRecipe>> {
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
                        ResourceLocation.parse("${HNI.ID}:${machineId}/$entityId/$tierId"),
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


    class ModelTierIngredient(private val model: DataModel, private val tier: ModelTier): ICustomIngredient {

        override fun test(stack: ItemStack): Boolean {
            if (!stack.`is`(Hostile.Items.DATA_MODEL.value())) return false

            val model = stack.get(Hostile.Components.DATA_MODEL)?.get() ?: return false
            if (model != this.model) return false

            val data = stack.get(Hostile.Components.DATA) ?: return false
            val tier = ModelTierRegistry.getByData(model, data).asHolder().optional.getOrNull() ?: return false

            return tier == this.tier
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

        override fun getType() = INGREDIENT_TYPE

        private companion object {

            val CODEC: MapCodec<ModelTierIngredient> = RecordCodecBuilder.mapCodec {
                it.group(
                    DataModel.CODEC.fieldOf("model").forGetter(ModelTierIngredient::model),
                    ModelTier.CODEC.fieldOf("tier").forGetter(ModelTierIngredient::tier)
                ).apply(it, ::ModelTierIngredient)
            }

            val INGREDIENT_TYPE = IngredientType(CODEC)

        }

    }

}