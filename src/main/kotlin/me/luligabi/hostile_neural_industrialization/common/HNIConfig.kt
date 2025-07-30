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
        @ConfigComment("Duration in ticks for generated recipes. Default: 17 seconds")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun duration() = 17 * 20

        @ConfigKey("energy_multiplier")
        @ConfigComment(
            "Energy per tick multiplier compared to model's simulation cost for generated recipes",
            "i.e. 1,000RF * 0.1 multiplier = 100 EU/t"
        )
        @Range.Double(min = 0.01, max = Double.MAX_VALUE)
        fun energyMultiplier() = 0.25

        /** Fluid */
        // OVERWORLD
        @ConfigKey("overworld_fluid_input_id")
        @ConfigComment(
            "Required fluid for Overworld predictions",
            "Leave empty to remove fluid requirement"
        )
        fun overworldFluidInputId() = ""

        @ConfigKey("overworld_fluid_input_amount")
        @ConfigComment("Amount of fluid required for Overworld predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun overworldFluidInputAmount() = 1_000

        @ConfigKey("overworld_fluid_input_probability")
        @ConfigComment("Probability the input fluid for Overworld predictions will be consumed")
        @Range.Double(min = 0.01, max = 1.0)
        fun overworldFluidInputProbability() = 1.0

        @ConfigKey("overworld_fluid_output_id")
        @ConfigComment(
            "Fluid outputted for Overworld predictions",
            "Leave empty to remove fluid output"
        )
        fun overworldFluidOutputId() = ""

        @ConfigKey("overworld_fluid_output_amount")
        @ConfigComment("Amount of fluid outputted for Overworld predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun overworldFluidOutputAmount() = 1_000

        @ConfigKey("overworld_fluid_output_probability")
        @ConfigComment("Probability the output fluid for Overworld predictions will be produced")
        @Range.Double(min = 0.01, max = 1.0)
        fun overworldFluidOutputProbability() = 1.0

        // NETHER
        @ConfigKey("nether_fluid_input_id")
        @ConfigComment(
            "Required fluid for Nether predictions",
            "Leave empty to remove fluid requirement"
        )
        fun netherFluidInputId() = ""

        @ConfigKey("nether_fluid_input_amount")
        @ConfigComment("Amount of fluid required for Nether predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun netherFluidInputAmount() = 1_000

        @ConfigKey("nether_fluid_input_probability")
        @ConfigComment("Probability the input fluid for Nether predictions will be consumed")
        @Range.Double(min = 0.01, max = 1.0)
        fun netherFluidInputProbability() = 1.0

        @ConfigKey("nether_fluid_output_id")
        @ConfigComment(
            "Fluid outputted for Nether predictions",
            "Leave empty to remove fluid output"
        )
        fun netherFluidOutputId() = ""

        @ConfigKey("nether_fluid_output_amount")
        @ConfigComment("Amount of fluid outputted for Nether predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun netherFluidOutputAmount() = 1_000

        @ConfigKey("nether_fluid_output_probability")
        @ConfigComment("Probability the output fluid for Nether predictions will be produced")
        @Range.Double(min = 0.01, max = 1.0)
        fun netherFluidOutputProbability() = 1.0

        // THE END
        @ConfigKey("end_fluid_input_id")
        @ConfigComment(
            "Required fluid for The End predictions",
            "Leave empty to remove fluid requirement"
        )
        fun theEndFluidInputId() = ""

        @ConfigKey("end_fluid_input_amount")
        @ConfigComment("Amount of fluid required for The End predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun theEndFluidInputAmount() = 1_000

        @ConfigKey("end_fluid_input_probability")
        @ConfigComment("Probability the input fluid for The End predictions will be consumed")
        @Range.Double(min = 0.01, max = 1.0)
        fun theEndFluidInputProbability() = 1.0

        @ConfigKey("end_fluid_output_id")
        @ConfigComment(
            "Fluid outputted for The End predictions",
            "Leave empty to remove fluid output"
        )
        fun theEndFluidOutputId() = ""

        @ConfigKey("end_fluid_output_amount")
        @ConfigComment("Amount of fluid outputted for The End predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun theEndFluidOutputAmount() = 1_000

        @ConfigKey("end_fluid_output_probability")
        @ConfigComment("Probability the output fluid for The End predictions will be produced")
        @Range.Double(min = 0.01, max = 1.0)
        fun theEndFluidOutputProbability() = 1.0

        // TWILIGHT
        @ConfigKey("twilight_fluid_input_id")
        @ConfigComment(
            "Required fluid for Twilight Forest predictions",
            "Leave empty to remove fluid requirement"
        )
        fun twilightFluidInputId() = ""

        @ConfigKey("twilight_fluid_input_amount")
        @ConfigComment("Amount of fluid required for Twilight Forest predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun twilightFluidInputAmount() = 1_000

        @ConfigKey("twilight_fluid_input_probability")
        @ConfigComment("Probability the input fluid for Twilight Forest predictions will be consumed")
        @Range.Double(min = 0.01, max = 1.0)
        fun twilightFluidInputProbability() = 1.0

        @ConfigKey("twilight_fluid_output_id")
        @ConfigComment(
            "Fluid outputted for Twilight Forest predictions",
            "Leave empty to remove fluid output"
        )
        fun twilightFluidOutputId() = ""

        @ConfigKey("twilight_fluid_output_amount")
        @ConfigComment("Amount of fluid outputted for Twilight Forest predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun twilightFluidOutputAmount() = 1_000

        @ConfigKey("twilight_fluid_output_probability")
        @ConfigComment("Probability the output fluid for Twilight Forest predictions will be produced")
        @Range.Double(min = 0.01, max = 1.0)
        fun twilightFluidOutputProbability() = 1.0
        /**********/

        @ConfigKey("runtime_recipes")
        @ConfigComment(
            "Whether Electic Simulation Chamber recipes should be generated automatically at runtime.",
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
        @ConfigComment("Duration in ticks for generated recipes. Default: 10 seconds")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun duration() = 10 * 20

        @ConfigKey("energy")
        @ConfigComment(
            "Amount of energy used for generated recipes",
            "In HNN, Loot Fabricators use 256 RF"
        )
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun energy() = 15

        /** Fluid */
        // OVERWORLD
        @ConfigKey("overworld_fluid_input_id")
        @ConfigComment(
            "Required fluid for Overworld predictions",
            "Leave empty to remove fluid requirement"
        )
        fun overworldFluidInputId() = ""

        @ConfigKey("overworld_fluid_input_amount")
        @ConfigComment("Amount of fluid required for Overworld predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun overworldFluidInputAmount() = 1_000

        @ConfigKey("overworld_fluid_input_probability")
        @ConfigComment("Probability the input fluid for Overworld predictions will be consumed")
        @Range.Double(min = 0.01, max = 1.0)
        fun overworldFluidInputProbability() = 1.0

        @ConfigKey("overworld_fluid_output_id")
        @ConfigComment(
            "Fluid outputted for Overworld predictions",
            "Leave empty to remove fluid output"
        )
        fun overworldFluidOutputId() = ""

        @ConfigKey("overworld_fluid_output_amount")
        @ConfigComment("Amount of fluid outputted for Overworld predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun overworldFluidOutputAmount() = 1_000

        @ConfigKey("overworld_fluid_output_probability")
        @ConfigComment("Probability the output fluid for Overworld predictions will be produced")
        @Range.Double(min = 0.01, max = 1.0)
        fun overworldFluidOutputProbability() = 1.0

        // NETHER
        @ConfigKey("nether_fluid_input_id")
        @ConfigComment(
            "Required fluid for Nether predictions",
            "Leave empty to remove fluid requirement"
        )
        fun netherFluidInputId() = ""

        @ConfigKey("nether_fluid_input_amount")
        @ConfigComment("Amount of fluid required for Nether predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun netherFluidInputAmount() = 1_000

        @ConfigKey("nether_fluid_input_probability")
        @ConfigComment("Probability the input fluid for Nether predictions will be consumed")
        @Range.Double(min = 0.01, max = 1.0)
        fun netherFluidInputProbability() = 1.0

        @ConfigKey("nether_fluid_output_id")
        @ConfigComment(
            "Fluid outputted for Nether predictions",
            "Leave empty to remove fluid output"
        )
        fun netherFluidOutputId() = ""

        @ConfigKey("nether_fluid_output_amount")
        @ConfigComment("Amount of fluid outputted for Nether predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun netherFluidOutputAmount() = 1_000

        @ConfigKey("nether_fluid_output_probability")
        @ConfigComment("Probability the output fluid for Nether predictions will be produced")
        @Range.Double(min = 0.01, max = 1.0)
        fun netherFluidOutputProbability() = 1.0

        // THE END
        @ConfigKey("end_fluid_input_id")
        @ConfigComment(
            "Required fluid for The End predictions",
            "Leave empty to remove fluid requirement"
        )
        fun theEndFluidInputId() = ""

        @ConfigKey("end_fluid_input_amount")
        @ConfigComment("Amount of fluid required for The End predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun theEndFluidInputAmount() = 1_000

        @ConfigKey("end_fluid_input_probability")
        @ConfigComment("Probability the input fluid for The End predictions will be consumed")
        @Range.Double(min = 0.01, max = 1.0)
        fun theEndFluidInputProbability() = 1.0

        @ConfigKey("end_fluid_output_id")
        @ConfigComment(
            "Fluid outputted for The End predictions",
            "Leave empty to remove fluid output"
        )
        fun theEndFluidOutputId() = ""

        @ConfigKey("end_fluid_output_amount")
        @ConfigComment("Amount of fluid outputted for The End predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun theEndFluidOutputAmount() = 1_000

        @ConfigKey("end_fluid_output_probability")
        @ConfigComment("Probability the output fluid for The End predictions will be produced")
        @Range.Double(min = 0.01, max = 1.0)
        fun theEndFluidOutputProbability() = 1.0

        // TWILIGHT
        @ConfigKey("twilight_fluid_input_id")
        @ConfigComment(
            "Required fluid for Twilight Forest predictions",
            "Leave empty to remove fluid requirement"
        )
        fun twilightFluidInputId() = ""

        @ConfigKey("twilight_fluid_input_amount")
        @ConfigComment("Amount of fluid required for Twilight Forest predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun twilightFluidInputAmount() = 1_000

        @ConfigKey("twilight_fluid_input_probability")
        @ConfigComment("Probability the input fluid for Twilight Forest predictions will be consumed")
        @Range.Double(min = 0.01, max = 1.0)
        fun twilightFluidInputProbability() = 1.0

        @ConfigKey("twilight_fluid_output_id")
        @ConfigComment(
            "Fluid outputted for Twilight Forest predictions",
            "Leave empty to remove fluid output"
        )
        fun twilightFluidOutputId() = ""

        @ConfigKey("twilight_fluid_output_amount")
        @ConfigComment("Amount of fluid outputted for Twilight Forest predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun twilightFluidOutputAmount() = 1_000

        @ConfigKey("twilight_fluid_output_probability")
        @ConfigComment("Probability the output fluid for Twilight Forest predictions will be produced")
        @Range.Double(min = 0.01, max = 1.0)
        fun twilightFluidOutputProbability() = 1.0
        /**********/

        @ConfigKey("runtime_recipes")
        @ConfigComment(
            "Whether Mono Loot Fabricator recipes should be generated automatically at runtime.",
            "Disable this if you're a modpack creator and intend to manually create all recipes."
        )
        fun runtimeRecipes() = true

    }

    @ConfigKey("large_simulation_chamber")
    @SubSection
    fun largeSimChamber(): LargeSimChamber

    interface LargeSimChamber {

        @ConfigKey("data_added_per_recipe")
        @ConfigComment("Determines amount of data added to model. Applies to all recipes")
        @Range.Integer(min = 1, max = 64)
        fun dataPerRecipeAmount() = 2

        @ConfigKey("matrixes_per_recipe")
        @ConfigComment("Determines amount of consumed Prediction Matrixes on generated recipes")
        @Range.Integer(min = 1, max = 64)
        fun matrixesPerRecipeAmount() = 8

        @ConfigKey("prediction_amount_per_recipe")
        @ConfigComment("Determines amount of Predictions crafted on generated recipes")
        @Range.Integer(min = 1, max = 64)
        fun predictionPerRecipeAmount() = 4

        @ConfigKey("generalized_prediction_amount_per_recipe")
        @ConfigComment("Determines amount of Generalized Predictions crafted on generated recipes")
        @Range.Integer(min = 1, max = 64)
        fun generalizedPredictionPerRecipeAmount() = 4

        @ConfigKey("duration")
        @ConfigComment("Duration in ticks for generated recipes. Default: 1 minute")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun duration() = 1 * 60 * 20

        @ConfigKey("energy_multiplier")
        @ConfigComment(
            "Energy per tick multiplier compared to model's simulation cost for generated recipes",
            "i.e. 1,000RF * 0.1 multiplier = 100 EU/t"
        )
        @Range.Double(min = 0.01, max = Double.MAX_VALUE)
        fun energyMultiplier() = 0.5

        /** Fluid */
        // OVERWORLD
        @ConfigKey("overworld_fluid_input_id")
        @ConfigComment(
            "Required fluid for Overworld predictions",
            "Leave empty to remove fluid requirement"
        )
        fun overworldFluidInputId() = ""

        @ConfigKey("overworld_fluid_input_amount")
        @ConfigComment("Amount of fluid required for Overworld predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun overworldFluidInputAmount() = 1_000

        @ConfigKey("overworld_fluid_input_probability")
        @ConfigComment("Probability the input fluid for Overworld predictions will be consumed")
        @Range.Double(min = 0.01, max = 1.0)
        fun overworldFluidInputProbability() = 1.0

        @ConfigKey("overworld_fluid_output_id")
        @ConfigComment(
            "Fluid outputted for Overworld predictions",
            "Leave empty to remove fluid output"
        )
        fun overworldFluidOutputId() = ""

        @ConfigKey("overworld_fluid_output_amount")
        @ConfigComment("Amount of fluid outputted for Overworld predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun overworldFluidOutputAmount() = 1_000

        @ConfigKey("overworld_fluid_output_probability")
        @ConfigComment("Probability the output fluid for Overworld predictions will be produced")
        @Range.Double(min = 0.01, max = 1.0)
        fun overworldFluidOutputProbability() = 1.0

        // NETHER
        @ConfigKey("nether_fluid_input_id")
        @ConfigComment(
            "Required fluid for Nether predictions",
            "Leave empty to remove fluid requirement"
        )
        fun netherFluidInputId() = ""

        @ConfigKey("nether_fluid_input_amount")
        @ConfigComment("Amount of fluid required for Nether predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun netherFluidInputAmount() = 1_000

        @ConfigKey("nether_fluid_input_probability")
        @ConfigComment("Probability the input fluid for Nether predictions will be consumed")
        @Range.Double(min = 0.01, max = 1.0)
        fun netherFluidInputProbability() = 1.0

        @ConfigKey("nether_fluid_output_id")
        @ConfigComment(
            "Fluid outputted for Nether predictions",
            "Leave empty to remove fluid output"
        )
        fun netherFluidOutputId() = ""

        @ConfigKey("nether_fluid_output_amount")
        @ConfigComment("Amount of fluid outputted for Nether predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun netherFluidOutputAmount() = 1_000

        @ConfigKey("nether_fluid_output_probability")
        @ConfigComment("Probability the output fluid for Nether predictions will be produced")
        @Range.Double(min = 0.01, max = 1.0)
        fun netherFluidOutputProbability() = 1.0

        // THE END
        @ConfigKey("end_fluid_input_id")
        @ConfigComment(
            "Required fluid for The End predictions",
            "Leave empty to remove fluid requirement"
        )
        fun theEndFluidInputId() = ""

        @ConfigKey("end_fluid_input_amount")
        @ConfigComment("Amount of fluid required for The End predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun theEndFluidInputAmount() = 1_000

        @ConfigKey("end_fluid_input_probability")
        @ConfigComment("Probability the input fluid for The End predictions will be consumed")
        @Range.Double(min = 0.01, max = 1.0)
        fun theEndFluidInputProbability() = 1.0

        @ConfigKey("end_fluid_output_id")
        @ConfigComment(
            "Fluid outputted for The End predictions",
            "Leave empty to remove fluid output"
        )
        fun theEndFluidOutputId() = ""

        @ConfigKey("end_fluid_output_amount")
        @ConfigComment("Amount of fluid outputted for The End predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun theEndFluidOutputAmount() = 1_000

        @ConfigKey("end_fluid_output_probability")
        @ConfigComment("Probability the output fluid for The End predictions will be produced")
        @Range.Double(min = 0.01, max = 1.0)
        fun theEndFluidOutputProbability() = 1.0

        // TWILIGHT
        @ConfigKey("twilight_fluid_input_id")
        @ConfigComment(
            "Required fluid for Twilight Forest predictions",
            "Leave empty to remove fluid requirement"
        )
        fun twilightFluidInputId() = ""

        @ConfigKey("twilight_fluid_input_amount")
        @ConfigComment("Amount of fluid required for Twilight Forest predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun twilightFluidInputAmount() = 1_000

        @ConfigKey("twilight_fluid_input_probability")
        @ConfigComment("Probability the input fluid for Twilight Forest predictions will be consumed")
        @Range.Double(min = 0.01, max = 1.0)
        fun twilightFluidInputProbability() = 1.0

        @ConfigKey("twilight_fluid_output_id")
        @ConfigComment(
            "Fluid outputted for Twilight Forest predictions",
            "Leave empty to remove fluid output"
        )
        fun twilightFluidOutputId() = ""

        @ConfigKey("twilight_fluid_output_amount")
        @ConfigComment("Amount of fluid outputted for Twilight Forest predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun twilightFluidOutputAmount() = 1_000

        @ConfigKey("twilight_fluid_output_probability")
        @ConfigComment("Probability the output fluid for Twilight Forest predictions will be produced")
        @Range.Double(min = 0.01, max = 1.0)
        fun twilightFluidOutputProbability() = 1.0
        /**********/

        @ConfigKey("runtime_recipes")
        @ConfigComment(
            "Whether Large Simulation Chamber recipes should be generated automatically at runtime.",
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
        @ConfigComment("Duration in ticks for generated recipes. Default: 3 minutes")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun duration() = 3 * 60 * 20

        @ConfigKey("energy_multiplier")
        @ConfigComment(
            "Energy per tick multiplier compared to model's simulation cost for generated recipes",
            "i.e. 1,000RF * 0.1 multiplier = 100 EU/t"
        )
        @Range.Double(min = 0.01, max = Double.MAX_VALUE)
        fun energyMultiplier() = 0.45

        /** Fluid */
        // OVERWORLD
        @ConfigKey("overworld_fluid_input_id")
        @ConfigComment(
            "Required fluid for Overworld predictions",
            "Leave empty to remove fluid requirement"
        )
        fun overworldFluidInputId() = ""

        @ConfigKey("overworld_fluid_input_amount")
        @ConfigComment("Amount of fluid required for Overworld predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun overworldFluidInputAmount() = 1_000

        @ConfigKey("overworld_fluid_input_probability")
        @ConfigComment("Probability the input fluid for Overworld predictions will be consumed")
        @Range.Double(min = 0.01, max = 1.0)
        fun overworldFluidInputProbability() = 1.0

        @ConfigKey("overworld_fluid_output_id")
        @ConfigComment(
            "Fluid outputted for Overworld predictions",
            "Leave empty to remove fluid output"
        )
        fun overworldFluidOutputId() = ""

        @ConfigKey("overworld_fluid_output_amount")
        @ConfigComment("Amount of fluid outputted for Overworld predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun overworldFluidOutputAmount() = 1_000

        @ConfigKey("overworld_fluid_output_probability")
        @ConfigComment("Probability the output fluid for Overworld predictions will be produced")
        @Range.Double(min = 0.01, max = 1.0)
        fun overworldFluidOutputProbability() = 1.0

        // NETHER
        @ConfigKey("nether_fluid_input_id")
        @ConfigComment(
            "Required fluid for Nether predictions",
            "Leave empty to remove fluid requirement"
        )
        fun netherFluidInputId() = ""

        @ConfigKey("nether_fluid_input_amount")
        @ConfigComment("Amount of fluid required for Nether predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun netherFluidInputAmount() = 1_000

        @ConfigKey("nether_fluid_input_probability")
        @ConfigComment("Probability the input fluid for Nether predictions will be consumed")
        @Range.Double(min = 0.01, max = 1.0)
        fun netherFluidInputProbability() = 1.0

        @ConfigKey("nether_fluid_output_id")
        @ConfigComment(
            "Fluid outputted for Nether predictions",
            "Leave empty to remove fluid output"
        )
        fun netherFluidOutputId() = ""

        @ConfigKey("nether_fluid_output_amount")
        @ConfigComment("Amount of fluid outputted for Nether predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun netherFluidOutputAmount() = 1_000

        @ConfigKey("nether_fluid_output_probability")
        @ConfigComment("Probability the output fluid for Nether predictions will be produced")
        @Range.Double(min = 0.01, max = 1.0)
        fun netherFluidOutputProbability() = 1.0

        // THE END
        @ConfigKey("end_fluid_input_id")
        @ConfigComment(
            "Required fluid for The End predictions",
            "Leave empty to remove fluid requirement"
        )
        fun theEndFluidInputId() = ""

        @ConfigKey("end_fluid_input_amount")
        @ConfigComment("Amount of fluid required for The End predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun theEndFluidInputAmount() = 1_000

        @ConfigKey("end_fluid_input_probability")
        @ConfigComment("Probability the input fluid for The End predictions will be consumed")
        @Range.Double(min = 0.01, max = 1.0)
        fun theEndFluidInputProbability() = 1.0

        @ConfigKey("end_fluid_output_id")
        @ConfigComment(
            "Fluid outputted for The End predictions",
            "Leave empty to remove fluid output"
        )
        fun theEndFluidOutputId() = ""

        @ConfigKey("end_fluid_output_amount")
        @ConfigComment("Amount of fluid outputted for The End predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun theEndFluidOutputAmount() = 1_000

        @ConfigKey("end_fluid_output_probability")
        @ConfigComment("Probability the output fluid for The End predictions will be produced")
        @Range.Double(min = 0.01, max = 1.0)
        fun theEndFluidOutputProbability() = 1.0

        // TWILIGHT
        @ConfigKey("twilight_fluid_input_id")
        @ConfigComment(
            "Required fluid for Twilight Forest predictions",
            "Leave empty to remove fluid requirement"
        )
        fun twilightFluidInputId() = ""

        @ConfigKey("twilight_fluid_input_amount")
        @ConfigComment("Amount of fluid required for Twilight Forest predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun twilightFluidInputAmount() = 1_000

        @ConfigKey("twilight_fluid_input_probability")
        @ConfigComment("Probability the input fluid for Twilight Forest predictions will be consumed")
        @Range.Double(min = 0.01, max = 1.0)
        fun twilightFluidInputProbability() = 1.0

        @ConfigKey("twilight_fluid_output_id")
        @ConfigComment(
            "Fluid outputted for Twilight Forest predictions",
            "Leave empty to remove fluid output"
        )
        fun twilightFluidOutputId() = ""

        @ConfigKey("twilight_fluid_output_amount")
        @ConfigComment("Amount of fluid outputted for Twilight Forest predictions")
        @Range.Integer(min = 1, max = Integer.MAX_VALUE)
        fun twilightFluidOutputAmount() = 1_000

        @ConfigKey("twilight_fluid_output_probability")
        @ConfigComment("Probability the output fluid for Twilight Forest predictions will be produced")
        @Range.Double(min = 0.01, max = 1.0)
        fun twilightFluidOutputProbability() = 1.0
        /**********/

        @ConfigKey("runtime_recipes")
        @ConfigComment(
            "Whether Large Loot Fabricator recipes should be generated automatically at runtime.",
            "Disable this if you're a modpack creator and intend to manually create all recipes."
        )
        fun runtimeRecipes() = true

    }
    
}