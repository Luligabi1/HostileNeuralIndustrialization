package me.luligabi.hostile_neural_industrialization.common

import aztech.modern_industrialization.util.TextHelper
import me.luligabi.hostile_neural_industrialization.common.block.HNIBlocks
import me.luligabi.hostile_neural_industrialization.common.block.machine.HNIMachines
import me.luligabi.hostile_neural_industrialization.common.compat.guideme.HNIGuide
import me.luligabi.hostile_neural_industrialization.common.item.HNIItems
import me.luligabi.hostile_neural_industrialization.common.misc.HNICreativeTab
import me.luligabi.hostile_neural_industrialization.common.misc.HNIIngredients
import me.luligabi.hostile_neural_industrialization.common.misc.network.HNIPackets
import me.luligabi.hostile_neural_industrialization.common.util.HNIText
import me.luligabi.hostile_neural_industrialization.datagen.HNIDatagen
import net.minecraft.resources.ResourceLocation
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import net.neoforged.fml.config.ModConfig
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent
import net.swedz.tesseract.neoforge.compat.mi.TesseractMI
import net.swedz.tesseract.neoforge.config.ConfigManager
import net.swedz.tesseract.neoforge.lang.LangInstance
import net.swedz.tesseract.neoforge.lang.LangManager

@Mod(HNI.ID)
class HNI(modEventBus: IEventBus, container: ModContainer) {

    companion object {
        const val ID = "hostile_neural_industrialization"

        fun id(id: String) = ResourceLocation.fromNamespaceAndPath(ID, id)

        lateinit var CONFIG: HNIConfig
            private set
        lateinit var TEXT: HNIText
            private set

        lateinit var LANG_INSTANCE: LangInstance<HNIText>
            private set
    }

    init {
        preSetup(modEventBus, container)

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

    private fun preSetup(bus: IEventBus, container: ModContainer) {
        val manager = ConfigManager().includeDefaultValueComments()

        CONFIG = manager
            .build(HNIConfig::class.java)
            .register(container, ModConfig.Type.STARTUP)
            .load()
            .listenToLoad(bus)
            .config()

        LANG_INSTANCE = LangManager(ID)
            .style("gray", { TextHelper.GRAY_TEXT })
            .style("highlight", { TextHelper.NUMBER_TEXT })
            .build(HNIText::class.java)
            .load()

        TEXT = LANG_INSTANCE
            .lang()
    }

}