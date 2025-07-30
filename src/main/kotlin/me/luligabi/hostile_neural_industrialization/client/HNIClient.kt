package me.luligabi.hostile_neural_industrialization.client

import aztech.modern_industrialization.machines.GuiComponentsClient
import aztech.modern_industrialization.machines.MachineBlock
import aztech.modern_industrialization.machines.MachineBlockEntityRenderer
import aztech.modern_industrialization.machines.multiblocks.MultiblockMachineBER
import aztech.modern_industrialization.machines.multiblocks.MultiblockMachineBlockEntity
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.HNIBlocks
import me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.mono.loot_selector.LootSelector
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers
import net.minecraft.world.level.block.entity.BlockEntity
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.neoforge.common.NeoForge

@Mod(HNI.ID, dist = [Dist.CLIENT])
@EventBusSubscriber(value = [Dist.CLIENT], modid = HNI.ID, bus = EventBusSubscriber.Bus.MOD)
object HNIClient {

    init {
        GuiComponentsClient.register(LootSelector.ID, ::LootSelectorClient)
        NeoForge.EVENT_BUS.register(DelayedClientTask)
    }

    @Suppress("UNCHECKED_CAST")
    @SubscribeEvent
    private fun registerBlockEntityRenderers(event: FMLClientSetupEvent) {
        for (blockDef in HNIBlocks.Registry.BLOCKS.entries) {
            if (blockDef.get() !is MachineBlock) continue

            val blockEntity = (blockDef.get() as MachineBlock).getBlockEntityInstance()
            val renderer = when (blockEntity) {
                is MultiblockMachineBlockEntity -> BlockEntityRendererProvider { c -> MultiblockMachineBER(c) }
                else -> BlockEntityRendererProvider { c -> MachineBlockEntityRenderer(c) }
            } as BlockEntityRendererProvider<BlockEntity> // I hate generics with a passion

            BlockEntityRenderers.register(blockEntity.type, renderer)

        }
    }

}