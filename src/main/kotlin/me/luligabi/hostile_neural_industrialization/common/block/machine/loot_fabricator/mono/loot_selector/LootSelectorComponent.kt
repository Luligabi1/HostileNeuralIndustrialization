package me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.mono.loot_selector

import aztech.modern_industrialization.machines.IComponent
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation

class LootSelectorComponent: IComponent.ServerOnly {

    var selectedLootId: ResourceLocation? = null

    override fun writeNbt(tag: CompoundTag, registries: HolderLookup.Provider) {

        if (selectedLootId == null) {
            tag.remove(SELECTED_LOOT_KEY)
            return
        }

        tag.putString(SELECTED_LOOT_KEY, selectedLootId.toString())
    }

    override fun readNbt(tag: CompoundTag, registries: HolderLookup.Provider, isUpgradingMachine: Boolean) {
        val id = tag.getString(SELECTED_LOOT_KEY)
        if (id.isNullOrBlank()) {
            selectedLootId = null
            return
        }

        selectedLootId = ResourceLocation.tryParse(id)
    }

    companion object {

        const val SELECTED_LOOT_KEY = "selectedLootId"

    }

}