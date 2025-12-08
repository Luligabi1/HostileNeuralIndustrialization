package me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.mono.loot_selector

import aztech.modern_industrialization.machines.gui.GuiComponentServer
import me.luligabi.hostile_neural_industrialization.common.HNI
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack

class LootSelector(
    val behavior: Behavior,
    private val selectedIdSupplier: () -> ResourceLocation?,
    private val lootListSupplier: () -> List<ItemStack>
): GuiComponentServer<LootSelector.Data, LootSelector.Data> {

    override fun getParams() = Data(selectedIdSupplier(), lootListSupplier())

    override fun extractData() = getParams()

    override fun getType() = TYPE


    companion object {

        val ID: ResourceLocation = HNI.id("loot_selector")

        val TYPE = GuiComponentServer.Type<Data, Data>(
            ID,
            Data.STREAM_CODEC,
            Data.STREAM_CODEC
        )

    }

    data class Data(val selectedId: ResourceLocation?, val lootList: List<ItemStack>) {

        override fun equals(other: Any?): Boolean {
            if (other !is Data) return false

            if (other.selectedId != selectedId) return false
            if (other.lootList != lootList) return false
            return true
        }

        companion object {
            private val NULLABLE_RL = object : StreamCodec<RegistryFriendlyByteBuf, ResourceLocation?> {
                    override fun decode(buf: RegistryFriendlyByteBuf): ResourceLocation? {
                        val present = buf.readBoolean()
                        return if (present) ResourceLocation.STREAM_CODEC.decode(buf) else null
                    }

                    override fun encode(buf: RegistryFriendlyByteBuf, value: ResourceLocation?) {
                        buf.writeBoolean(value != null)
                        if (value != null) {
                            ResourceLocation.STREAM_CODEC.encode(buf, value)
                        }
                    }
                }

            val STREAM_CODEC = StreamCodec.composite(
                NULLABLE_RL, Data::selectedId,
                ItemStack.LIST_STREAM_CODEC, Data::lootList,
                ::Data
            )
        }

    }

    interface Behavior {

        fun handleClick(id: ResourceLocation)
    }

}