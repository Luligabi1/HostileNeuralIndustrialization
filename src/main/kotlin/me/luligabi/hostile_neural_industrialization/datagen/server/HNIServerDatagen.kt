package me.luligabi.hostile_neural_industrialization.datagen.server

import me.luligabi.hostile_neural_industrialization.datagen.HNIDatagenEntrypoint
import me.luligabi.hostile_neural_industrialization.datagen.server.provider.HNILootTableProvider
import me.luligabi.hostile_neural_industrialization.datagen.server.provider.recipe.HNIRecipeProvider
import net.minecraft.core.HolderLookup
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry
import net.minecraft.data.loot.LootTableSubProvider
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import net.neoforged.neoforge.data.event.GatherDataEvent

object HNIServerDatagen: HNIDatagenEntrypoint {

    fun onGatherData(event: GatherDataEvent) {
        event.add(::HNIRecipeProvider)
        event.addLootTable(::HNILootTableProvider)
    }

    private fun GatherDataEvent.addLootTable(providerCreator: (HolderLookup.Provider) -> LootTableSubProvider) {
        generator.addProvider(
            includeServer(),
            LootTableProvider(
                generator.packOutput,
                setOf(),
                listOf(SubProviderEntry(providerCreator, LootContextParamSets.BLOCK)),
                lookupProvider
            )
        )
    }

}