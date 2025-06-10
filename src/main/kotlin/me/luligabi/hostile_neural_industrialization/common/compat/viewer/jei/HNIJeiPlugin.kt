package me.luligabi.hostile_neural_industrialization.common.compat.viewer.jei

import dev.shadowsoffire.hostilenetworks.jei.LootFabCategory
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.machine.HNIMachines
import me.luligabi.hostile_neural_industrialization.common.block.machine.large_loot_fabricator.LargeLootFabricatorBlockEntity
import me.luligabi.hostile_neural_industrialization.common.block.machine.mono_loot_fabricator.MonoLootFabricatorBlockEntity
import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import mezz.jei.api.registration.IRecipeCatalystRegistration

@JeiPlugin
class HNIJeiPlugin: IModPlugin {

    override fun registerRecipeCatalysts(registration: IRecipeCatalystRegistration) {
        registration.addRecipeCatalyst(HNIMachines.getMachineFromId(MonoLootFabricatorBlockEntity.ID), LootFabCategory.TYPE)
        registration.addRecipeCatalyst(HNIMachines.getMachineFromId(LargeLootFabricatorBlockEntity.ID), LootFabCategory.TYPE)
    }

    override fun getPluginUid() = HNI.id("jei_plugin")

}