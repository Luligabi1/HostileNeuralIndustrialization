package me.luligabi.hostile_neural_industrialization.client

import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.client.event.ClientTickEvent

object DelayedClientTask {

    private val delayedActions = mutableListOf<DelayedAction>()

    @SubscribeEvent // a bit overengineered, but the user *could* spam click the slot, causing slight desyncs
    fun onClientTick(event: ClientTickEvent.Post) {

        val iterator = delayedActions.iterator()
        while (iterator.hasNext()) {
            val action = iterator.next()
            action.ticksRemaining--
            if (action.ticksRemaining <= 0) {
                action.runnable()
                iterator.remove()
            }
        }
    }

    fun runLater(ticks: Int, action: () -> Unit) {
        delayedActions.add(DelayedAction(ticks, action))
    }

    private data class DelayedAction(var ticksRemaining: Int, val runnable: () -> Unit)
}