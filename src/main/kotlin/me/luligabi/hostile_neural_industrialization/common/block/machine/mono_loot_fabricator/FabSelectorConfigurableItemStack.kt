package me.luligabi.hostile_neural_industrialization.common.block.machine.mono_loot_fabricator

import aztech.modern_industrialization.inventory.ConfigurableItemStack
import aztech.modern_industrialization.thirdparty.fabrictransfer.api.item.ItemVariant
import dev.shadowsoffire.hostilenetworks.Hostile

class FabSelectorConfigurableItemStack private constructor(private val blockEntitySupplier: () -> MonoLootFabricatorBlockEntity): ConfigurableItemStack() {

    companion object {

        fun create(blockEntitySupplier: () -> MonoLootFabricatorBlockEntity): FabSelectorConfigurableItemStack {

            val stack = FabSelectorConfigurableItemStack(blockEntitySupplier).apply {
                key = ItemVariant.of(Hostile.Items.FAB_DIRECTIVE.value())
                lockedInstance = Hostile.Items.FAB_DIRECTIVE.value()
                playerInsert = true
                playerLockable = false
                playerLocked = true
                machineLocked = true
                pipesInsert = false
            }

            return stack
        }

    }
//
//    override fun setAmount(amount: Long) {
//        super.setAmount(amount)
//        blockEntitySupplier().onFabChange(if (amount >= 1) true else false)
//    }

    override fun setKey(key: ItemVariant) {
        super.setKey(key)
        blockEntitySupplier().onFabChange(!key.isBlank)
    }

}