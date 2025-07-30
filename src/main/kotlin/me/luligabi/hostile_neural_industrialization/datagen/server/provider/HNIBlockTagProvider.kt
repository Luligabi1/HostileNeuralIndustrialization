package me.luligabi.hostile_neural_industrialization.datagen.server.provider

import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.HNIBlocks
import net.minecraft.core.HolderLookup
import net.neoforged.neoforge.common.data.BlockTagsProvider
import net.neoforged.neoforge.data.event.GatherDataEvent


class HNIBlockTagProvider(event: GatherDataEvent): BlockTagsProvider(event.generator.packOutput, event.lookupProvider, HNI.ID, event.existingFileHelper) {

    override fun addTags(provider: HolderLookup.Provider) {
        for (block in HNIBlocks.values().sortedBy { it.identifier().id() }) {
            for (tag in block.tags()) {
                tag(tag).add(block.get())
            }
        }

    }

}