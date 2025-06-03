package me.luligabi.hostile_neural_industrialization.common.item

import com.google.common.collect.Sets
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.HNICreativeTab
import net.minecraft.world.item.Item
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredRegister
import net.swedz.tesseract.neoforge.registry.SortOrder
import net.swedz.tesseract.neoforge.registry.common.CommonModelBuilders
import net.swedz.tesseract.neoforge.registry.holder.ItemHolder
import java.util.function.Function

@Suppress("MemberVisibilityCanBePrivate")
object HNIItems {

    object Registry {

        val ITEMS: DeferredRegister.Items = DeferredRegister.createItems(HNI.ID)
        val HOLDERS: MutableSet<ItemHolder<*>> = Sets.newHashSet()

        internal fun init(bus: IEventBus) {
            ITEMS.register(bus)
        }

        fun include(holder: ItemHolder<*>) {
            HOLDERS.add(holder)
        }

    }

    fun init(bus: IEventBus) {
        Registry.init(bus)
    }

    fun values(): Set<ItemHolder<*>> = java.util.Set.copyOf(Registry.HOLDERS)


    val GUIDEBOOK = create("guidebook", "HNI Guidebook", ::HNIGuidebookItem, HNICreativeTab.Order.GUIDEBOOK)
        .withModelBuilder(CommonModelBuilders::generated)
        .register()


    private fun <T : Item> create(id: String, englishName: String, creator: Function<Item.Properties, T>, sortOrder: SortOrder): ItemHolder<T> {
        val holder = ItemHolder(HNI.id(id), englishName, Registry.ITEMS, creator).sorted(sortOrder)
        Registry.include(holder)
        return holder
    }

}