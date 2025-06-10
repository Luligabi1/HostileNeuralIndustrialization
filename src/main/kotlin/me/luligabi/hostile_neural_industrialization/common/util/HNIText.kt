package me.luligabi.hostile_neural_industrialization.common.util

import me.luligabi.hostile_neural_industrialization.common.HNI
import net.swedz.tesseract.neoforge.compat.mi.tooltip.MICompatibleTranslatableTextEnum

enum class HNIText(private val text: String): MICompatibleTranslatableTextEnum {

    GUIDEBOOK_TOOLTIP("The neural network must grow."),
    FAB_COST_TOOLTIP("Large Fab. Cost: ");

    override fun englishText() = text

    override fun getTranslationKey() = "text.${HNI.ID}.${this.name.lowercase()}"

}