package me.luligabi.hostile_neural_industrialization.common.block.machine.mono_loot_fabricator

import aztech.modern_industrialization.machines.recipe.MachineRecipe
import aztech.modern_industrialization.machines.recipe.ProxyableMachineRecipeType
import aztech.modern_industrialization.thirdparty.fabrictransfer.api.item.ItemVariant
import dev.shadowsoffire.hostilenetworks.data.DataModel
import dev.shadowsoffire.hostilenetworks.data.DataModelRegistry
import me.luligabi.hostile_neural_industrialization.common.HNI
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
        lootIndex: Int
    ): RecipeHolder<MachineRecipe> {

        val recipeBuilder = MIMachineRecipeBuilder(this, model.simCost / 2, 60 * 20).apply {
            addItemInput(model.predictionDrop.item, 1, 1f)
            addItemOutput(ItemVariant.of(outputLoot), outputLoot.count, 1f)
        }

        val recipe = (recipeBuilder.convert() as MachineRecipe).apply {
            conditions = listOf(LootIndexProcessCondition(lootIndex))
        }

        return RecipeHolder(id, recipe)
    }

    private fun getPredictionRecipes(): MutableList<RecipeHolder<MachineRecipe>> {

        val recipes = mutableListOf<RecipeHolder<MachineRecipe>>()

        for (model in DataModelRegistry.INSTANCE.values) {

            model.fabDrops.forEachIndexed { i, drop ->

                val entityId = BuiltInRegistries.ENTITY_TYPE.getKey(model.entity)
                val itemId = BuiltInRegistries.ITEM.getKey(drop.item)

                recipes.add(
                    generate(
                        ResourceLocation.parse("${HNI.ID}:${entityId.namespace}/${entityId.path}/${itemId.namespace}/${itemId.path}"),
                        model, drop, i
                    )
                )

            }

        }
        return recipes
    }

    override fun fillRecipeList(level: Level, recipeList: MutableList<RecipeHolder<MachineRecipe>>) {
        recipeList.addAll(getManagerRecipes(level))
        recipeList.addAll(getPredictionRecipes())
    }

}