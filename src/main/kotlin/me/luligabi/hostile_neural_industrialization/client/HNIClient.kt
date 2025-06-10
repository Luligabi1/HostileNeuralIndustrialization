package me.luligabi.hostile_neural_industrialization.client

import aztech.modern_industrialization.MIText
import aztech.modern_industrialization.machines.GuiComponentsClient
import dev.shadowsoffire.hostilenetworks.Hostile
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.machine.mono_loot_fabricator.loot_selector.LootSelector
import me.luligabi.hostile_neural_industrialization.common.util.HNIText
import net.minecraft.ChatFormatting
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.common.NeoForge
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent

@Mod(HNI.ID, dist = [Dist.CLIENT])
class HNIClient {

    init {
        GuiComponentsClient.register(LootSelector.ID, ::LootSelectorClient)

        NeoForge.EVENT_BUS.register(this)
    }

    @SubscribeEvent
    fun onTooltip(event: ItemTooltipEvent) {

        when(event.itemStack.item) {

            Hostile.Items.PREDICTION.value() -> {

                val model = event.itemStack.get(Hostile.Components.DATA_MODEL)?.get() ?: return

                val costText = MIText.EuT.text((model.simCost / 10), "").withStyle(ChatFormatting.GRAY)
                val prefixText = HNIText.FAB_COST_TOOLTIP.text(costText).withStyle(ChatFormatting.WHITE)

                event.toolTip.add(prefixText.append(costText))
            }

        }

    }

}