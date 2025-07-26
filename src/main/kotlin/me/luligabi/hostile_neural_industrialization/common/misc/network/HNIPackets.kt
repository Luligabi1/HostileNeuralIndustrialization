package me.luligabi.hostile_neural_industrialization.common.misc.network

import me.luligabi.hostile_neural_industrialization.common.HNI
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent
import net.swedz.tesseract.neoforge.packet.CustomPacket
import net.swedz.tesseract.neoforge.packet.PacketRegistry


object HNIPackets {

    fun init(event: RegisterPayloadHandlersEvent) {
        create("select_loot", SelectLootPacket::class.java, SelectLootPacket.STREAM_CODEC)
        create("refresh_loot_list", RefreshLootListPacket::class.java, RefreshLootListPacket.STREAM_CODEC)

        REGISTRY.registerAll(event)
    }

    fun getType(packetClass: Class<out CustomPacket>) = REGISTRY.getType(packetClass)

    private fun <P : CustomPacket> create(
        id: String,
        packetClass: Class<P>,
        packetCodec: StreamCodec<in RegistryFriendlyByteBuf, P>
    ) {
        REGISTRY.create(id, packetClass, packetCodec)
    }

    private val REGISTRY = PacketRegistry.create<CustomPacket>(HNI.ID)
}