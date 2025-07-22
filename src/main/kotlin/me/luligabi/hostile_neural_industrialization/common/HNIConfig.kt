package me.luligabi.hostile_neural_industrialization.common

import net.swedz.tesseract.neoforge.config.annotation.ConfigComment
import net.swedz.tesseract.neoforge.config.annotation.ConfigKey
import net.swedz.tesseract.neoforge.config.annotation.Range
import net.swedz.tesseract.neoforge.config.annotation.SubSection

interface HNIConfig {

    @ConfigKey("electric_simulation_chamber")
    @SubSection
    fun electricSimChamber(): ElectricSimChamber

    interface ElectricSimChamber {

        @ConfigKey("duration")
        @ConfigComment("Duration in ticks for generated recipes")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun duration() = 17 * 20

        @ConfigKey("energy_multiplier")
        @ConfigComment(
            "Energy per tick multiplier compared to model's simulation cost for generated recipes",
            "i.e. 1,000RF * 0.1 multiplier = 100 EU/t"
        )
        @Range.Double(min = 0.01, max = Double.MAX_VALUE)
        fun energyMultiplier() = 0.1

        @ConfigKey("runtime_recipes")
        @ConfigComment(
            "Whether Electic Simulation Chamber recipes should be generated automatically at runtime.",
            "Disable this if you're a modpack creator and intend to manually create all recipes."
        )
        fun runtimeRecipes() = true

    }

    @ConfigKey("large_simulation_chamber")
    @SubSection
    fun largeSimChamber(): LargeSimChamber

    interface LargeSimChamber {

        @ConfigKey("input_amount_per_recipe")
        @ConfigComment(
            "Input amount on each generated recipe",
            "Determines amount of data added to model and consumed Prediction Matrixes."
        )
        @Range.Integer(min = 1, max = 64)
        fun inputPerRecipeAmount() = 2

        @ConfigKey("output_amount_per_recipe")
        @ConfigComment(
            "Output amount on each generated recipe",
            "Determines amount of (Generalized) Predictions crafted."
        )
        @Range.Integer(min = 1, max = 64)
        fun outputPerRecipeAmount() = 4

        @ConfigKey("duration")
        @ConfigComment("Duration in ticks for generated recipes")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun duration() = 1 * 60 * 20

        @ConfigKey("energy_multiplier")
        @ConfigComment(
            "Energy per tick multiplier compared to model's simulation cost for generated recipes",
            "i.e. 1,000RF * 0.1 multiplier = 100 EU/t"
        )
        @Range.Double(min = 0.01, max = Double.MAX_VALUE)
        fun energyMultiplier() = 0.5

        @ConfigKey("runtime_recipes")
        @ConfigComment(
            "Whether Large Simulation Chamber recipes should be generated automatically at runtime.",
            "Disable this if you're a modpack creator and intend to manually create all recipes."
        )
        fun runtimeRecipes() = true

    }

    @ConfigKey("mono_loot_fabricator")
    @SubSection
    fun monoLootFabricator(): MonoLootFabricator

    interface MonoLootFabricator {

        @ConfigKey("output_amount_multiplier")
        @ConfigComment(
            "Output amount multiplier for generated recipes",
            "i.e. 64x Steaks with a 0.5 multiplier = 32x Steaks"
        )
        @Range.Double(min = 0.01, max = 64.0)
        fun outputAmountMultiplier() = 1.0

        @ConfigKey("duration")
        @ConfigComment("Duration in ticks for generated recipes")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun duration() = 10 * 20

        @ConfigKey("energy_multiplier")
        @ConfigComment(
            "Energy per tick multiplier compared to model's simulation cost for generated recipes",
            "i.e. 1,000RF * 0.1 multiplier = 100 EU/t"
        )
        @Range.Double(min = 0.01, max = Double.MAX_VALUE)
        fun energyMultiplier() = 0.1

        @ConfigKey("runtime_recipes")
        @ConfigComment(
            "Whether Mono Loot Fabricator recipes should be generated automatically at runtime.",
            "Disable this if you're a modpack creator and intend to manually create all recipes."
        )
        fun runtimeRecipes() = true

    }

    @ConfigKey("large_loot_fabricator")
    @SubSection
    fun largeLootFabricator(): LargeLootFabricator

    interface LargeLootFabricator {

        @ConfigKey("base_prediction_amount")
        @ConfigComment("Base amount of Predictions required on generated recipes")
        @Range.Integer(min = 1, max = 64)
        fun basePredictionAmount() = 1

        @ConfigKey("bonus_prediction_amount")
        @ConfigComment("For every X amount of possible drops, the amount of predictions required will increase by 1")
        @Range.Integer(min = 0, max = 64)
        fun bonusPredictionAmount() = 4

        @ConfigKey("min_loot_for_generated_recipes")
        @ConfigComment("Minimum amount of possible drops a model must have to generate a recipe")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun minimumLootForRecipe() = 3

        @ConfigKey("output_probability")
        @ConfigComment("Probability each loot will be generated on generated recipes")
        @Range.Double(min = 0.01, max = 1.0)
        fun outputProbability() = 1.0

        @ConfigKey("output_amount_multiplier")
        @ConfigComment(
            "Output amount multiplier for generated recipes",
            "i.e. 64x Steaks with a 0.5 multiplier = 32 Steaks"
        )
        @Range.Double(min = 0.01, max = 2.0)
        fun outputAmountMultiplier() = 1.0

        @ConfigKey("duration")
        @ConfigComment("Duration in ticks for generated recipes")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun duration() = 7 * 60 * 20

        @ConfigKey("energy_multiplier")
        @ConfigComment(
            "Energy per tick multiplier compared to model's simulation cost for generated recipes",
            "i.e. 1,000RF * 0.1 multiplier = 100 EU/t"
        )
        @Range.Double(min = 0.01, max = Double.MAX_VALUE)
        fun energyMultiplier() = 0.1

        @ConfigKey("runtime_recipes")
        @ConfigComment(
            "Whether Large Loot Fabricator recipes should be generated automatically at runtime.",
            "Disable this if you're a modpack creator and intend to manually create all recipes."
        )
        fun runtimeRecipes() = true

    }
    
}