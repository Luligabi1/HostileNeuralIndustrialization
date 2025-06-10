package me.luligabi.hostile_neural_industrialization.common.compat.guideme

import aztech.modern_industrialization.guidebook.MultiblockShapeCompiler
import guideme.Guide
import guideme.scene.element.SceneElementTagCompiler
import me.luligabi.hostile_neural_industrialization.common.HNI

object HNIGuide {

    val ID = HNI.id("guide")

    val GUIDE = Guide.builder(ID)
        .folder("hni_guidebook")
        .extension(SceneElementTagCompiler.EXTENSION_POINT, MultiblockShapeCompiler())
        .build()

}