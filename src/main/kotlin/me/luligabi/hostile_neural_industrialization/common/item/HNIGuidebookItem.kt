package me.luligabi.hostile_neural_industrialization.common.item

import guideme.GuidesCommon
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.compat.guideme.HNIGuide
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level


class HNIGuidebookItem(properties: Properties) : Item(properties) {

    override fun use(world: Level, user: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        if (world.isClientSide) {
            GuidesCommon.openGuide(user, HNIGuide.ID)
        }

        return InteractionResultHolder.consume(user.getItemInHand(hand))
    }

    override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltip: MutableList<Component>, flag: TooltipFlag) {
        tooltip.add(HNI.TEXT.guidebookTooltip())
    }

}