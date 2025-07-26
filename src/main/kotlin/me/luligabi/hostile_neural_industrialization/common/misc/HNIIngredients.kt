package me.luligabi.hostile_neural_industrialization.common.misc

import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.PredictionIngredient
import me.luligabi.hostile_neural_industrialization.common.block.machine.sim_chamber.AbstractSimChamberRecipeType
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.common.crafting.IngredientType
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NeoForgeRegistries
import java.util.function.Supplier

object HNIIngredients {

    fun init(modBus: IEventBus) {
        INGREDIENTS.register(modBus)
    }

    private val INGREDIENTS = DeferredRegister.create(NeoForgeRegistries.Keys.INGREDIENT_TYPES, HNI.ID)

    val DATA_MODEL = INGREDIENTS.register(
        "data_model",
        Supplier { IngredientType(AbstractSimChamberRecipeType.DataModelIngredient.CODEC) }
    )

    val PREDICTION = INGREDIENTS.register(
        "prediction",
        Supplier { IngredientType(PredictionIngredient.CODEC) }
    )

}