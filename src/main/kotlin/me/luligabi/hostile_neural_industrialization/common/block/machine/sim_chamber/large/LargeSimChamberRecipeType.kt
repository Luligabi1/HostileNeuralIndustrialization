package me.luligabi.hostile_neural_industrialization.common.block.machine.sim_chamber.large

import aztech.modern_industrialization.machines.recipe.MachineRecipe
import aztech.modern_industrialization.thirdparty.fabrictransfer.api.item.ItemVariant
import dev.shadowsoffire.hostilenetworks.Hostile
import dev.shadowsoffire.hostilenetworks.data.DataModelInstance
import dev.shadowsoffire.hostilenetworks.data.ModelTier
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.machine.sim_chamber.AbstractSimChamberRecipeType
import me.luligabi.hostile_neural_industrialization.common.util.getDimensionFluid
import me.luligabi.hostile_neural_industrialization.common.util.largeSimChamberCost
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.RecipeHolder
import net.swedz.tesseract.neoforge.compat.mi.recipe.MIMachineRecipeBuilder

class LargeSimChamberRecipeType(id: ResourceLocation): AbstractSimChamberRecipeType(id) {

    override val machineId = "large_simulation_chamber"

    override fun generate(
        id: ResourceLocation,
        instance: DataModelInstance,
        tier: ModelTier
    ): RecipeHolder<MachineRecipe> {

        val recipeBuilder = MIMachineRecipeBuilder(
            this,
            instance.model.largeSimChamberCost,
            HNI.config().largeSimChamber().duration()
        ).apply {
            addItemInput(DataModelIngredient(instance.model, tier).toVanilla(), 1, 0f)
            addItemInput(Hostile.Items.PREDICTION_MATRIX.value(), HNI.config().largeSimChamber().matrixesPerRecipeAmount(), 1f)

            instance.model.getDimensionFluid(
                HNI.config().largeSimChamber().overworldFluidInputId(), HNI.config().largeSimChamber().overworldFluidInputAmount(), HNI.config().largeSimChamber().overworldFluidInputProbability().toFloat(),
                HNI.config().largeSimChamber().netherFluidInputId(), HNI.config().largeSimChamber().netherFluidInputAmount(), HNI.config().largeSimChamber().netherFluidInputProbability().toFloat(),
                HNI.config().largeSimChamber().theEndFluidInputId(), HNI.config().largeSimChamber().theEndFluidInputAmount(), HNI.config().largeSimChamber().theEndFluidInputProbability().toFloat(),
                HNI.config().largeSimChamber().twilightFluidInputId(), HNI.config().largeSimChamber().twilightFluidInputAmount(), HNI.config().largeSimChamber().twilightFluidInputProbability().toFloat()
            )?.let { addFluidInput(it.first, it.second, it.third) }
            
            addItemOutput(
                ItemVariant.of(instance.model.baseDrop),
                HNI.config().largeSimChamber().generalizedPredictionPerRecipeAmount(),
                1f
            )
            addItemOutput(
                ItemVariant.of(instance.model.predictionDrop),
                HNI.config().largeSimChamber().predictionPerRecipeAmount(),
                tier.accuracy
            )

            instance.model.getDimensionFluid(
                HNI.config().largeSimChamber().overworldFluidOutputId(), HNI.config().largeSimChamber().overworldFluidOutputAmount(), HNI.config().largeSimChamber().overworldFluidOutputProbability().toFloat(),
                HNI.config().largeSimChamber().netherFluidOutputId(), HNI.config().largeSimChamber().netherFluidOutputAmount(), HNI.config().largeSimChamber().netherFluidOutputProbability().toFloat(),
                HNI.config().largeSimChamber().theEndFluidOutputId(), HNI.config().largeSimChamber().theEndFluidOutputAmount(), HNI.config().largeSimChamber().theEndFluidOutputProbability().toFloat(),
                HNI.config().largeSimChamber().twilightFluidOutputId(), HNI.config().largeSimChamber().twilightFluidOutputAmount(), HNI.config().largeSimChamber().twilightFluidOutputProbability().toFloat()
            )?.let { addFluidOutput(it.first, it.second, it.third) }
        }

        return RecipeHolder(id, recipeBuilder.convert() as MachineRecipe)
    }

    override fun generatesRuntime() = HNI.config().largeSimChamber().runtimeRecipes()

}