package me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.mono.loot_selector

import aztech.modern_industrialization.machines.gui.GuiComponent
import me.luligabi.hostile_neural_industrialization.common.HNI
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack

class LootSelector {

    interface Behavior {

        fun handleClick(id: ResourceLocation)
    }

    class Server(
        val behavior: Behavior,
        private val selectedIdSupplier: () -> ResourceLocation?,
        private val lootListSupplier: () -> List<ItemStack>
    ): GuiComponent.Server<Data> {

        override fun copyData(): Data {
            return Data(selectedIdSupplier(), lootListSupplier())
        }

        override fun needsSync(cachedData: Data): Boolean {
            if (cachedData.selectedId != selectedIdSupplier()) return true

            cachedData.lootList.forEachIndexed { i, stack ->
                if (stack != lootListSupplier().getOrNull(i)) return true
            }

            return false
        }

        override fun writeInitialData(buf: RegistryFriendlyByteBuf) {
            buf.writeResourceLocation(selectedIdSupplier() ?: NONE)
            ItemStack.LIST_STREAM_CODEC.encode(buf, lootListSupplier())
        }

        override fun writeCurrentData(buf: RegistryFriendlyByteBuf) {
            writeInitialData(buf)
        }

        override fun getId() = ID

    }

    data class Data(val selectedId: ResourceLocation?, val lootList: List<ItemStack>)

    companion object {

        val ID: ResourceLocation = HNI.id("loot_selector")

        val NONE = HNI.id("none")

    }

}