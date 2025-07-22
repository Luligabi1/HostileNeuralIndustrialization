package me.luligabi.hostile_neural_industrialization.common.block.machine.sim_chamber.large

import aztech.modern_industrialization.machines.recipe.MachineRecipe
import aztech.modern_industrialization.thirdparty.fabrictransfer.api.item.ItemVariant
import dev.shadowsoffire.hostilenetworks.Hostile
import dev.shadowsoffire.hostilenetworks.data.DataModel
import dev.shadowsoffire.hostilenetworks.data.DataModelInstance
import dev.shadowsoffire.hostilenetworks.data.ModelTier
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.machine.sim_chamber.AbstractSimChamberRecipeType
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

        val inputCount = HNI.CONFIG.largeSimChamber().inputPerRecipeAmount()
        val outputCount = HNI.CONFIG.largeSimChamber().outputPerRecipeAmount()

        val recipeBuilder = MIMachineRecipeBuilder(
            this,
            /*instance.model.largeSimChamberCost*/ 2,
            HNI.CONFIG.largeSimChamber().duration()
        ).apply {
            addItemInput(ModelTierIngredient(instance.model, tier).toVanilla(), 1, 0f)
            addItemInput(Hostile.Items.PREDICTION_MATRIX.value(), inputCount, 1f)

            val baseDrop = instance.model.baseDrop
            addItemOutput(ItemVariant.of(baseDrop), outputCount, 1f)

            val predictionDrop = instance.model.predictionDrop
            addItemOutput(ItemVariant.of(predictionDrop), outputCount, tier.accuracy)
        }

        return RecipeHolder(id, recipeBuilder.convert() as MachineRecipe)
    }

    override fun generatesRuntime() = HNI.CONFIG.largeSimChamber().runtimeRecipes()
}