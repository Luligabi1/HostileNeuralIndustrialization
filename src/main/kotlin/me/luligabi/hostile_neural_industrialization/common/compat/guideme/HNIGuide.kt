package me.luligabi.hostile_neural_industrialization.common.compat.guideme

import guideme.Guide
import me.luligabi.hostile_neural_industrialization.common.HNI

object HNIGuide {

    val ID = HNI.id("guide")

    val GUIDE = Guide.builder(ID)
        .folder("hni_guidebook")
        .build()

}