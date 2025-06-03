package me.luligabi.hostile_neural_industrialization.datagen.server.provider.recipe

import aztech.modern_industrialization.MIItem
import dev.shadowsoffire.hostilenetworks.Hostile
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.item.HNIItems
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.world.level.ItemLike
import net.neoforged.neoforge.data.event.GatherDataEvent
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

    }

    private fun shapeless(name: String, result: ItemLike, resultCount: Int, crafting: (ShapelessRecipeBuilder) -> Unit, output: RecipeOutput) {

        ShapelessRecipeBuilder().apply {
            crafting.invoke(this)
            output(result, resultCount)
            offerTo(output, HNI.id(name))
        }

    }

}