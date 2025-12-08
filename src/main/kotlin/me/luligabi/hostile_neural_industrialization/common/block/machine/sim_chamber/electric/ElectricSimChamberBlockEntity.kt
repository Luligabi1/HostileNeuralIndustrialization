package me.luligabi.hostile_neural_industrialization.common.block.machine.sim_chamber.electric

import aztech.modern_industrialization.api.machine.holder.CrafterComponentHolder
import aztech.modern_industrialization.api.machine.holder.EnergyComponentHolder
import aztech.modern_industrialization.inventory.ConfigurableFluidStack
import aztech.modern_industrialization.inventory.ConfigurableItemStack
import aztech.modern_industrialization.inventory.SlotPositions
import aztech.modern_industrialization.machines.BEP
import aztech.modern_industrialization.machines.blockentities.ElectricCraftingMachineBlockEntity
import aztech.modern_industrialization.machines.components.CrafterComponent
import aztech.modern_industrialization.machines.components.MachineInventoryComponent
import aztech.modern_industrialization.machines.gui.MachineGuiParameters
import aztech.modern_industrialization.machines.guicomponents.EnergyBar
import aztech.modern_industrialization.machines.guicomponents.ProgressBar
import aztech.modern_industrialization.machines.guicomponents.RecipeEfficiencyBar
import aztech.modern_industrialization.machines.init.MachineTier
import aztech.modern_industrialization.util.Tickable
import dev.shadowsoffire.hostilenetworks.Hostile
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.machine.HNIMachines
import me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.mono.MonoLootFabricatorBlockEntity
import me.luligabi.hostile_neural_industrialization.common.block.machine.sim_chamber.HNISimChamber

class ElectricSimChamberBlockEntity(
    bep: BEP
): ElectricCraftingMachineBlockEntity(
    bep,
    HNIMachines.RecipeTypes.ELECTRIC_SIM_CHAMBER,
    buildInventory(),
    MachineGuiParameters.Builder(HNI.id(MonoLootFabricatorBlockEntity.Companion.ID), true).build(),
    EnergyBar.Params(14, 35),
    ProgressBar.Params(79, 34, "compress"),
    RecipeEfficiencyBar.Params(38, 66),
    MachineTier.LV,
    3200
), EnergyComponentHolder, Tickable, CrafterComponentHolder, CrafterComponent.Behavior, HNISimChamber {

    companion object {

        const val ID = "electric_simulation_chamber"
        const val NAME = "Electric Simulation Chamber"

        private fun buildInventory(): MachineInventoryComponent {

            val itemInputs = listOf(ConfigurableItemStack.standardInputSlot(), ConfigurableItemStack.standardInputSlot())
            val itemOutputs = listOf(ConfigurableItemStack.standardOutputSlot(), ConfigurableItemStack.standardOutputSlot())

            val fluidInputs = listOf(ConfigurableFluidStack.standardInputSlot(16_000))
            val fluidOutputs = listOf(ConfigurableFluidStack.standardOutputSlot(16_000))

            val itemPositions = SlotPositions.Builder()
                .addSlots(57, 27, 1, 2) // input
                .addSlots(103, 27, 1, 2) // output
                .build()

            val fluidPositions = SlotPositions.Builder()
                .addSlot(39, 27) // input
                .addSlot(121, 27) // output
                .build()

            return MachineInventoryComponent(itemInputs, itemOutputs, fluidInputs, fluidOutputs, itemPositions, fluidPositions)
        }
    }

    override fun onCraft() {
        val input = inventory.itemStacks[0]
        if (!input.toStack().`is`(Hostile.Items.DATA_MODEL)) return

        inventory.itemStacks[0].setContent(getUpdatedModel(input))
    }
}