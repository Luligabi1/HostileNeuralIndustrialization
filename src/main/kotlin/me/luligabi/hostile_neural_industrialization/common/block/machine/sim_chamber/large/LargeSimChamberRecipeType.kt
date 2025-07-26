package me.luligabi.hostile_neural_industrialization.common.block.machine.sim_chamber.large

import aztech.modern_industrialization.machines.recipe.MachineRecipe
import aztech.modern_industrialization.thirdparty.fabrictransfer.api.item.ItemVariant
import dev.shadowsoffire.hostilenetworks.Hostile
import dev.shadowsoffire.hostilenetworks.data.DataModelInstance
import dev.shadowsoffire.hostilenetworks.data.ModelTier
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.machine.sim_chamber.AbstractSimChamberRecipeType
import me.luligabi.hostile_neural_industrialization.common.util.getDimensionFluid
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
            /*instance.model.largeSimChamberCost*/ 2,
            HNI.CONFIG.largeSimChamber().duration()
        ).apply {
            addItemInput(DataModelIngredient(instance.model, tier).toVanilla(), 1, 0f)
            addItemInput(Hostile.Items.PREDICTION_MATRIX.value(), HNI.CONFIG.largeSimChamber().matrixesPerRecipeAmount(), 1f)

            instance.model.getDimensionFluid(
                HNI.CONFIG.largeSimChamber().overworldFluidInputId(), HNI.CONFIG.largeSimChamber().overworldFluidInputAmount(), HNI.CONFIG.largeSimChamber().overworldFluidInputProbability().toFloat(),
                HNI.CONFIG.largeSimChamber().netherFluidInputId(), HNI.CONFIG.largeSimChamber().netherFluidInputAmount(), HNI.CONFIG.largeSimChamber().netherFluidInputProbability().toFloat(),
                HNI.CONFIG.largeSimChamber().theEndFluidInputId(), HNI.CONFIG.largeSimChamber().theEndFluidInputAmount(), HNI.CONFIG.largeSimChamber().theEndFluidInputProbability().toFloat(),
                HNI.CONFIG.largeSimChamber().twilightFluidInputId(), HNI.CONFIG.largeSimChamber().twilightFluidInputAmount(), HNI.CONFIG.largeSimChamber().twilightFluidInputProbability().toFloat()
            )?.let { addFluidInput(it.first, it.second, it.third) }
            
            addItemOutput(
                ItemVariant.of(instance.model.baseDrop),
                HNI.CONFIG.largeSimChamber().generalizedPredictionPerRecipeAmount(),
                1f
            )
            addItemOutput(
                ItemVariant.of(instance.model.predictionDrop),
                HNI.CONFIG.largeSimChamber().predictionPerRecipeAmount(),
                tier.accuracy
            )

            instance.model.getDimensionFluid(
                HNI.CONFIG.largeSimChamber().overworldFluidOutputId(), HNI.CONFIG.largeSimChamber().overworldFluidOutputAmount(), HNI.CONFIG.largeSimChamber().overworldFluidOutputProbability().toFloat(),
                HNI.CONFIG.largeSimChamber().netherFluidOutputId(), HNI.CONFIG.largeSimChamber().netherFluidOutputAmount(), HNI.CONFIG.largeSimChamber().netherFluidOutputProbability().toFloat(),
                HNI.CONFIG.largeSimChamber().theEndFluidOutputId(), HNI.CONFIG.largeSimChamber().theEndFluidOutputAmount(), HNI.CONFIG.largeSimChamber().theEndFluidOutputProbability().toFloat(),
                HNI.CONFIG.largeSimChamber().twilightFluidOutputId(), HNI.CONFIG.largeSimChamber().twilightFluidOutputAmount(), HNI.CONFIG.largeSimChamber().twilightFluidOutputProbability().toFloat()
            )?.let { addFluidOutput(it.first, it.second, it.third) }
        }

        return RecipeHolder(id, recipeBuilder.convert() as MachineRecipe)
    }

    override fun generatesRuntime() = HNI.CONFIG.largeSimChamber().runtimeRecipes()

}