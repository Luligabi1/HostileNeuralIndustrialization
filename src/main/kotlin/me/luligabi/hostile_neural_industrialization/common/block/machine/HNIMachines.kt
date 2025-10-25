package me.luligabi.hostile_neural_industrialization.common.block.machine

import aztech.modern_industrialization.api.energy.CableTier
import aztech.modern_industrialization.compat.rei.machines.SteamMode
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
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredRegister
import net.swedz.tesseract.neoforge.compat.mi.hook.context.listener.MachineCasingsMIHookContext
import net.swedz.tesseract.neoforge.compat.mi.hook.context.listener.MachineRecipeTypesMIHookContext
import net.swedz.tesseract.neoforge.compat.mi.hook.context.listener.MultiblockMachinesMIHookContext
import net.swedz.tesseract.neoforge.compat.mi.hook.context.listener.SingleBlockSpecialMachinesMIHookContext

object HNIMachines {

    fun singleBlockSpecial(hook: SingleBlockSpecialMachinesMIHookContext) {
        hook.builder(ElectricSimChamberBlockEntity.ID, ElectricSimChamberBlockEntity.NAME, ::ElectricSimChamberBlockEntity)
            .builtinModel(CableTier.LV.casing, ElectricSimChamberBlockEntity.ID, { it.front(true).active(true) })
            .registrator(ElectricSimChamberBlockEntity::registerCapabilities)
            .gui(SteamMode.ELECTRIC_ONLY, RecipeTypes.ELECTRIC_SIM_CHAMBER, {
                it.slots { slots ->
                    slots.itemInputs(57, 27, 1, 2)
                    slots.itemOutputs(103, 27, 1, 2)
                    slots.fluidInput(39, 27, 16_000)
                    slots.fluidOutput(121, 27, 16_000)
                }
                it.progressBar(79, 34, "compress")
            })
            .registerRecipeCategory()
            .registerMachine()

        hook.builder(MonoLootFabricatorBlockEntity.ID, MonoLootFabricatorBlockEntity.NAME, ::MonoLootFabricatorBlockEntity)
            .builtinModel(CableTier.LV.casing, MonoLootFabricatorBlockEntity.ID, { it.front(true).active(true) })
            .registrator(MonoLootFabricatorBlockEntity::registerCapabilities)
            .gui(SteamMode.ELECTRIC_ONLY, RecipeTypes.MONO_LOOT_FABRICATOR, {
                it.slots { slots ->
                    slots.itemInput(38, 36)
                    slots.itemOutputs(84, 27, 3, 3)
                    slots.fluidInput(38, 54, 16_000)
                    slots.fluidOutput(138, 27, 16_000)
                }
                it.progressBar(60, 44, "compress")
            })
            .registerRecipeCategory()
            .registerMachine()
    }

    fun multiblockMachines(hook: MultiblockMachinesMIHookContext) {
        hook.builder(LargeSimChamberBlockEntity.ID, LargeSimChamberBlockEntity.NAME, ::LargeSimChamberBlockEntity)
            .builtinModel(Casings.PREDICTION_MACHINE_CASING, LargeSimChamberBlockEntity.ID, { it.front(true).active(true) })
            .gui(SteamMode.ELECTRIC_ONLY, RecipeTypes.LARGE_SIM_CHAMBER, {
                it.slots { slots ->
                    slots.itemInputs(58, 27, 1, 2)
                    slots.itemOutputs(102, 27, 1, 2)
                    slots.fluidInput(40, 27, 16_000)
                    slots.fluidOutput(120, 27, 16_000)
                }
                it.progressBar(77, 33, "compress")
            })
            .registerRecipeCategory()
            .registerMultiblockShape(LargeSimChamberBlockEntity.SHAPE)
            .registerMachine()

        hook.builder(LargeLootFabricatorBlockEntity.ID, LargeLootFabricatorBlockEntity.NAME, ::LargeLootFabricatorBlockEntity)
            .builtinModel(Casings.PREDICTION_MACHINE_CASING, LargeLootFabricatorBlockEntity.ID, { it.front(true).active(true) })
            .gui(SteamMode.ELECTRIC_ONLY, RecipeTypes.LARGE_LOOT_FABRICATOR, {
                it.slots { slots ->
                    slots.itemInput(56, 35)
                    slots.itemOutputs(102, 35, 5, 4)
                    slots.fluidInput(56, 53, 16_000)
                    slots.fluidOutput(84, 89, 16_000)
                }
                it.progressBar(77, 33, "compress")
            })
            .registerRecipeCategory()
            .registerMultiblockShape(LargeLootFabricatorBlockEntity.SHAPE)
            .registerMachine()
    }

    object RecipeTypes {

        lateinit var ELECTRIC_SIM_CHAMBER: MachineRecipeType
        lateinit var LARGE_SIM_CHAMBER: MachineRecipeType

        lateinit var MONO_LOOT_FABRICATOR: MachineRecipeType
        lateinit var LARGE_LOOT_FABRICATOR: MachineRecipeType


        val RECIPE_TYPES: DeferredRegister<RecipeType<*>> = DeferredRegister.create(Registries.RECIPE_TYPE, HNI.ID)
        val RECIPE_SERIALIZERS: DeferredRegister<RecipeSerializer<*>> = DeferredRegister.create(Registries.RECIPE_SERIALIZER, HNI.ID)

        private val RECIPE_TYPE_NAMES = Maps.newHashMap<MachineRecipeType, String>()

        fun init(modBus: IEventBus) {
            RECIPE_TYPES.register(modBus)
            RECIPE_SERIALIZERS.register(modBus)
        }

        internal fun create(
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
        ).withItemInputs().withItemOutputs().withFluidInputs().withFluidOutputs()

        RecipeTypes.LARGE_SIM_CHAMBER = RecipeTypes.create(hook,
            LargeSimChamberBlockEntity.NAME, LargeSimChamberBlockEntity.ID,
            ::LargeSimChamberRecipeType
        ).withItemInputs().withItemOutputs().withFluidInputs().withFluidOutputs()

        RecipeTypes.MONO_LOOT_FABRICATOR = RecipeTypes.create(hook,
            MonoLootFabricatorBlockEntity.NAME, MonoLootFabricatorBlockEntity.ID,
            ::MonoLootFabricatorRecipeType
        ).withItemInputs().withItemOutputs().withFluidInputs().withFluidOutputs()

        RecipeTypes.LARGE_LOOT_FABRICATOR = RecipeTypes.create(hook,
            LargeLootFabricatorBlockEntity.NAME, LargeLootFabricatorBlockEntity.ID,
            ::LargeLootFabricatorRecipeType
        ).withItemInputs().withItemOutputs().withFluidInputs().withFluidOutputs()
    }

    object Casings {

        lateinit var PREDICTION_MACHINE_CASING: MachineCasing
    }

    fun machineCasings(hook: MachineCasingsMIHookContext) {
        Casings.PREDICTION_MACHINE_CASING = hook.registerImitateBlock(
            "prediction_machine_casing",
            HNIBlocks.PREDICTION_MACHINE_CASING
        )
    }


    fun getMachineFromId(id: String): Item {
        return HNIItems.Registry.ITEMS.registry.get()
            .get(HNI.id(id)) ?: throw IllegalStateException("Failed to get HNI machine with ID $id")
    }

}