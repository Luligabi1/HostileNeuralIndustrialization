package me.luligabi.hostile_neural_industrialization.common

import me.luligabi.hostile_neural_industrialization.common.block.HNIBlocks
import me.luligabi.hostile_neural_industrialization.common.block.machine.HNIMachines
import me.luligabi.hostile_neural_industrialization.common.compat.guideme.HNIGuide
import me.luligabi.hostile_neural_industrialization.common.item.HNIItems
import me.luligabi.hostile_neural_industrialization.common.misc.HNICreativeTab
import me.luligabi.hostile_neural_industrialization.common.misc.HNIIngredients
import me.luligabi.hostile_neural_industrialization.common.misc.network.HNIPackets
import me.luligabi.hostile_neural_industrialization.datagen.HNIDatagen
import net.minecraft.resources.ResourceLocation
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import net.neoforged.fml.config.ModConfig
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent
import net.swedz.tesseract.neoforge.compat.mi.TesseractMI
import net.swedz.tesseract.neoforge.config.ConfigManager

@Mod(HNI.ID)
class HNI(modEventBus: IEventBus, container: ModContainer) {

    companion object {
        const val ID = "hostile_neural_industrialization"

        fun id(id: String) = ResourceLocation.fromNamespaceAndPath(ID, id)

        private var CONFIG: HNIConfig? = null

        fun config() = requireNotNull(CONFIG, { "Config not yet loaded" })
    }

    init {
        setupConfig(modEventBus, container)

        TesseractMI.init(ID)
        HNIItems.init(modEventBus)
        HNIBlocks.init(modEventBus)
        HNIMachines.RecipeTypes.init(modEventBus)
        HNIIngredients.init(modEventBus)
        modEventBus.addListener(RegisterPayloadHandlersEvent::class.java, HNIPackets::init)
        HNICreativeTab.init(modEventBus)
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