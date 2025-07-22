package me.luligabi.hostile_neural_industrialization.common.util

import me.luligabi.hostile_neural_industrialization.common.HNI
import net.swedz.tesseract.neoforge.compat.mi.tooltip.MICompatibleTranslatableTextEnum

enum class HNIText(private val text: String): MICompatibleTranslatableTextEnum {

    GUIDEBOOK_TOOLTIP("The neural network must grow."),
    LOOT_SELECTOR_TITLE("Select Loot Output"),
    LOOT_SELECTOR_DESCRIPTION("Click to open loot selection panel."),
    LOOT_SELECTOR_MEMBER_NAME("%dx %s");


    override fun englishText() = text

    override fun getTranslationKey() = "text.${HNI.ID}.${this.name.lowercase()}"

}