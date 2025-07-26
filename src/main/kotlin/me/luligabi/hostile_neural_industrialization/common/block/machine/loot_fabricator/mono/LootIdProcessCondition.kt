package me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.mono

import aztech.modern_industrialization.machines.recipe.MachineRecipe
import aztech.modern_industrialization.machines.recipe.condition.MachineProcessCondition
import aztech.modern_industrialization.machines.recipe.condition.MachineProcessConditions
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import me.luligabi.hostile_neural_industrialization.common.HNI
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.codec.StreamCodec
import net.minecraft.resources.ResourceLocation

class LootIdProcessCondition(val lootId: ResourceLocation): MachineProcessCondition {

    private companion object {

        val CODEC: MapCodec<LootIdProcessCondition> = RecordCodecBuilder.mapCodec { i ->
            i.group(
                ResourceLocation.CODEC.fieldOf("loot_id").forGetter(LootIdProcessCondition::lootId)
            ).apply(i, ::LootIdProcessCondition)
        }

        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, LootIdProcessCondition> = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, LootIdProcessCondition::lootId,
            ::LootIdProcessCondition
        )

        init {
            MachineProcessConditions.register(HNI.id("loot_id"), CODEC, STREAM_CODEC)
        }

    }

    override fun canProcessRecipe(context: MachineProcessCondition.Context, recipe: MachineRecipe): Boolean {
        return (context.blockEntity as? MonoLootFabricatorBlockEntity)?.let {
            it.lootSelector.selectedLootId == lootId
        } ?: false
    }

    override fun codec() = CODEC

    override fun streamCodec() = STREAM_CODEC

    override fun appendDescription(list: MutableList<Component>) {
    }

}