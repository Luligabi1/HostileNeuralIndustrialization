package me.luligabi.hostile_neural_industrialization.datagen.server.provider.recipe

import aztech.modern_industrialization.MIItem
import aztech.modern_industrialization.machines.recipe.MachineRecipeType
import aztech.modern_industrialization.materials.MIMaterials
import aztech.modern_industrialization.materials.part.MIParts
import dev.shadowsoffire.hostilenetworks.Hostile
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.HNIBlocks
import me.luligabi.hostile_neural_industrialization.common.block.machine.HNIMachines
import me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.large.LargeLootFabricatorBlockEntity
import me.luligabi.hostile_neural_industrialization.common.item.HNIItems
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.world.item.Items
import net.minecraft.world.level.ItemLike
import net.neoforged.neoforge.common.Tags
import net.neoforged.neoforge.data.event.GatherDataEvent
import net.swedz.tesseract.neoforge.compat.mi.recipe.MIMachineRecipeBuilder
import net.swedz.tesseract.neoforge.compat.vanilla.recipe.ShapedRecipeBuilder
import net.swedz.tesseract.neoforge.compat.vanilla.recipe.ShapelessRecipeBuilder


class HNIRecipeProvider(event: GatherDataEvent): RecipeProvider(event.generator.packOutput, event.lookupProvider) {

    override fun buildRecipes(output: RecipeOutput, lookup: HolderLookup.Provider) {

        shapeless(
            "guidebook",
            HNIItems.GUIDEBOOK.get(), 1,
            { builder -> builder
                .with(MIItem.GUIDE_BOOK.asItem())
                .with(Hostile.Items.BLANK_DATA_MODEL.value())
            },
            output
        )

        shaped(
            "prediction_machine_casing",
            HNIBlocks.PREDICTION_MACHINE_CASING.get(), 1,
            { builder -> builder
                .define('P', Hostile.Items.PREDICTION_MATRIX.value())
                .define('S', MIMaterials.STAINLESS_STEEL.getPart(MIParts.MACHINE_CASING_SPECIAL).asBlock())
                .pattern("PPP")
                .pattern("PSP")
                .pattern("PPP")
            },
            output
        )

        shaped(
            "machine/large_loot_fabricator",
            HNIMachines.getMachineFromId(LargeLootFabricatorBlockEntity.ID), 1,
            { builder -> builder
                .define('P', HNIBlocks.PREDICTION_MACHINE_CASING.get())
                .define('S', MIMaterials.STAINLESS_STEEL.getPart(MIParts.LARGE_PLATE))
                .define('F', Hostile.Blocks.LOOT_FABRICATOR.value())
                .define('M', MIItem.ADVANCED_MOTOR)
                .pattern("PSP")
                .pattern("MFM")
                .pattern("PSP")
            },
            output
        )

        buildCompatRecipes(output, lookup)

    }

    private fun buildCompatRecipes(output: RecipeOutput, lookup: HolderLookup.Provider) {

        assembler(
            "hostilenetworks/simulation_chamber",
            Hostile.Blocks.SIM_CHAMBER.value(), 1,
            { builder -> builder
                .define('G', Tags.Items.GLASS_PANES)
                .define('E', Tags.Items.ENDER_PEARLS)
                .define('O', Tags.Items.OBSIDIANS)
                .define('L', Tags.Items.GEMS_LAPIS)
                .define('C', Items.COMPARATOR)
                .pattern(" G ")
                .pattern("EOE")
                .pattern("LCL")
            },
            output
        )

        assembler(
            "hostilenetworks/loot_fabricator",
            Hostile.Blocks.LOOT_FABRICATOR.value(), 1,
            { builder -> builder
                .define('N', Tags.Items.INGOTS_NETHERITE)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('O', Tags.Items.OBSIDIANS)
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('C', Items.COMPARATOR)
                .pattern(" N ")
                .pattern("DOD")
                .pattern("GCG")
            },
            output
        )

        assembler(
            "hostilenetworks/deep_learner",
            Hostile.Items.DEEP_LEARNER.value(), 1,
            { builder -> builder
                .define('O', Tags.Items.OBSIDIANS)
                .define('C', Items.COMPARATOR)
                .define('G', Tags.Items.GLASS_PANES)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .pattern("OCO")
                .pattern("CGC")
                .pattern("ORO")
            },
            output
        )

        assembler(
            "hostilenetworks/blank_data_model",
            Hostile.Items.BLANK_DATA_MODEL.value(), 1,
            { builder -> builder
                .define('O', Items.CLAY_BALL)
                .define('C', Items.COMPARATOR)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('S', Items.SMOOTH_STONE)
                .define('G', Tags.Items.INGOTS_GOLD)
                .pattern("OCO")
                .pattern("RSR")
                .pattern("OGO")
            },
            output
        )

        assembler(
            "hostilenetworks/prediction_matrix",
            Hostile.Items.PREDICTION_MATRIX.value(), 1,
            { builder -> builder
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', Tags.Items.GLASS_PANES)
                .define('O', Items.CLAY_BALL)
                .define('G', Tags.Items.INGOTS_GOLD)
                .pattern("IP ")
                .pattern("POP")
                .pattern(" PG")
            },
            output
        )

    }


    private fun shapeless(
        path: String,
        result: ItemLike, resultCount: Int,
        crafting: (ShapelessRecipeBuilder) -> Unit,
        output: RecipeOutput
    ) {

        ShapelessRecipeBuilder().apply {
            crafting.invoke(this)
            output(result, resultCount)
            offerTo(output, HNI.id(path))
        }

    }

    private fun shaped(
        path: String,
        result: ItemLike, resultCount: Int,
        crafting: (ShapedRecipeBuilder) -> Unit,
        output: RecipeOutput,
        registerForAssembler: Boolean = true
    ) {

        val builder = ShapedRecipeBuilder().apply {
            crafting.invoke(this)
            output(result, resultCount)
            offerTo(output, HNI.id(path))
        }

        if(registerForAssembler) {
            MIMachineRecipeBuilder.fromShapedToAssembler(builder).offerTo(output, HNI.id("${path}_assembler"))
        }

    }

    private fun assembler(
        path: String,
        result: ItemLike, resultCount: Int,
        crafting: (ShapedRecipeBuilder) -> Unit,
        output: RecipeOutput
    ) {

        val builder = ShapedRecipeBuilder().apply {
            crafting.invoke(this)
            output(result, resultCount)
        }

        MIMachineRecipeBuilder.fromShapedToAssembler(builder).offerTo(output, HNI.id("${path}_assembler"))
    }

    private fun addMachineRecipe(
        path: String,
        recipeType: MachineRecipeType,
        eu: Int, duration: Int,
        crafting: (MIMachineRecipeBuilder) -> Unit,
        output: RecipeOutput
    ) {

        MIMachineRecipeBuilder(recipeType, eu, duration).apply {
            crafting.invoke(this)
            offerTo(output, HNI.id(path))
        }

    }

}