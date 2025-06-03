package me.luligabi.hostile_neural_industrialization.client

import aztech.modern_industrialization.machines.GuiComponentsClient
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.machine.mono_loot_fabricator.loot_selector.LootSelector
import net.neoforged.api.distmarker.Dist
import net.neoforged.fml.common.Mod

@Mod(HNI.ID, dist = [Dist.CLIENT])
class HNIClient {

    init {
        GuiComponentsClient.register(LootSelector.ID, ::LootSelectorClient)
    }

}