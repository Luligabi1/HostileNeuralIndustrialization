package me.luligabi.hostile_neural_industrialization.common.misc.network

import aztech.modern_industrialization.machines.gui.MachineMenuServer
import aztech.modern_industrialization.network.MIStreamCodecs
import me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.mono.loot_selector.LootSelector
import net.minecraft.network.codec.StreamCodec
import net.minecraft.resources.ResourceLocation
import net.swedz.tesseract.neoforge.packet.CustomPacket
import net.swedz.tesseract.neoforge.packet.PacketContext

data class SelectLootPacket(private val syncId: Int, private val id: ResourceLocation): CustomPacket {

    companion object {

        val STREAM_CODEC = StreamCodec.composite(
            MIStreamCodecs.BYTE, SelectLootPacket::syncId,
            ResourceLocation.STREAM_CODEC, SelectLootPacket::id,
            ::SelectLootPacket
        )

    }

    override fun handle(ctx: PacketContext) {
        ctx.assertServerbound()

        val menu = ctx.player.containerMenu
        if (menu.containerId == syncId && menu is MachineMenuServer) {
            val lootSelector = menu.blockEntity.guiComponents.get<LootSelector.Server>(LootSelector.ID)
            lootSelector.behavior.handleClick(id)
        }

    }

    override fun type() = HNIPackets.getType(this::class.java)
}