package me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.mono

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
import dev.shadowsoffire.hostilenetworks.item.DataModelItem
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.machine.HNIMachines
import me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.mono.loot_selector.LootSelector
import me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.mono.loot_selector.LootSelectorComponent
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack

class MonoLootFabricatorBlockEntity(
    bep: BEP
): ElectricCraftingMachineBlockEntity(
    bep,
    HNIMachines.RecipeTypes.MONO_LOOT_FABRICATOR,
    buildInventory(),
    MachineGuiParameters.Builder(HNI.id(ID), true).backgroundHeight(184).build(),
    EnergyBar.Params(14, 44),
    ProgressBar.Params(60, 44, "compress"),
    RecipeEfficiencyBar.Params(38, 84),
    MachineTier.LV,
    3200
), EnergyComponentHolder, Tickable, CrafterComponentHolder, CrafterComponent.Behavior {

    companion object {

        const val ID = "mono_loot_fabricator"
        const val NAME = "Mono Loot Fabricator"

        private fun buildInventory(): MachineInventoryComponent {

            val itemInputs = listOf(ConfigurableItemStack.standardInputSlot())
            val itemOutputs = List(9) { ConfigurableItemStack.standardOutputSlot() }

            val fluidInputs = listOf(ConfigurableFluidStack.standardInputSlot(16_000))
            val fluidOutputs = listOf(ConfigurableFluidStack.standardOutputSlot(16_000))

            val itemPositions = SlotPositions.Builder()
                .addSlot(38, 36) // input
                .addSlots(84, 27, 3, 3) // output
                .build()

            val fluidPositions = SlotPositions.Builder()
                .addSlot(38, 54) // input
                .addSlot(138, 27) // output
                .build()

            return MachineInventoryComponent(itemInputs, itemOutputs, fluidInputs, fluidOutputs, itemPositions, fluidPositions)
        }
    }

    val lootSelector = LootSelectorComponent({ this })

    init {
        registerGuiComponent(LootSelector(
            object : LootSelector.Behavior {

                override fun handleClick(id: ResourceLocation) {
                    lootSelector.selectedLootId = id
                }

            },
            { lootSelector.selectedLootId },
            { getInputFabDrops() ?: emptyList() }
        ))

        registerComponents(lootSelector)
    }

    override fun onCraft() {
        if (inventory.itemStacks[0].isEmpty) return

        val drops = getInputFabDrops()?.map { it.item } ?: return
        if (!drops.contains(BuiltInRegistries.ITEM.get(lootSelector.selectedLootId))) {
            lootSelector.selectedLootId = null
        }

    }

    private fun getInputFabDrops(): List<ItemStack>? {
        val prediction = inventory.itemStacks[0].toStack()
        val model = DataModelItem.getStoredModel(prediction).optional

        return if (model.isPresent) model.get().fabDrops else null
    }

    private var inputListenerLoaded = false
    override fun tick() {
        if(level?.isClientSide == true) return

        // ugly hack due to listeners not working for machines placed before the current session... ugh
        if (!inputListenerLoaded) {
            lootSelector.initInputStackListener(false)
            inputListenerLoaded = true
        }

        val active = crafter.tickRecipe()
        isActiveComponent.updateActive(active, this)

        if (orientation.extractItems) {
            inventory.autoExtractItems(level, worldPosition, orientation.outputDirection)
        }
        if (orientation.extractFluids) {
            inventory.autoExtractFluids(level, worldPosition, orientation.outputDirection)
        }

        setChanged()
    }

}