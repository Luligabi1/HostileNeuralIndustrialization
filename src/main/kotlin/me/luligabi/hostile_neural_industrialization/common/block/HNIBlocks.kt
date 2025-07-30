package me.luligabi.hostile_neural_industrialization.common.block

import com.google.common.collect.Sets
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.item.HNIItems
import me.luligabi.hostile_neural_industrialization.common.misc.HNICreativeTab
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredRegister
import net.swedz.tesseract.neoforge.registry.SortOrder
import net.swedz.tesseract.neoforge.registry.common.CommonLootTableBuilders
import net.swedz.tesseract.neoforge.registry.common.CommonModelBuilders
import net.swedz.tesseract.neoforge.registry.holder.BlockHolder
import net.swedz.tesseract.neoforge.registry.holder.BlockWithItemHolder

object HNIBlocks {


    object Registry {
        val BLOCKS: DeferredRegister.Blocks = DeferredRegister.createBlocks(HNI.ID)
        val BLOCK_ENTITIES: DeferredRegister<BlockEntityType<*>> = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, HNI.ID)

        internal val HOLDERS: MutableSet<BlockHolder<*>> = Sets.newHashSet()

        internal fun init(bus: IEventBus) {
            BLOCKS.register(bus)
            BLOCK_ENTITIES.register(bus)
        }

        fun include(holder: BlockHolder<*>) {
            HOLDERS.add(holder)
        }

    }

    fun init(bus: IEventBus) {
        Registry.init(bus)
    }

    fun values(): Set<BlockHolder<*>> = java.util.Set.copyOf(Registry.HOLDERS)


    val PREDICTION_MACHINE_CASING = create("prediction_machine_casing", "Prediction Machine Casing", ::Block, ::BlockItem, HNICreativeTab.Order.PARTS)
        .withLootTable(CommonLootTableBuilders::self)
        .withModel(CommonModelBuilders::blockCubeAll)
        .register()


    fun <B: Block, I: BlockItem> create(id: String, englishName: String, blockCreator: (BlockBehaviour.Properties) -> B, itemCreator: (Block, Item.Properties) -> I, sortOrder: SortOrder): BlockWithItemHolder<B, I> {

        val holder = BlockWithItemHolder(
            HNI.id(id), englishName,
            Registry.BLOCKS, blockCreator,
            HNIItems.Registry.ITEMS, itemCreator
        )

        holder.item().sorted(sortOrder)
        Registry.include(holder)
        HNIItems.Registry.include(holder.item())
        return holder
    }

    private fun <T : Block> itemless(
        id: String, englishName: String, blockCreator: (BlockBehaviour.Properties) -> T): BlockHolder<T> {
        val holder = BlockHolder(HNI.id(id), englishName, Registry.BLOCKS, blockCreator)
        Registry.include(holder)
        return holder
    }
    
}