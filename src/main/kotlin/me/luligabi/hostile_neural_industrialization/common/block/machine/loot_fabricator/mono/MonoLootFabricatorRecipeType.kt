package me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.mono

import aztech.modern_industrialization.machines.recipe.MachineRecipe
import aztech.modern_industrialization.machines.recipe.ProxyableMachineRecipeType
import aztech.modern_industrialization.thirdparty.fabrictransfer.api.item.ItemVariant
import dev.shadowsoffire.hostilenetworks.data.DataModel
import dev.shadowsoffire.hostilenetworks.data.DataModelRegistry
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.PredictionIngredient
import me.luligabi.hostile_neural_industrialization.common.util.getDimensionFluid
import me.luligabi.hostile_neural_industrialization.common.util.isModelRegistryLoaded
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeHolder
import net.minecraft.world.level.Level
import net.swedz.tesseract.neoforge.compat.mi.recipe.MIMachineRecipeBuilder

class MonoLootFabricatorRecipeType(id: ResourceLocation): ProxyableMachineRecipeType(id) {

    private fun generate(
        id: ResourceLocation,
        model: DataModel,
        outputLoot: ItemStack
    ): RecipeHolder<MachineRecipe> {

        val recipeBuilder = MIMachineRecipeBuilder(
            this,
            HNI.config().monoLootFabricator().energy(),
            HNI.config().monoLootFabricator().duration()
        ).apply {
            addItemInput(PredictionIngredient(model).toVanilla(), 1, 1f)

            model.getDimensionFluid(
                HNI.config().monoLootFabricator().overworldFluidInputId(), HNI.config().monoLootFabricator().overworldFluidInputAmount(), HNI.config().monoLootFabricator().overworldFluidInputProbability().toFloat(),
                HNI.config().monoLootFabricator().netherFluidInputId(), HNI.config().monoLootFabricator().netherFluidInputAmount(), HNI.config().monoLootFabricator().netherFluidInputProbability().toFloat(),
                HNI.config().monoLootFabricator().theEndFluidInputId(), HNI.config().monoLootFabricator().theEndFluidInputAmount(), HNI.config().monoLootFabricator().theEndFluidInputProbability().toFloat(),
                HNI.config().monoLootFabricator().twilightFluidInputId(), HNI.config().monoLootFabricator().twilightFluidInputAmount(), HNI.config().monoLootFabricator().twilightFluidInputProbability().toFloat()
            )?.let { addFluidInput(it.first, it.second, it.third) }
            
            val outputAmount = (outputLoot.count * HNI.config().monoLootFabricator().outputAmountMultiplier()).toInt().coerceAtMost(64)
            if (outputAmount > 0) addItemOutput(ItemVariant.of(outputLoot), outputAmount, 1f)

            model.getDimensionFluid(
                HNI.config().monoLootFabricator().overworldFluidOutputId(), HNI.config().monoLootFabricator().overworldFluidOutputAmount(), HNI.config().monoLootFabricator().overworldFluidOutputProbability().toFloat(),
                HNI.config().monoLootFabricator().netherFluidOutputId(), HNI.config().monoLootFabricator().netherFluidOutputAmount(), HNI.config().monoLootFabricator().netherFluidOutputProbability().toFloat(),
                HNI.config().monoLootFabricator().theEndFluidOutputId(), HNI.config().monoLootFabricator().theEndFluidOutputAmount(), HNI.config().monoLootFabricator().theEndFluidOutputProbability().toFloat(),
                HNI.config().monoLootFabricator().twilightFluidOutputId(), HNI.config().monoLootFabricator().twilightFluidOutputAmount(), HNI.config().monoLootFabricator().twilightFluidOutputProbability().toFloat()
            )?.let { addFluidOutput(it.first, it.second, it.third) }
        }

        val recipe = (recipeBuilder.convert() as MachineRecipe).apply {
            conditions = listOf(LootIdProcessCondition(BuiltInRegistries.ITEM.getKey(outputLoot.item)))
        }
        return RecipeHolder(id, recipe)
    }

    private fun getPredictionRecipes(): MutableList<RecipeHolder<MachineRecipe>> {
        if (!isModelRegistryLoaded()) return mutableListOf()

        val recipes = mutableListOf<RecipeHolder<MachineRecipe>>()
        for (model in DataModelRegistry.INSTANCE.values) {

            model.fabDrops.forEach {

                val entityId = BuiltInRegistries.ENTITY_TYPE.getKey(model.entity)
                val itemId = BuiltInRegistries.ITEM.getKey(it.item)

                recipes.add(
                    generate(
                        ResourceLocation.parse("${HNI.ID}:/mono_loot_fabricator/${entityId.namespace}/${entityId.path}/${itemId.namespace}/${itemId.path}"),
                        model, it
                    )
                )

            }

        }
        return recipes
    }

    override fun fillRecipeList(level: Level, recipeList: MutableList<RecipeHolder<MachineRecipe>>) {
        recipeList.addAll(getManagerRecipes(level))
        if (HNI.config().monoLootFabricator().runtimeRecipes()) {
            recipeList.addAll(getPredictionRecipes())
        }
    }

}