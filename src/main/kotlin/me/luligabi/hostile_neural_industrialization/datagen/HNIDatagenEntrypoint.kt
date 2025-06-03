package me.luligabi.hostile_neural_industrialization.datagen

import net.minecraft.data.DataProvider
import net.neoforged.neoforge.data.event.GatherDataEvent

interface HNIDatagenEntrypoint {

    fun GatherDataEvent.add(provider: (GatherDataEvent) -> DataProvider) {
        generator.addProvider(includeClient(), provider.invoke(this))
    }

}