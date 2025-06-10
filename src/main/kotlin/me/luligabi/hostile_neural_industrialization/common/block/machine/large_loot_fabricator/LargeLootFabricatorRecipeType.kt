package me.luligabi.hostile_neural_industrialization.common.block.machine.large_loot_fabricator

import aztech.modern_industrialization.machines.recipe.MachineRecipe
import aztech.modern_industrialization.machines.recipe.ProxyableMachineRecipeType
import aztech.modern_industrialization.thirdparty.fabrictransfer.api.item.ItemVariant
import dev.shadowsoffire.hostilenetworks.data.DataModel
import dev.shadowsoffire.hostilenetworks.data.DataModelRegistry
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.util.PredictionIngredient
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.RecipeHolder
import net.minecraft.world.level.Level
import net.swedz.tesseract.neoforge.compat.mi.recipe.MIMachineRecipeBuilder


class LargeLootFabricatorRecipeType(id: ResourceLocation): ProxyableMachineRecipeType(id) {

    private fun generate(
        id: ResourceLocation,
        model: DataModel
    ): RecipeHolder<MachineRecipe> {

        val recipeBuilder = MIMachineRecipeBuilder(this, model.simCost / 10, 7 * 60 * 20).apply {
            addItemInput(PredictionIngredient(model).toVanilla(), 1, 1f)
            model.fabDrops.forEach {
                addItemOutput(ItemVariant.of(it), it.count, 1f)
            }

        }

        return RecipeHolder(id, recipeBuilder.convert() as MachineRecipe)
    }

    private fun getPredictionRecipes(): MutableList<RecipeHolder<MachineRecipe>> {

        val recipes = mutableListOf<RecipeHolder<MachineRecipe>>()

        for (model in DataModelRegistry.INSTANCE.values) {

            val entityId = BuiltInRegistries.ENTITY_TYPE.getKey(model.entity)

            recipes.add(
                generate(
                    ResourceLocation.parse("${HNI.ID}:${entityId.namespace}/${entityId.path}"),
                    model
                )
            )

        }
        return recipes
    }

    override fun fillRecipeList(level: Level, recipeList: MutableList<RecipeHolder<MachineRecipe>>) {
        recipeList.addAll(getManagerRecipes(level))
        recipeList.addAll(getPredictionRecipes())
    }

}