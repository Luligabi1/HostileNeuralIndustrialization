package me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.mono

import aztech.modern_industrialization.inventory.ChangeListener
import aztech.modern_industrialization.inventory.ConfigurableItemStack
import me.luligabi.hostile_neural_industrialization.common.misc.network.RefreshLootListPacket
import net.minecraft.server.level.ServerLevel

class MonoLootFabricatorInputConfigurableItemStack(
    private val blockEntitySupplier: () -> MonoLootFabricatorBlockEntity,
    private val stack: ConfigurableItemStack
): ChangeListener() {

    override fun onChange() { // TODO PR way to check change's source slot?
        println("changed!")
        RefreshLootListPacket.INSTANCE.broadcastToClients(
            (blockEntitySupplier().level as ServerLevel),
            blockEntitySupplier().blockPos,
            10.0
        )
    }

    override fun isValid(token: Any?) = true
}