package me.luligabi.hostile_neural_industrialization.common.block.machine.mono_loot_fabricator.loot_selector

import aztech.modern_industrialization.machines.IComponent
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag

class LootSelectorComponent: IComponent.ServerOnly {

    var selectedLootIndex = -1

    override fun writeNbt(tag: CompoundTag, registries: HolderLookup.Provider) {
        tag.putInt("selectedLootIndex", selectedLootIndex)
    }

    override fun readNbt(tag: CompoundTag, registries: HolderLookup.Provider, isUpgradingMachine: Boolean) {
        selectedLootIndex = tag.getInt("selectedLootIndex")
    }
}