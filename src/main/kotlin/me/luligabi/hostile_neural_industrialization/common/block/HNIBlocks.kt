package me.luligabi.hostile_neural_industrialization.common.block

import com.google.common.collect.Sets
import me.luligabi.hostile_neural_industrialization.common.HNI
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.block.entity.BlockEntityType
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredRegister
import net.swedz.tesseract.neoforge.registry.holder.BlockHolder


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

//    object Registry {
//        val BLOCKS: DeferredRegister.Blocks = DeferredRegister.createBlocks(HNI.ID)
//        val BLOCK_ENTITIES: DeferredRegister<BlockEntityType<*>> =
//            DeferredRegister.create<BlockEntityType<*>>(Registries.BLOCK_ENTITY_TYPE, HNI.ID)
//        val HOLDERS: MutableSet<BlockHolder<*>> = Sets.newHashSet()
//
//        internal fun init(bus: IEventBus) {
//            BLOCKS.register(bus)
//            BLOCK_ENTITIES.register(bus)
//        }
//
//        fun include(holder: BlockHolder<*>) {
//            HOLDERS.add(holder)
//        }
//    }
//
//    fun init(bus: IEventBus) {
//        Registry.init(bus)
//    }
//
//    static
//    val MACHINE_CHAINER_RELAY: BlockHolder<Block> =
//        create<BlockType, ItemType>(
//            "machine_chainer_relay", "Machine Chainer Relay",
//            Function<BlockBehaviour.Properties, BlockType> { Block() },
//            BiFunction<Block?, Item.Properties?, ItemType> { block: Block?, properties: Item.Properties? ->
//                BlockItem(
//                    block,
//                    properties
//                )
//            }, EISortOrder.MACHINES
//        ).withProperties(
//            Consumer<BlockBehaviour.Properties> { p: BlockBehaviour.Properties ->
//                p.mapColor(MapColor.METAL).destroyTime(4f).requiresCorrectToolForDrops()
//            }).tag(TagHelper.getMiningLevelTag(1)).tag(EITags.Blocks.MACHINE_CHAINER_RELAY).withLootTable(
//            Function<BlockHolder<BlockType?>, Function<AccessibleBlockLootSubProvider, LootTable.Builder>> { block: BlockHolder<BlockType?>? ->
//                CommonLootTableBuilders.self(
//                    block
//                )
//            }).withModel(
//            Function<BlockHolder<BlockType?>, Consumer<BlockStateProvider>> { block: BlockHolder<BlockType?>? ->
//                CommonModelBuilders.blockstateOnly(
//                    block
//                )
//            }).register()
//
//    val STEEL_PLATED_BRICKS: BlockHolder<Block> = createSimple(
//        "steel_plated_bricks",
//        "Steel Plated Bricks",
//        EISortOrder.CASINGS,
//        MapColor.METAL,
//        5f,
//        6f
//    ).withModel { block: BlockHolder<Block?>? ->
//        CommonModelBuilders.blockCubeAll(
//            block
//        )
//    }.register()
//
//    fun values(): Set<BlockHolder<*>> {
//        return java.util.Set.copyOf(Registry.HOLDERS)
//    }
//
//    fun get(id: String): Block {
//        return Registry.HOLDERS.stream().filter { b: BlockHolder<*> -> b.identifier().id() == id }.findFirst()
//            .orElseThrow().get()
//    }
//
//    fun <BlockType : Block?> create(
//        id: String?, englishName: String?,
//        blockCreator: Function<BlockBehaviour.Properties?, BlockType>?
//    ): BlockHolder<BlockType> {
//        val holder = BlockHolder<BlockType>(
//            EI.id(id), englishName,
//            Registry.BLOCKS, blockCreator
//        )
//        Registry.include(holder)
//        return holder
//    }
//
//    fun <BlockType : Block?, ItemType : BlockItem?> create(
//        id: String?, englishName: String?,
//        blockCreator: Function<BlockBehaviour.Properties?, BlockType>?,
//        itemCreator: BiFunction<Block?, Item.Properties?, ItemType>?,
//        sortOrder: SortOrder?
//    ): BlockWithItemHolder<BlockType, ItemType> {
//        val holder = BlockWithItemHolder<BlockType, ItemType>(
//            EI.id(id), englishName,
//            Registry.BLOCKS, blockCreator,
//            EIItems.Registry.ITEMS, itemCreator
//        )
//        holder.item().sorted(sortOrder)
//        Registry.include(holder)
//        EIItems.Registry.include(holder.item())
//        return holder
//    }
//
//    fun createSimple(
//        id: String?,
//        englishName: String?,
//        sortOrder: SortOrder?,
//        mapColor: MapColor,
//        destroyTime: Float,
//        explosionResistance: Float
//    ): BlockHolder<Block> {
//        return create<BlockType, ItemType>(
//            id, englishName,
//            Function<BlockBehaviour.Properties, BlockType> { Block() },
//            BiFunction<Block?, Item.Properties?, ItemType> { block: Block?, properties: Item.Properties? ->
//                BlockItem(
//                    block,
//                    properties
//                )
//            }, sortOrder
//        )
//            .withProperties(Consumer<BlockBehaviour.Properties> { p: BlockBehaviour.Properties ->
//                p.mapColor(mapColor).destroyTime(destroyTime).explosionResistance(explosionResistance)
//                    .requiresCorrectToolForDrops()
//            })
//            .tag(TagHelper.getMiningLevelTag(1))
//            .withLootTable(Function<BlockHolder<BlockType?>, Function<AccessibleBlockLootSubProvider, LootTable.Builder>> { block: BlockHolder<BlockType?>? ->
//                CommonLootTableBuilders.self(
//                    block
//                )
//            })
//    }



}