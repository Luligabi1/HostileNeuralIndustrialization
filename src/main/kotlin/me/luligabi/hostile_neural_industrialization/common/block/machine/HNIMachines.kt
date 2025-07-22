package me.luligabi.hostile_neural_industrialization.common.block.machine

import aztech.modern_industrialization.api.energy.CableTier
import aztech.modern_industrialization.compat.rei.machines.MachineCategoryParams
import aztech.modern_industrialization.compat.rei.machines.SteamMode
import aztech.modern_industrialization.inventory.SlotPositions
import aztech.modern_industrialization.machines.guicomponents.EnergyBar
import aztech.modern_industrialization.machines.guicomponents.ProgressBar
import aztech.modern_industrialization.machines.guicomponents.RecipeEfficiencyBar
import aztech.modern_industrialization.machines.init.MultiblockMachines.Rei
import aztech.modern_industrialization.machines.init.SingleBlockCraftingMachines
import aztech.modern_industrialization.machines.models.MachineCasing
import aztech.modern_industrialization.machines.recipe.MachineRecipeType
import com.google.common.collect.Maps
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.HNIBlocks
import me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.large.LargeLootFabricatorBlockEntity
import me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.large.LargeLootFabricatorRecipeType
import me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.mono.MonoLootFabricatorBlockEntity
import me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.mono.MonoLootFabricatorRecipeType
import me.luligabi.hostile_neural_industrialization.common.block.machine.sim_chamber.electric.ElectricSimChamberBlockEntity
import me.luligabi.hostile_neural_industrialization.common.block.machine.sim_chamber.electric.ElectricSimChamberRecipeType
import me.luligabi.hostile_neural_industrialization.common.block.machine.sim_chamber.large.LargeSimChamberBlockEntity
import me.luligabi.hostile_neural_industrialization.common.block.machine.sim_chamber.large.LargeSimChamberRecipeType
import me.luligabi.hostile_neural_industrialization.common.item.HNIItems
import me.luligabi.hostile_neural_industrialization.mixin.HackedMachineRegistrationHelperAccessor
import me.luligabi.hostile_neural_industrialization.mixin.MIHookContextAccessor
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.neoforged.neoforge.registries.DeferredRegister
import net.swedz.tesseract.neoforge.compat.mi.hook.MIHookTracker
import net.swedz.tesseract.neoforge.compat.mi.hook.context.listener.MachineCasingsMIHookContext
import net.swedz.tesseract.neoforge.compat.mi.hook.context.listener.MachineRecipeTypesMIHookContext
import net.swedz.tesseract.neoforge.compat.mi.hook.context.listener.MultiblockMachinesMIHookContext
import net.swedz.tesseract.neoforge.compat.mi.hook.context.listener.SingleBlockSpecialMachinesMIHookContext

object HNIMachines {

    fun singleBlockSpecial(hook: SingleBlockSpecialMachinesMIHookContext) {

        hook.register(
            ElectricSimChamberBlockEntity.NAME, ElectricSimChamberBlockEntity.ID, ElectricSimChamberBlockEntity.ID,
            CableTier.LV.casing, true, false, false, true,
            ::ElectricSimChamberBlockEntity,
            ElectricSimChamberBlockEntity::registerEnergyApi
        )
        HackedMachineRegistrationHelperAccessor.invokeRegisterReiTiers(
            (hook as MIHookContextAccessor).hook,
            ElectricSimChamberBlockEntity.NAME, ElectricSimChamberBlockEntity.ID,
            RecipeTypes.ELECTRIC_SIM_CHAMBER,
            MachineCategoryParams(
                null, null,
                SlotPositions.Builder().addSlots(56, 27, 1, 2).build(),
                SlotPositions.Builder().addSlots(102, 27, 1, 2).build(),
                SlotPositions.empty(), SlotPositions.empty(),
                ProgressBar.Parameters(78, 34, "compress"),
                RecipeTypes.ELECTRIC_SIM_CHAMBER,
                null,
                false,
                SteamMode.ELECTRIC_ONLY
            ),
            SingleBlockCraftingMachines.TIER_ELECTRIC
        )

        hook.register(
            MonoLootFabricatorBlockEntity.NAME, MonoLootFabricatorBlockEntity.ID, MonoLootFabricatorBlockEntity.ID,
            CableTier.LV.casing, true, false, false, true,
            ::MonoLootFabricatorBlockEntity,
            MonoLootFabricatorBlockEntity::registerEnergyApi
        )
        HackedMachineRegistrationHelperAccessor.invokeRegisterReiTiers(
            (hook as MIHookContextAccessor).hook,
            MonoLootFabricatorBlockEntity.NAME, MonoLootFabricatorBlockEntity.ID,
            RecipeTypes.MONO_LOOT_FABRICATOR,
            MachineCategoryParams(
                null, null,
                SlotPositions.Builder().addSlot(56, 39).build(),
                SlotPositions.Builder().addSlot(102, 39).build(),
                SlotPositions.empty(), SlotPositions.empty(),
                ProgressBar.Parameters(78, 38, "compress"),
                RecipeTypes.MONO_LOOT_FABRICATOR,
                null,
                false,
                SteamMode.ELECTRIC_ONLY
            ),
            SingleBlockCraftingMachines.TIER_ELECTRIC
        )

    }

