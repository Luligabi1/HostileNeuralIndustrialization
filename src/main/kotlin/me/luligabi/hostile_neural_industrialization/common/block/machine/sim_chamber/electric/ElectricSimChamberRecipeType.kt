package me.luligabi.hostile_neural_industrialization.common.block.machine.sim_chamber.electric

import aztech.modern_industrialization.machines.recipe.MachineRecipe
import aztech.modern_industrialization.thirdparty.fabrictransfer.api.item.ItemVariant
import dev.shadowsoffire.hostilenetworks.Hostile
import dev.shadowsoffire.hostilenetworks.data.DataModelInstance
import dev.shadowsoffire.hostilenetworks.data.ModelTier
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.machine.sim_chamber.AbstractSimChamberRecipeType
import me.luligabi.hostile_neural_industrialization.common.util.electricSimChamberCost
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
            addItemInput(ModelTierIngredient(instance.model, tier).toVanilla(), 1, 0f)
            addItemInput(Hostile.Items.PREDICTION_MATRIX.value(), 1, 1f)

            val baseDrop = instance.model.baseDrop
            addItemOutput(ItemVariant.of(baseDrop), baseDrop.count, 1f)

            val predictionDrop = instance.model.predictionDrop
            addItemOutput(ItemVariant.of(predictionDrop), 1, tier.accuracy)
        }

        return RecipeHolder(id, recipeBuilder.convert() as MachineRecipe)
    }

    override fun generatesRuntime() = HNI.CONFIG.electricSimChamber().runtimeRecipes()

}