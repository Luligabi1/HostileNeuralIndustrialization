package me.luligabi.hostile_neural_industrialization.datagen

import me.luligabi.hostile_neural_industrialization.datagen.client.HNIClientDatagen
import me.luligabi.hostile_neural_industrialization.datagen.server.HNIServerDatagen
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.data.event.GatherDataEvent

object HNIDatagen {

    @SubscribeEvent
    fun onGatherData(event: GatherDataEvent) {
        HNIServerDatagen.onGatherData(event)
        HNIClientDatagen.onGatherData(event)
    }

}