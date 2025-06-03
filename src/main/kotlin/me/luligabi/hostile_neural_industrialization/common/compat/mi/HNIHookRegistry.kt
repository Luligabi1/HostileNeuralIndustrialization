package me.luligabi.hostile_neural_industrialization.common.compat.mi

import aztech.modern_industrialization.machines.recipe.MachineRecipeType
import me.luligabi.hostile_neural_industrialization.common.HNICreativeTab
import me.luligabi.hostile_neural_industrialization.common.block.HNIBlocks
import me.luligabi.hostile_neural_industrialization.common.block.machine.HNIMachines
import me.luligabi.hostile_neural_industrialization.common.item.HNIItems
import net.minecraft.world.level.block.entity.BlockEntityType
import net.swedz.tesseract.neoforge.compat.mi.hook.MIHookEntrypoint
import net.swedz.tesseract.neoforge.compat.mi.hook.MIHookRegistry
import net.swedz.tesseract.neoforge.registry.holder.BlockHolder
import net.swedz.tesseract.neoforge.registry.holder.ItemHolder

@MIHookEntrypoint
class HNIHookRegistry: MIHookRegistry {

    override fun blockRegistry() = HNIBlocks.Registry.BLOCKS

    override fun blockEntityRegistry() = HNIBlocks.Registry.BLOCK_ENTITIES

    override fun itemRegistry() = HNIItems.Registry.ITEMS

    override fun recipeSerializerRegistry() = HNIMachines.RecipeTypes.RECIPE_SERIALIZERS

    override fun recipeTypeRegistry() = HNIMachines.RecipeTypes.RECIPE_TYPES

    override fun onBlockRegister(holder: BlockHolder<*>) {
        HNIBlocks.Registry.include(holder)
    }

    override fun onBlockEntityRegister(type: BlockEntityType<*>) {
    }

    override fun onItemRegister(holder: ItemHolder<*>) {
        HNIItems.Registry.include(holder)
    }

    override fun onMachineRecipeTypeRegister(type: MachineRecipeType) {
    }

    override fun sortOrderMachines() = HNICreativeTab.Order.MACHINES

}