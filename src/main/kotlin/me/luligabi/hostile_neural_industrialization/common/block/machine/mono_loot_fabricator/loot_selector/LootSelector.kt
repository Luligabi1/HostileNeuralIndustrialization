package me.luligabi.hostile_neural_industrialization.common.block.machine.mono_loot_fabricator.loot_selector

import aztech.modern_industrialization.machines.gui.GuiComponent
import me.luligabi.hostile_neural_industrialization.common.HNI
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack

class LootSelector {

    class Server(
        private val selectedIndex: () -> Int,
        private val lootList: () -> List<ItemStack>
    ): GuiComponent.Server<Data> {

        override fun copyData(): Data {
            return Data(selectedIndex.invoke(), lootList.invoke())
        }

        override fun needsSync(cachedData: Data): Boolean {
            return cachedData.selectedIndex != selectedIndex.invoke() || cachedData.lootList != lootList.invoke()
        }

        override fun writeInitialData(buf: RegistryFriendlyByteBuf) {
            writeCurrentData(buf)
        }

        override fun writeCurrentData(buf: RegistryFriendlyByteBuf) {
            buf.writeInt(selectedIndex.invoke())
            ItemStack.LIST_STREAM_CODEC.encode(buf, lootList.invoke())
        }

        override fun getId() = ID

    }

    data class Data(val selectedIndex: Int, val lootList: List<ItemStack>)

    companion object {

        val ID: ResourceLocation = HNI.id("loot_selector")

    }

}