package me.luligabi.hostile_neural_industrialization.common.misc.network

import aztech.modern_industrialization.machines.gui.MachineScreen
import me.luligabi.hostile_neural_industrialization.client.LootSelectorClient
import me.luligabi.hostile_neural_industrialization.mixin.MachineScreenAccessor
import net.minecraft.client.Minecraft
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.swedz.tesseract.neoforge.packet.CustomPacket
import net.swedz.tesseract.neoforge.packet.PacketContext

class RefreshLootListPacket private constructor(): CustomPacket {

    companion object {

        val INSTANCE = RefreshLootListPacket()

        val STREAM_CODEC = StreamCodec.unit<RegistryFriendlyByteBuf, RefreshLootListPacket>(INSTANCE)

    }

    override fun handle(ctx: PacketContext) {
        ctx.assertClientbound()

        (Minecraft.getInstance().screen as? MachineScreen)?.let { screen ->

            val lootSelector = ((screen as MachineScreenAccessor).renderers.find { it is LootSelectorClient.Renderer } as? LootSelectorClient.Renderer) ?: return
            lootSelector.refreshSelectionButtons()
        }

    }

    override fun type() = HNIPackets.getType(this::class.java)
}