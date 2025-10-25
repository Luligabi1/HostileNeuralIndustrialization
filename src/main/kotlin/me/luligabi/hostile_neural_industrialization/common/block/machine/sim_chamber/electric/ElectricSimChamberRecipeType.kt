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
            HNI.config().electricSimChamber().duration()
        ).apply {
            addItemInput(DataModelIngredient(instance.model, tier).toVanilla(), 1, 0f)
            addItemInput(Hostile.Items.PREDICTION_MATRIX.value(), 1, 1f)

            instance.model.getDimensionFluid(
                HNI.config().electricSimChamber().overworldFluidInputId(), HNI.config().electricSimChamber().overworldFluidInputAmount(), HNI.config().electricSimChamber().overworldFluidInputProbability().toFloat(),
                HNI.config().electricSimChamber().netherFluidInputId(), HNI.config().electricSimChamber().netherFluidInputAmount(), HNI.config().electricSimChamber().netherFluidInputProbability().toFloat(),
                HNI.config().electricSimChamber().theEndFluidInputId(), HNI.config().electricSimChamber().theEndFluidInputAmount(), HNI.config().electricSimChamber().theEndFluidInputProbability().toFloat(),
                HNI.config().electricSimChamber().twilightFluidInputId(), HNI.config().electricSimChamber().twilightFluidInputAmount(), HNI.config().electricSimChamber().twilightFluidInputProbability().toFloat()
            )?.let { addFluidInput(it.first, it.second, it.third) }

            val baseDrop = instance.model.baseDrop
            addItemOutput(ItemVariant.of(baseDrop), baseDrop.count, 1f)

            val predictionDrop = instance.model.predictionDrop
            addItemOutput(ItemVariant.of(predictionDrop), 1, tier.accuracy)

            instance.model.getDimensionFluid(
                HNI.config().electricSimChamber().overworldFluidOutputId(), HNI.config().electricSimChamber().overworldFluidOutputAmount(), HNI.config().electricSimChamber().overworldFluidOutputProbability().toFloat(),
                HNI.config().electricSimChamber().netherFluidOutputId(), HNI.config().electricSimChamber().netherFluidOutputAmount(), HNI.config().electricSimChamber().netherFluidOutputProbability().toFloat(),
                HNI.config().electricSimChamber().theEndFluidOutputId(), HNI.config().electricSimChamber().theEndFluidOutputAmount(), HNI.config().electricSimChamber().theEndFluidOutputProbability().toFloat(),
                HNI.config().electricSimChamber().twilightFluidOutputId(), HNI.config().electricSimChamber().twilightFluidOutputAmount(), HNI.config().electricSimChamber().twilightFluidOutputProbability().toFloat()
            )?.let { addFluidOutput(it.first, it.second, it.third) }
        }

        return RecipeHolder(id, recipeBuilder.convert() as MachineRecipe)
    }

    override fun generatesRuntime() = HNI.config().electricSimChamber().runtimeRecipes()

}