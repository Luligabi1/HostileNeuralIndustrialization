package me.luligabi.hostile_neural_industrialization.common.block.machine.sim_chamber.electric

import aztech.modern_industrialization.machines.recipe.MachineRecipe
import aztech.modern_industrialization.thirdparty.fabrictransfer.api.item.ItemVariant
import dev.shadowsoffire.hostilenetworks.Hostile
import dev.shadowsoffire.hostilenetworks.data.DataModelInstance
import dev.shadowsoffire.hostilenetworks.data.ModelTier
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.machine.sim_chamber.AbstractSimChamberRecipeType
import me.luligabi.hostile_neural_industrialization.common.util.electricSimChamberCost
import me.luligabi.hostile_neural_industrialization.common.util.getDimensionFluid
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.RecipeHolder
import net.swedz.tesseract.neoforge.compat.mi.recipe.MIMachineRecipeBuilder

class ElectricSimChamberRecipeType(id: ResourceLocation): AbstractSimChamberRecipeType(id) {

    override val machineId = "electric_simulation_chamber"

    override fun generate(
        id: ResourceLocation,
        instance: DataModelInstance,
        tier: ModelTier
    ): RecipeHolder<MachineRecipe> {

        val recipeBuilder = MIMachineRecipeBuilder(
            this,
            instance.model.electricSimChamberCost,
            HNI.CONFIG.electricSimChamber().duration()
        ).apply {
            addItemInput(DataModelIngredient(instance.model, tier).toVanilla(), 1, 0f)
            addItemInput(Hostile.Items.PREDICTION_MATRIX.value(), 1, 1f)

            instance.model.getDimensionFluid(
                HNI.CONFIG.electricSimChamber().overworldFluidInputId(), HNI.CONFIG.electricSimChamber().overworldFluidInputAmount(), HNI.CONFIG.electricSimChamber().overworldFluidInputProbability().toFloat(),
                HNI.CONFIG.electricSimChamber().netherFluidInputId(), HNI.CONFIG.electricSimChamber().netherFluidInputAmount(), HNI.CONFIG.electricSimChamber().netherFluidInputProbability().toFloat(),
                HNI.CONFIG.electricSimChamber().theEndFluidInputId(), HNI.CONFIG.electricSimChamber().theEndFluidInputAmount(), HNI.CONFIG.electricSimChamber().theEndFluidInputProbability().toFloat(),
                HNI.CONFIG.electricSimChamber().twilightFluidInputId(), HNI.CONFIG.electricSimChamber().twilightFluidInputAmount(), HNI.CONFIG.electricSimChamber().twilightFluidInputProbability().toFloat()
            )?.let { addFluidInput(it.first, it.second, it.third) }

            val baseDrop = instance.model.baseDrop
            addItemOutput(ItemVariant.of(baseDrop), baseDrop.count, 1f)

            val predictionDrop = instance.model.predictionDrop
            addItemOutput(ItemVariant.of(predictionDrop), 1, tier.accuracy)

            instance.model.getDimensionFluid(
                HNI.CONFIG.electricSimChamber().overworldFluidOutputId(), HNI.CONFIG.electricSimChamber().overworldFluidOutputAmount(), HNI.CONFIG.electricSimChamber().overworldFluidOutputProbability().toFloat(),
                HNI.CONFIG.electricSimChamber().netherFluidOutputId(), HNI.CONFIG.electricSimChamber().netherFluidOutputAmount(), HNI.CONFIG.electricSimChamber().netherFluidOutputProbability().toFloat(),
                HNI.CONFIG.electricSimChamber().theEndFluidOutputId(), HNI.CONFIG.electricSimChamber().theEndFluidOutputAmount(), HNI.CONFIG.electricSimChamber().theEndFluidOutputProbability().toFloat(),
                HNI.CONFIG.electricSimChamber().twilightFluidOutputId(), HNI.CONFIG.electricSimChamber().twilightFluidOutputAmount(), HNI.CONFIG.electricSimChamber().twilightFluidOutputProbability().toFloat()
            )?.let { addFluidOutput(it.first, it.second, it.third) }
        }

        return RecipeHolder(id, recipeBuilder.convert() as MachineRecipe)
    }

    override fun generatesRuntime() = HNI.CONFIG.electricSimChamber().runtimeRecipes()

}