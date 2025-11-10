package me.luligabi.hostile_neural_industrialization.common.util

import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.swedz.tesseract.neoforge.lang.annotation.LangKey
import net.swedz.tesseract.neoforge.lang.annotation.WithStyle

interface HNIText {

    @WithStyle("gray")
    @LangKey(text = ["The neural network must grow."])
    fun guidebookTooltip(): MutableComponent

    @LangKey(text = ["Select Loot Output"])
    fun lootSelectorTitle(): MutableComponent

    @WithStyle("gray")
    @LangKey(text = ["Click to open loot selection panel."])
    fun lootSelectorDescription(): MutableComponent

    @WithStyle("gray")
    @LangKey(text = ["%dx %s"])
    fun lootSelectorMemberName(
        @WithStyle("highlight") amount: Int,
        @WithStyle("highlight") name: Component
    ): MutableComponent

}