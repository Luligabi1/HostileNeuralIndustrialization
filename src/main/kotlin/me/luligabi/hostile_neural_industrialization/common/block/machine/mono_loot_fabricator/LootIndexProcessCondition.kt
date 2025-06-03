package me.luligabi.hostile_neural_industrialization.common.block.machine.mono_loot_fabricator

import aztech.modern_industrialization.machines.recipe.MachineRecipe
import aztech.modern_industrialization.machines.recipe.condition.MachineProcessCondition
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec

class LootIndexProcessCondition(val index: Int): MachineProcessCondition {

    private companion object {

        val CODEC: MapCodec<LootIndexProcessCondition> = RecordCodecBuilder.mapCodec { i: RecordCodecBuilder.Instance<LootIndexProcessCondition> ->
            i.group(
                Codec.INT.fieldOf("index").forGetter(LootIndexProcessCondition::index)
            ).apply(i) { index -> LootIndexProcessCondition(index) }
        }

        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, LootIndexProcessCondition> = StreamCodec.composite(
            ByteBufCodecs.INT,
            LootIndexProcessCondition::index
        ) { index -> LootIndexProcessCondition(index) }
    }

    override fun canProcessRecipe(context: MachineProcessCondition.Context, recipe: MachineRecipe): Boolean {
        return (context.blockEntity as? MonoLootFabricatorBlockEntity)?.let {
            it.lootSelector.selectedLootIndex == index
        } ?: false
    }

    override fun codec() = CODEC

    override fun streamCodec() = STREAM_CODEC

    override fun appendDescription(list: MutableList<Component>) {}

}