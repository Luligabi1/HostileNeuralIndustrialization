package me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.mono

import aztech.modern_industrialization.machines.recipe.MachineRecipe
import aztech.modern_industrialization.machines.recipe.ProxyableMachineRecipeType
import aztech.modern_industrialization.thirdparty.fabrictransfer.api.item.ItemVariant
import dev.shadowsoffire.hostilenetworks.data.DataModel
import dev.shadowsoffire.hostilenetworks.data.DataModelRegistry
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.PredictionIngredient
import me.luligabi.hostile_neural_industrialization.common.util.getDimensionFluid
import me.luligabi.hostile_neural_industrialization.mixin.DataModelRegistryAccessor
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
            HNI.CONFIG.monoLootFabricator().energy(),
            HNI.CONFIG.monoLootFabricator().duration()
        ).apply {
            addItemInput(PredictionIngredient(model).toVanilla(), 1, 1f)

            model.getDimensionFluid(
                HNI.CONFIG.monoLootFabricator().overworldFluidInputId(), HNI.CONFIG.monoLootFabricator().overworldFluidInputAmount(), HNI.CONFIG.monoLootFabricator().overworldFluidInputProbability().toFloat(),
                HNI.CONFIG.monoLootFabricator().netherFluidInputId(), HNI.CONFIG.monoLootFabricator().netherFluidInputAmount(), HNI.CONFIG.monoLootFabricator().netherFluidInputProbability().toFloat(),
                HNI.CONFIG.monoLootFabricator().theEndFluidInputId(), HNI.CONFIG.monoLootFabricator().theEndFluidInputAmount(), HNI.CONFIG.monoLootFabricator().theEndFluidInputProbability().toFloat(),
                HNI.CONFIG.monoLootFabricator().twilightFluidInputId(), HNI.CONFIG.monoLootFabricator().twilightFluidInputAmount(), HNI.CONFIG.monoLootFabricator().twilightFluidInputProbability().toFloat()
            )?.let { addFluidInput(it.first, it.second, it.third) }
            
            val outputAmount = (outputLoot.count * HNI.CONFIG.monoLootFabricator().outputAmountMultiplier()).toInt().coerceAtMost(64)
            if (outputAmount > 0) addItemOutput(ItemVariant.of(outputLoot), outputAmount, 1f)

            model.getDimensionFluid(
                HNI.CONFIG.monoLootFabricator().overworldFluidOutputId(), HNI.CONFIG.monoLootFabricator().overworldFluidOutputAmount(), HNI.CONFIG.monoLootFabricator().overworldFluidOutputProbability().toFloat(),
                HNI.CONFIG.monoLootFabricator().netherFluidOutputId(), HNI.CONFIG.monoLootFabricator().netherFluidOutputAmount(), HNI.CONFIG.monoLootFabricator().netherFluidOutputProbability().toFloat(),
                HNI.CONFIG.monoLootFabricator().theEndFluidOutputId(), HNI.CONFIG.monoLootFabricator().theEndFluidOutputAmount(), HNI.CONFIG.monoLootFabricator().theEndFluidOutputProbability().toFloat(),
                HNI.CONFIG.monoLootFabricator().twilightFluidOutputId(), HNI.CONFIG.monoLootFabricator().twilightFluidOutputAmount(), HNI.CONFIG.monoLootFabricator().twilightFluidOutputProbability().toFloat()
            )?.let { addFluidOutput(it.first, it.second, it.third) }
        }

        val recipe = (recipeBuilder.convert() as MachineRecipe).apply {
            conditions = listOf(LootIdProcessCondition(BuiltInRegistries.ITEM.getKey(outputLoot.item)))
        }
        return RecipeHolder(id, recipe)
    }

    private fun getPredictionRecipes(): MutableList<RecipeHolder<MachineRecipe>> {
        if ((DataModelRegistry.INSTANCE as DataModelRegistryAccessor).modelsByType.isEmpty()) return mutableListOf()

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
        if (HNI.CONFIG.monoLootFabricator().runtimeRecipes()) {
            recipeList.addAll(getPredictionRecipes())
        }
    }

}