    fun multiblockMachines(hook: MultiblockMachinesMIHookContext) {

        hook.register(
            LargeSimChamberBlockEntity.NAME, LargeSimChamberBlockEntity.ID, LargeSimChamberBlockEntity.ID,
            Casings.PREDICTION_MACHINE_CASING, true, false, false, true,
            ::LargeSimChamberBlockEntity,
            { LargeSimChamberBlockEntity.registerReiShapes() }
        )
        Rei(LargeSimChamberBlockEntity.NAME, HNI.id(LargeSimChamberBlockEntity.ID), RecipeTypes.LARGE_SIM_CHAMBER, ProgressBar.Parameters(77, 33, "arrow"))
            .items(
                { it.addSlots(58, 27, 1, 2) },
                { it.addSlots(102, 27, 1, 2) }
            )
            .workstations(HNI.id(LargeSimChamberBlockEntity.ID))
            .register()
        MIHookTracker.addReiCategoryName(HNI.id(LargeSimChamberBlockEntity.ID), LargeSimChamberBlockEntity.NAME)

        hook.register(
            LargeLootFabricatorBlockEntity.NAME, LargeLootFabricatorBlockEntity.ID, LargeLootFabricatorBlockEntity.ID,
            Casings.PREDICTION_MACHINE_CASING, true, false, false, true,
            ::LargeLootFabricatorBlockEntity,
            { LargeLootFabricatorBlockEntity.registerReiShapes() }
        )
        Rei(LargeLootFabricatorBlockEntity.NAME, HNI.id(LargeLootFabricatorBlockEntity.ID), RecipeTypes.LARGE_LOOT_FABRICATOR, ProgressBar.Parameters(77, 33, "arrow"))
            .items(
                { it.addSlot(56, 35) },
                { it.addSlots(102, 35, 5, 4) }
            )
            .workstations(HNI.id(LargeLootFabricatorBlockEntity.ID))
            .register()
        MIHookTracker.addReiCategoryName(HNI.id(LargeLootFabricatorBlockEntity.ID), LargeLootFabricatorBlockEntity.NAME)

    }

    object RecipeTypes {

        lateinit var ELECTRIC_SIM_CHAMBER: MachineRecipeType
        lateinit var LARGE_SIM_CHAMBER: MachineRecipeType

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

        RecipeTypes.ELECTRIC_SIM_CHAMBER = RecipeTypes.create(hook,
            ElectricSimChamberBlockEntity.NAME, ElectricSimChamberBlockEntity.ID,
            ::ElectricSimChamberRecipeType
        ).withItemInputs().withItemOutputs()

        RecipeTypes.LARGE_SIM_CHAMBER = RecipeTypes.create(hook,
            LargeSimChamberBlockEntity.NAME, LargeSimChamberBlockEntity.ID,
            ::LargeSimChamberRecipeType
        ).withItemInputs().withItemOutputs()

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