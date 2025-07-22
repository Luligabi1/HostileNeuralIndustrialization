package me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.mono

import aztech.modern_industrialization.machines.recipe.MachineRecipe
import aztech.modern_industrialization.machines.recipe.ProxyableMachineRecipeType
import aztech.modern_industrialization.thirdparty.fabrictransfer.api.item.ItemVariant
import dev.shadowsoffire.hostilenetworks.data.DataModel
import dev.shadowsoffire.hostilenetworks.data.DataModelRegistry
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.PredictionIngredient
import me.luligabi.hostile_neural_industrialization.common.util.monoLootFabricatorCost
import me.luligabi.hostile_neural_industrialization.mixin.DataModelRegistryAccessor
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeHolder
import net.minecraft.world.level.Level
import net.swedz.tesseract.neoforge.compat.mi.recipe.MIMachineRecipeBuilder

class MonoLootFabricatorRecipeType(id: ResourceLocation): ProxyableMachineRecipeType(id) {

    private fun generate(
        id: ResourceLocation,
        model: DataModel,
        outputLoot: ItemStack,
        index: Int
    ): RecipeHolder<MachineRecipe> {

        val recipeBuilder = MIMachineRecipeBuilder(
            this,
            model.monoLootFabricatorCost,
            HNI.CONFIG.monoLootFabricator().duration()
        ).apply {
            addItemInput(PredictionIngredient(model).toVanilla(), 1, 0f)

            val outputAmount = (outputLoot.count * HNI.CONFIG.monoLootFabricator().outputAmountMultiplier()).toInt().coerceAtMost(64)
            if (outputAmount > 0) addItemOutput(ItemVariant.of(outputLoot), outputAmount, 1f)
        }

        val recipe = (recipeBuilder.convert() as MachineRecipe).apply {
            conditions = listOf(LootIndexProcessCondition(index))
        }

        return RecipeHolder(id, recipe)
    }

    private fun getPredictionRecipes(): MutableList<RecipeHolder<MachineRecipe>> {
        if ((DataModelRegistry.INSTANCE as DataModelRegistryAccessor).modelsByType.isEmpty()) return mutableListOf()

        val recipes = mutableListOf<RecipeHolder<MachineRecipe>>()
        for (model in DataModelRegistry.INSTANCE.values) {

            model.fabDrops.forEachIndexed { i, drop ->

                val entityId = BuiltInRegistries.ENTITY_TYPE.getKey(model.entity)
                val itemId = BuiltInRegistries.ITEM.getKey(drop.item)

                recipes.add(
                    generate(
                        ResourceLocation.parse("${HNI.ID}:mono_loot_fabricator/${entityId.namespace}/${entityId.path}/${itemId.namespace}/${itemId.path}"),
                        model, drop, i
                    )
                )

            }

        }
        return recipes
    }

    override fun fillRecipeList(level: Level, recipeList: MutableList<RecipeHolder<MachineRecipe>>) {
        recipeList.addAll(getManagerRecipes(level))
        if (HNI.CONFIG.monoLootFabricator().runtimeRecipes()) {
            recipeList.addAll(getPredictionRecipes())
        }
    }

}