package me.luligabi.hostile_neural_industrialization.common.block.machine.sim_chamber

import aztech.modern_industrialization.inventory.ConfigurableItemStack
import aztech.modern_industrialization.thirdparty.fabrictransfer.api.item.ItemVariant
import dev.shadowsoffire.hostilenetworks.HostileConfig
import dev.shadowsoffire.hostilenetworks.data.DataModelInstance
import dev.shadowsoffire.hostilenetworks.item.DataModelItem
import me.luligabi.hostile_neural_industrialization.mixin.AbstractConfigurableStackAccessor
import net.minecraft.world.item.ItemStack

interface HNISimChamber {

    val dataIncreaseAmount: Int
        get() = 1

    fun getUpdatedModel(configurable: ConfigurableItemStack) = configurable.toStack().let {

        val model = DataModelInstance(it, 0)
        val tier = model.getTier()
        if (!tier.isMax && HostileConfig.simModelUpgrade > 0) {
            val newData = model.getData() + dataIncreaseAmount
            if (HostileConfig.simModelUpgrade != 2 || newData <= model.nextTierData) {
                model.setData(newData)
            }
        }
        DataModelItem.setIters(it, DataModelItem.getIters(it) + 1)
        it
    }

    fun ConfigurableItemStack.setContent(updatedModel: ItemStack) = apply {
        setKey(ItemVariant.of(updatedModel))
        amount = 1
        (this as AbstractConfigurableStackAccessor).invokeNotifyListeners()
    }


}