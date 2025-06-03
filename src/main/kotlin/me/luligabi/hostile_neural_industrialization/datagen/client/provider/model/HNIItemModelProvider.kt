package me.luligabi.hostile_neural_industrialization.datagen.client.provider.model

import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.item.HNIItems
import net.neoforged.neoforge.client.model.generators.ItemModelProvider
import net.neoforged.neoforge.data.event.GatherDataEvent


class HNIItemModelProvider(event: GatherDataEvent): ItemModelProvider(event.generator.packOutput, HNI.ID, event.existingFileHelper) {

    override fun registerModels() {

        for (item in HNIItems.values()) {
            if (!item.hasModelProvider()) continue
            item.modelProvider().accept(this)
        }

    }

}