package me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.mono.loot_selector

import aztech.modern_industrialization.inventory.ChangeListener
import aztech.modern_industrialization.inventory.ConfigurableItemStack
import aztech.modern_industrialization.machines.IComponent
import me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.mono.MonoLootFabricatorBlockEntity
import me.luligabi.hostile_neural_industrialization.common.misc.network.RefreshLootListPacket
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel


class LootSelectorComponent(val blockEntity: () -> MonoLootFabricatorBlockEntity): IComponent.ServerOnly {

    var selectedLootId: ResourceLocation? = null
    lateinit var inputStackListener: InputStackListener

    fun initInputStackListener(triggerOnChange: Boolean = true) {
        val input = blockEntity().inventory.itemInputs[0]

        val listener = InputStackListener(input, blockEntity)
        listener.listenAll(listOf(input), null)
        if (triggerOnChange) listener.onChange()
        inputStackListener = listener
    }


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

    inner class InputStackListener(
        private val stack: ConfigurableItemStack,
        private val blockEntity: () -> MonoLootFabricatorBlockEntity
    ) : ChangeListener() {

        private var lastVariant = stack.variant

        public override fun onChange() {
            if (blockEntity().level?.isClientSide == true) return

            if (!stack.variant.equals(lastVariant)) {
                (blockEntity().level as? ServerLevel)?.let { // FIXME this bullshit
                    RefreshLootListPacket.INSTANCE.broadcastToClients(it, blockEntity().blockPos, 8.0)
                }
            }
            lastVariant = stack.variant
        }

        override fun isValid(token: Any?) = true

    }

}