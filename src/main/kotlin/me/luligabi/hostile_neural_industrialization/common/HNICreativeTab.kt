package me.luligabi.hostile_neural_industrialization.common

import me.luligabi.hostile_neural_industrialization.common.block.machine.HNIMachines
import me.luligabi.hostile_neural_industrialization.common.block.machine.large_loot_fabricator.LargeLootFabricatorBlockEntity
import me.luligabi.hostile_neural_industrialization.common.item.HNIItems
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredRegister
import net.swedz.tesseract.neoforge.registry.SortOrder
import net.swedz.tesseract.neoforge.registry.holder.ItemHolder
import java.util.function.Supplier

object HNICreativeTab {

    private val CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HNI.ID)

    val CREATIVE_TAB = CREATIVE_MODE_TABS.register(HNI.ID, Supplier {

        CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.${HNI.ID}.${HNI.ID}"))
            .icon { HNIMachines.getMachineFromId(LargeLootFabricatorBlockEntity.ID).defaultInstance }
            .displayItems { _, output ->

                val compareBySortOrder = Comparator.comparing { obj: ItemHolder<*> -> obj.sortOrder() }
                val compareByName: Comparator<ItemHolder<*>> = Comparator.comparing { i -> i.identifier().id() }
                HNIItems.values().stream()
                    .sorted(compareBySortOrder.thenComparing(compareByName))
                    .forEach(output::accept)
            }
            .build()
    })

    fun init(bus: IEventBus) {
        CREATIVE_MODE_TABS.register(bus)
    }

    object Order {

        val GUIDEBOOK = SortOrder(0)
        val MACHINES = SortOrder(1)
        val PARTS = SortOrder(2)

    }

}