package me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.large

import aztech.modern_industrialization.machines.recipe.MachineRecipe
import aztech.modern_industrialization.machines.recipe.ProxyableMachineRecipeType
import aztech.modern_industrialization.thirdparty.fabrictransfer.api.item.ItemVariant
import dev.shadowsoffire.hostilenetworks.data.DataModel
import dev.shadowsoffire.hostilenetworks.data.DataModelRegistry
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.PredictionIngredient
import me.luligabi.hostile_neural_industrialization.common.util.getDimensionFluid
import me.luligabi.hostile_neural_industrialization.common.util.isModelRegistryLoaded
import me.luligabi.hostile_neural_industrialization.common.util.largeLootFabricatorCost
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
            HNI.config().largeLootFabricator().duration()
        ).apply {

            val baseInputAmount = HNI.config().largeLootFabricator().basePredictionAmount()
            val bonusInputAmount = (model.fabDrops.size / HNI.config().largeLootFabricator().bonusPredictionAmount()) - 1
            addItemInput(PredictionIngredient(model).toVanilla(), (baseInputAmount + bonusInputAmount).coerceAtLeast(1), 1f)

            model.getDimensionFluid(
                HNI.config().largeLootFabricator().overworldFluidInputId(), HNI.config().largeLootFabricator().overworldFluidInputAmount(), HNI.config().largeLootFabricator().overworldFluidInputProbability().toFloat(),
                HNI.config().largeLootFabricator().netherFluidInputId(), HNI.config().largeLootFabricator().netherFluidInputAmount(), HNI.config().largeLootFabricator().netherFluidInputProbability().toFloat(),
                HNI.config().largeLootFabricator().theEndFluidInputId(), HNI.config().largeLootFabricator().theEndFluidInputAmount(), HNI.config().largeLootFabricator().theEndFluidInputProbability().toFloat(),
                HNI.config().largeLootFabricator().twilightFluidInputId(), HNI.config().largeLootFabricator().twilightFluidInputAmount(), HNI.config().largeLootFabricator().twilightFluidInputProbability().toFloat()
            )?.let { addFluidInput(it.first, it.second, it.third) }

            val outputProbability = HNI.config().largeLootFabricator().outputProbability().toFloat()
            model.fabDrops.forEach {
                val outputAmount = (it.count * HNI.config().largeLootFabricator().outputAmountMultiplier()).toInt().coerceAtMost(64)
                if (outputAmount > 0) addItemOutput(ItemVariant.of(it), outputAmount, outputProbability)
            }

            model.getDimensionFluid(
                HNI.config().largeLootFabricator().overworldFluidOutputId(), HNI.config().largeLootFabricator().overworldFluidOutputAmount(), HNI.config().largeLootFabricator().overworldFluidOutputProbability().toFloat(),
                HNI.config().largeLootFabricator().netherFluidOutputId(), HNI.config().largeLootFabricator().netherFluidOutputAmount(), HNI.config().largeLootFabricator().netherFluidOutputProbability().toFloat(),
                HNI.config().largeLootFabricator().theEndFluidOutputId(), HNI.config().largeLootFabricator().theEndFluidOutputAmount(), HNI.config().largeLootFabricator().theEndFluidOutputProbability().toFloat(),
                HNI.config().largeLootFabricator().twilightFluidOutputId(), HNI.config().largeLootFabricator().twilightFluidOutputAmount(), HNI.config().largeLootFabricator().twilightFluidOutputProbability().toFloat()
            )?.let { addFluidOutput(it.first, it.second, it.third) }
        }

        return RecipeHolder(id, recipeBuilder.convert() as MachineRecipe)
    }

    private fun getPredictionRecipes(): MutableList<RecipeHolder<MachineRecipe>> {
        if (!isModelRegistryLoaded()) return mutableListOf()

        val recipes = mutableListOf<RecipeHolder<MachineRecipe>>()
        for (model in DataModelRegistry.INSTANCE.values) {

            if (model.fabDrops.size < HNI.config().largeLootFabricator().minimumLootForRecipe()) continue

            val entityId = BuiltInRegistries.ENTITY_TYPE.getKey(model.entity)

            recipes.add(
                generate(
                    ResourceLocation.parse("${HNI.ID}:/large_loot_fabricator/${entityId.namespace}/${entityId.path}"),
                    model
                )
            )

        }
        return recipes
    }

    override fun fillRecipeList(level: Level, recipeList: MutableList<RecipeHolder<MachineRecipe>>) {
        recipeList.addAll(getManagerRecipes(level))
        if (HNI.config().largeLootFabricator().runtimeRecipes()) {
            recipeList.addAll(getPredictionRecipes())
        }
    }

}