package me.luligabi.hostile_neural_industrialization.datagen.client.provider.model

import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.HNIBlocks
import net.neoforged.neoforge.client.model.generators.BlockStateProvider
import net.neoforged.neoforge.data.event.GatherDataEvent


class HNIBlockModelProvider(event: GatherDataEvent): BlockStateProvider(event.generator.packOutput, HNI.ID, event.existingFileHelper) {

    override fun registerStatesAndModels() {

        for (block in HNIBlocks.values()) {
            if (!block.hasModelProvider()) continue
            block.modelProvider().accept(this)
        }

    }

}