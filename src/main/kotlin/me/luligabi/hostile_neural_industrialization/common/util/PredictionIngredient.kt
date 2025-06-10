package me.luligabi.hostile_neural_industrialization.common.util

import dev.shadowsoffire.hostilenetworks.Hostile
import dev.shadowsoffire.hostilenetworks.data.DataModel
import net.minecraft.core.Holder
import net.minecraft.core.HolderSet
import net.minecraft.core.component.DataComponentPredicate
import net.neoforged.neoforge.common.crafting.DataComponentIngredient

class PredictionIngredient(model: DataModel): DataComponentIngredient(
    HolderSet.direct(Holder.direct(model.predictionDrop.item)),
    DataComponentPredicate.builder()
        .expect(Hostile.Components.DATA_MODEL, model.predictionDrop.get(Hostile.Components.DATA_MODEL) ?: throw IllegalArgumentException("Model has invalid prediction data"))
        .build(),
    true
)