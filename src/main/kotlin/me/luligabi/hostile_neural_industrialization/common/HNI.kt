package me.luligabi.hostile_neural_industrialization.common

import me.luligabi.hostile_neural_industrialization.common.block.HNIBlocks
import me.luligabi.hostile_neural_industrialization.common.compat.guideme.HNIGuide
import me.luligabi.hostile_neural_industrialization.common.item.HNIItems
import me.luligabi.hostile_neural_industrialization.common.network.HNIPackets
import me.luligabi.hostile_neural_industrialization.datagen.HNIDatagen
import net.minecraft.resources.ResourceLocation
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import net.neoforged.fml.config.ModConfig
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent
import net.swedz.tesseract.neoforge.config.ConfigManager

@Mod(HNI.ID)
class HNI(modEventBus: IEventBus, container: ModContainer) {

    companion object {
        const val ID = "hostile_neural_industrialization"

        fun id(id: String) = ResourceLocation.fromNamespaceAndPath(ID, id)

        lateinit var CONFIG: HNIConfig
            private set

    }

    init {
        setupConfig(modEventBus, container)

        HNIItems.init(modEventBus)
        HNIBlocks.init(modEventBus)
        HNICreativeTab.init(modEventBus)
        modEventBus.addListener(RegisterPayloadHandlersEvent::class.java, HNIPackets::init)

        HNIGuide

        modEventBus.register(HNIDatagen)
    }

    private fun setupConfig(bus: IEventBus, container: ModContainer) {
        val manager = ConfigManager().includeDefaultValueComments()

        CONFIG = manager
            .build(HNIConfig::class.java)
            .register(container, ModConfig.Type.STARTUP)
            .load()
            .listenToLoad(bus)
            .config()
    }

}