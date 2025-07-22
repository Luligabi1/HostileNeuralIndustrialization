package me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.large

import aztech.modern_industrialization.machines.recipe.MachineRecipe
import aztech.modern_industrialization.machines.recipe.ProxyableMachineRecipeType
import aztech.modern_industrialization.thirdparty.fabrictransfer.api.item.ItemVariant
import dev.shadowsoffire.hostilenetworks.data.DataModel
import dev.shadowsoffire.hostilenetworks.data.DataModelRegistry
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.PredictionIngredient
import me.luligabi.hostile_neural_industrialization.common.util.largeLootFabricatorCost
import me.luligabi.hostile_neural_industrialization.mixin.DataModelRegistryAccessor
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

        val recipeBuilder = MIMachineRecipeBuilder(
            this,
            model.largeLootFabricatorCost,
            HNI.CONFIG.largeLootFabricator().duration()
        ).apply {

            val baseInputAmount = HNI.CONFIG.largeLootFabricator().basePredictionAmount()
            val bonusInputAmount = (model.fabDrops.size / HNI.CONFIG.largeLootFabricator().bonusPredictionAmount()) - 1

            addItemInput(PredictionIngredient(model).toVanilla(), baseInputAmount + bonusInputAmount, 1f)

            val outputProbability = HNI.CONFIG.largeLootFabricator().outputProbability().toFloat()
            model.fabDrops.forEach {
                val outputAmount = (it.count * HNI.CONFIG.largeLootFabricator().outputAmountMultiplier()).toInt().coerceAtMost(64)
                if (outputAmount > 0) addItemOutput(ItemVariant.of(it), outputAmount, outputProbability)
            }

        }

        return RecipeHolder(id, recipeBuilder.convert() as MachineRecipe)
    }

    private fun getPredictionRecipes(): MutableList<RecipeHolder<MachineRecipe>> {
        if ((DataModelRegistry.INSTANCE as DataModelRegistryAccessor).modelsByType.isEmpty()) return mutableListOf()

        val recipes = mutableListOf<RecipeHolder<MachineRecipe>>()
        for (model in DataModelRegistry.INSTANCE.values) {

            if (model.fabDrops.size < HNI.CONFIG.largeLootFabricator().minimumLootForRecipe()) continue

            val entityId = BuiltInRegistries.ENTITY_TYPE.getKey(model.entity)

            recipes.add(
                generate(
                    ResourceLocation.parse("${HNI.ID}:large_loot_fabricator/${entityId.namespace}/${entityId.path}"),
                    model
                )
            )

        }
        return recipes
    }

    override fun fillRecipeList(level: Level, recipeList: MutableList<RecipeHolder<MachineRecipe>>) {
        recipeList.addAll(getManagerRecipes(level))
        if (HNI.CONFIG.largeLootFabricator().runtimeRecipes()) {
            recipeList.addAll(getPredictionRecipes())
        }
    }

}