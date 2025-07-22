package me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.mono

import aztech.modern_industrialization.inventory.ChangeListener
import aztech.modern_industrialization.inventory.ConfigurableItemStack
import dev.shadowsoffire.hostilenetworks.Hostile
import me.luligabi.hostile_neural_industrialization.common.network.RefreshLootListPacket
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.item.ItemStack

class MonoLootFabricatorInputConfigurableItemStack private constructor(): ConfigurableItemStack() {

    companion object {

        fun create(blockEntitySupplier: () -> MonoLootFabricatorBlockEntity): MonoLootFabricatorInputConfigurableItemStack {

            val stack = MonoLootFabricatorInputConfigurableItemStack().apply {
                playerInsert = true
                pipesInsert = true
            }

            stack.addListener(object : ChangeListener() {

                override fun onChange() {

                    val blockEntity = blockEntitySupplier()

                    if (blockEntity.level?.isClientSide == true) return

                    if (stack.amount == 0L) {
                        if (!blockEntity.isActiveComponent.isActive) { // current recipe will halt otherwise
                            blockEntity.lootSelector.selectedLootIndex = -1
                        }
                        blockEntity.inventory.itemStacks[1].disableMachineLock()
                    }
                    RefreshLootListPacket.INSTANCE.broadcastToClients((blockEntity.level as ServerLevel), blockEntity.blockPos, 10.0)


                }

                override fun isValid(token: Any?) = true

            }, null)

            return stack
        }

    }

    override fun isValid(stack: ItemStack): Boolean {
        return stack.has(Hostile.Components.DATA_MODEL) || super.isValid(stack)
    }

}