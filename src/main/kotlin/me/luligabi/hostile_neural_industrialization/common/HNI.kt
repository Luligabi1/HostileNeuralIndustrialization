package me.luligabi.hostile_neural_industrialization.common

import me.luligabi.hostile_neural_industrialization.common.block.HNIBlocks
import me.luligabi.hostile_neural_industrialization.common.compat.guideme.HNIGuide
import me.luligabi.hostile_neural_industrialization.common.item.HNIItems
import me.luligabi.hostile_neural_industrialization.datagen.HNIDatagen
import net.minecraft.resources.ResourceLocation
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import net.neoforged.fml.config.ModConfig
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent

@Mod(HNI.ID)
class HNI(modEventBus: IEventBus, modContainer: ModContainer) {

    companion object {
        const val ID = "hostile_neural_industrialization"

        fun id(id: String) = ResourceLocation.fromNamespaceAndPath(ID, id)

    }

    init {
        modEventBus.addListener(::commonSetup)
        HNIItems.init(modEventBus)
        HNIBlocks.init(modEventBus)
        HNICreativeTab.init(modEventBus)

        HNIGuide

        modEventBus.register(HNIDatagen)
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC)
    }

    private fun commonSetup(event: FMLCommonSetupEvent) {
    }

}