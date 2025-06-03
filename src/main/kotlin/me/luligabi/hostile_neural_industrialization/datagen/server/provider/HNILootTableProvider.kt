package me.luligabi.hostile_neural_industrialization.datagen.server.provider

import me.luligabi.hostile_neural_industrialization.common.block.HNIBlocks
import net.minecraft.core.HolderLookup
import net.minecraft.data.loot.BlockLootSubProvider
import net.minecraft.world.flag.FeatureFlags

class HNILootTableProvider(registries: HolderLookup.Provider): BlockLootSubProvider(emptySet(), FeatureFlags.VANILLA_SET, registries) {


    override fun getKnownBlocks() = HNIBlocks.values()
        .filter { it.hasLootTable() }
        .map { it.get() }
        .toList()

    override fun generate() {

        for (block in HNIBlocks.values()) {
            if (!block.hasLootTable()) continue
            add(block.get(), block.buildLootTable(this))
        }

    }

}