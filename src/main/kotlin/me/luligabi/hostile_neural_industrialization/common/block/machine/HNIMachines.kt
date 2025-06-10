package me.luligabi.hostile_neural_industrialization.common.block.machine

import aztech.modern_industrialization.api.energy.CableTier
import aztech.modern_industrialization.machines.models.MachineCasing
import aztech.modern_industrialization.machines.recipe.MachineRecipeType
import com.google.common.collect.Maps
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.HNIBlocks
import me.luligabi.hostile_neural_industrialization.common.block.machine.large_loot_fabricator.LargeLootFabricatorBlockEntity
import me.luligabi.hostile_neural_industrialization.common.block.machine.large_loot_fabricator.LargeLootFabricatorRecipeType
import me.luligabi.hostile_neural_industrialization.common.block.machine.mono_loot_fabricator.MonoLootFabricatorBlockEntity
import me.luligabi.hostile_neural_industrialization.common.block.machine.mono_loot_fabricator.MonoLootFabricatorRecipeType
import me.luligabi.hostile_neural_industrialization.common.item.HNIItems
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.neoforged.neoforge.registries.DeferredRegister
import net.swedz.tesseract.neoforge.compat.mi.hook.context.listener.*

object HNIMachines {

    fun singleBlockSpecial(hook: SingleBlockSpecialMachinesMIHookContext) {

        hook.register(
            MonoLootFabricatorBlockEntity.NAME, MonoLootFabricatorBlockEntity.ID, MonoLootFabricatorBlockEntity.ID,
            CableTier.LV.casing, true, false, false, true,
            ::MonoLootFabricatorBlockEntity,
            MonoLootFabricatorBlockEntity::registerEnergyApi
        )

    }

    fun multiblockMachines(hook: MultiblockMachinesMIHookContext) {

        hook.register(
            LargeLootFabricatorBlockEntity.NAME, LargeLootFabricatorBlockEntity.ID, LargeLootFabricatorBlockEntity.ID,
            Casings.PREDICTION_MACHINE_CASING, true, false, false,
            ::LargeLootFabricatorBlockEntity,
            { LargeLootFabricatorBlockEntity.registerReiShapes() }
        )

    }

    object RecipeTypes {

        lateinit var MONO_LOOT_FABRICATOR: MachineRecipeType
        lateinit var LARGE_LOOT_FABRICATOR: MachineRecipeType


        val RECIPE_TYPES: DeferredRegister<RecipeType<*>> = DeferredRegister.create(Registries.RECIPE_TYPE, HNI.ID)
        val RECIPE_SERIALIZERS: DeferredRegister<RecipeSerializer<*>> = DeferredRegister.create(Registries.RECIPE_SERIALIZER, HNI.ID)

        private val RECIPE_TYPE_NAMES = Maps.newHashMap<MachineRecipeType, String>()

        fun create(
            hook: MachineRecipeTypesMIHookContext,
            englishName: String,
            id: String,
            creator: (ResourceLocation) -> MachineRecipeType = ::MachineRecipeType
        ): MachineRecipeType {
            val recipeType = hook.create(id, creator)
            RECIPE_TYPE_NAMES[recipeType] = englishName
            return recipeType
        }
    }

    fun recipeTypes(hook: MachineRecipeTypesMIHookContext) {
        RecipeTypes.MONO_LOOT_FABRICATOR = RecipeTypes.create(hook,
            MonoLootFabricatorBlockEntity.NAME, MonoLootFabricatorBlockEntity.ID,
            ::MonoLootFabricatorRecipeType
        ).withItemInputs().withItemOutputs()

        RecipeTypes.LARGE_LOOT_FABRICATOR = RecipeTypes.create(hook,
            LargeLootFabricatorBlockEntity.NAME, LargeLootFabricatorBlockEntity.ID,
            ::LargeLootFabricatorRecipeType
        ).withItemInputs().withItemOutputs()
    }

    object Casings {

        lateinit var PREDICTION_MACHINE_CASING: MachineCasing
    }

    fun machineCasings(hook: MachineCasingsMIHookContext) {

        Casings.PREDICTION_MACHINE_CASING = hook.registerImitateBlock("prediction_machine_casing") {
            HNIBlocks.PREDICTION_MACHINE_CASING.get()
        }

    }


    fun getMachineFromId(id: String): Item {
        return HNIItems.Registry.ITEMS.registry.get()
            .get(HNI.id(id)) ?: throw IllegalStateException("Failed to get HNI machine with ID $id")
    }

}