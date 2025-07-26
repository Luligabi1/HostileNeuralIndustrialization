package me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.shadowsoffire.hostilenetworks.Hostile
import dev.shadowsoffire.hostilenetworks.data.DataModel
import dev.shadowsoffire.hostilenetworks.data.DataModelRegistry
import dev.shadowsoffire.hostilenetworks.item.DataModelItem
import me.luligabi.hostile_neural_industrialization.common.misc.HNIIngredients
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.common.crafting.ICustomIngredient
import java.util.stream.Stream

class PredictionIngredient(private val modelId: ResourceLocation): ICustomIngredient {

    constructor(model: DataModel): this(
        DataModelRegistry.INSTANCE.getKey(model) ?: throw IllegalArgumentException("Data Model $model not found!")
    )

    private val model by lazy {
        DataModelRegistry.INSTANCE.getValue(modelId) ?: throw IllegalArgumentException("Data Model $modelId not found!")
    }

    override fun test(stack: ItemStack): Boolean {
        if (!stack.`is`(Hostile.Items.PREDICTION.value())) return false

        val model = stack.get(Hostile.Components.DATA_MODEL)?.get() ?: return false
        return model == this.model
    }

    override fun getItems(): Stream<ItemStack> {
        val modelStack = ItemStack(Hostile.Items.PREDICTION)
        DataModelItem.setStoredModel(modelStack, model)

        return Stream.of(modelStack)
    }

    override fun isSimple() = false

    override fun getType() = HNIIngredients.PREDICTION.value()

    companion object {

        val CODEC: MapCodec<PredictionIngredient> = RecordCodecBuilder.mapCodec {
            it.group(
                ResourceLocation.CODEC.fieldOf("model").forGetter(PredictionIngredient::modelId),
            ).apply(it, ::PredictionIngredient)
        }

    }

}