package me.luligabi.hostile_neural_industrialization.common.block.machine

import aztech.modern_industrialization.api.energy.CableTier
import aztech.modern_industrialization.machines.recipe.MachineRecipeType
import com.google.common.collect.Maps
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.machine.mono_loot_fabricator.MonoLootFabricatorBlockEntity
import me.luligabi.hostile_neural_industrialization.common.block.machine.mono_loot_fabricator.MonoLootFabricatorRecipeType
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.neoforged.neoforge.registries.DeferredRegister
import net.swedz.tesseract.neoforge.compat.mi.hook.context.listener.*


object HNIMachines {

//    fun blastFurnaceTiers(hook: BlastFurnaceTiersMIHookContext?) {
//    }
//
//    object Casings {
//        var BRONZE_PIPE: MachineCasing? = null
//        var STEEL_PIPE: MachineCasing? = null
//        var STEEL_PLATED_BRICKS: MachineCasing? = null
//        var LARGE_STEEL_CRATE: MachineCasing? = null
//    }
//
//    fun casings(hook: MachineCasingsMIHookContext) {
//        Casings.BRONZE_PIPE = hook.registerImitateBlock(
//            "bronze_pipe"
//        ) { MIMaterials.BRONZE.getPart(MIParts.MACHINE_CASING_PIPE).asBlock() }
//        Casings.STEEL_PIPE = hook.registerImitateBlock(
//            "steel_pipe"
//        ) { MIMaterials.STEEL.getPart(MIParts.MACHINE_CASING_PIPE).asBlock() }
//        Casings.STEEL_PLATED_BRICKS = hook.registerImitateBlock("steel_plated_bricks", EIBlocks.STEEL_PLATED_BRICKS)
//        Casings.LARGE_STEEL_CRATE =
//            hook.registerCubeAll("large_steel_crate", "Large Steel Crate", EI.id("block/casings/large_steel_crate"))
//    }
//

    object RecipeTypes {

        lateinit var MONO_LOOT_FABRICATOR: MachineRecipeType


        val RECIPE_TYPES: DeferredRegister<RecipeType<*>> = DeferredRegister.create(Registries.RECIPE_TYPE, HNI.ID)
        val RECIPE_SERIALIZERS: DeferredRegister<RecipeSerializer<*>> = DeferredRegister.create(Registries.RECIPE_SERIALIZER, HNI.ID)

        private val RECIPE_TYPE_NAMES = Maps.newHashMap<MachineRecipeType, String>()

        val names: Map<MachineRecipeType, String>
            get() = RECIPE_TYPE_NAMES

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
            "Mono Loot Fabricator", "mono_loot_fabricator",
            ::MonoLootFabricatorRecipeType
        ).withItemInputs().withItemOutputs()
    }

//
//    fun multiblocks(hook: MultiblockMachinesMIHookContext) {
//        hook.register(
//            "Steam Farmer", "steam_farmer", "farmer",
//            BRONZE_PLATED_BRICKS, true, true, false,
//            { SteamFarmerBlockEntity() },
//            { `__` -> SteamFarmerBlockEntity.registerReiShapes() }
//        )
//
//        hook.register(
//            "Electric Farmer", "electric_farmer", "farmer",
//            STEEL, true, true, false,
//            { ElectricFarmerBlockEntity() },
//            { `__` -> ElectricFarmerBlockEntity.registerReiShapes() }
//        )
//
//        hook.register(
//            "Processing Array", "processing_array", "processing_array",
//            CLEAN_STAINLESS_STEEL, true, false, false,
//            { ProcessingArrayBlockEntity() },
//            { `__` -> ProcessingArrayBlockEntity.registerReiShapes() }
//        )
//
//        run {
//            val fireClayBricks = SimpleMember.forBlock(MIBlock.BLOCK_DEFINITIONS[MI.id("fire_clay_bricks")])
//            val bronzePlatedBricks = SimpleMember.forBlock(MIBlock.BLOCK_DEFINITIONS[MI.id("bronze_plated_bricks")])
//            val hatches =
//                HatchFlags.Builder().with(HatchType.ITEM_INPUT, HatchType.ITEM_OUTPUT, HatchType.FLUID_INPUT).build()
//            val shape = ShapeTemplate.Builder(FIREBRICKS)
//                .add3by3(-1, fireClayBricks, false, hatches)
//                .add3by3(0, bronzePlatedBricks, true, HatchFlags.NO_HATCH)
//                .add3by3(1, bronzePlatedBricks, false, HatchFlags.NO_HATCH)
//                .build()
//            hook.register(
//                "Large Steam Furnace", "large_steam_furnace", "large_furnace",
//                BRONZE_PLATED_BRICKS, true, false, false,
//                { bep: BEP? ->
//                    SteamMultipliedCraftingMultiblockBlockEntity(
//                        bep, EI.id("large_steam_furnace"), arrayOf<ShapeTemplate>(shape),
//                        OverclockComponent.getDefaultCatalysts(),
//                        MIMachineRecipeTypes.FURNACE,
//                        EI.config().batchingMachines().largeSteamFurnaceSize(),
//                        EuCostTransformers.percentage { EI.config().batchingMachines().largeSteamFurnaceEU() }
//                    )
//                }
//            )
//            ReiMachineRecipes.registerMultiblockShape(EI.id("large_steam_furnace"), shape)
//            ReiMachineRecipes.registerWorkstation(MI.id("bronze_furnace"), EI.id("large_steam_furnace"))
//            ReiMachineRecipes.registerWorkstation(MI.id("steel_furnace"), EI.id("large_steam_furnace"))
//        }
//
//        hook.register(
//            "Large Electric Furnace", "large_electric_furnace", "large_furnace",
//            HEATPROOF, true, false, false,
//            { LargeElectricFurnaceBlockEntity() }
//        )
//        ReiMachineRecipes.registerWorkstation(MI.id("bronze_furnace"), EI.id("large_electric_furnace"))
//        ReiMachineRecipes.registerWorkstation(MI.id("steel_furnace"), EI.id("large_electric_furnace"))
//        ReiMachineRecipes.registerWorkstation(MI.id("electric_furnace"), EI.id("large_electric_furnace"))
//
//        run {
//            val bronzePlatedBricks = SimpleMember.forBlock(MIBlock.BLOCK_DEFINITIONS[MI.id("bronze_plated_bricks")])
//            val hatches =
//                HatchFlags.Builder().with(HatchType.ITEM_INPUT, HatchType.ITEM_OUTPUT, HatchType.FLUID_INPUT).build()
//            val shape =
//                ShapeTemplate.Builder(BRONZE_PLATED_BRICKS).add3by3LevelsRoofed(-1, 1, bronzePlatedBricks, hatches)
//                    .build()
//            hook.register(
//                "Large Steam Macerator", "large_steam_macerator", "large_macerator",
//                BRONZE_PLATED_BRICKS, true, false, false,
//                { bep: BEP? ->
//                    SteamMultipliedCraftingMultiblockBlockEntity(
//                        bep, EI.id("large_steam_macerator"), arrayOf<ShapeTemplate>(shape),
//                        OverclockComponent.getDefaultCatalysts(),
//                        MIMachineRecipeTypes.MACERATOR,
//                        EI.config().batchingMachines().largeSteamMaceratorSize(),
//                        EuCostTransformers.percentage { EI.config().batchingMachines().largeSteamMaceratorEU() }
//                    )
//                }
//            )
//            ReiMachineRecipes.registerMultiblockShape(EI.id("large_steam_macerator"), shape)
//            ReiMachineRecipes.registerWorkstation(MI.id("bronze_macerator"), EI.id("large_steam_macerator"))
//            ReiMachineRecipes.registerWorkstation(MI.id("steel_macerator"), EI.id("large_steam_macerator"))
//        }
//
//        run {
//            val steelPlatedBricks = SimpleMember.forBlock(EIBlocks.STEEL_PLATED_BRICKS)
//            val hatches =
//                HatchFlags.Builder().with(HatchType.ITEM_INPUT, HatchType.ITEM_OUTPUT, HatchType.ENERGY_INPUT).build()
//            val shape = ShapeTemplate.Builder(Casings.STEEL_PLATED_BRICKS)
//                .add3by3LevelsRoofed(-1, 1, steelPlatedBricks, hatches).build()
//            hook.register(
//                "Large Electric Macerator", "large_electric_macerator", "large_macerator",
//                Casings.STEEL_PLATED_BRICKS, true, false, false,
//                { bep: BEP? ->
//                    ElectricMultipliedCraftingMultiblockBlockEntity(
//                        bep, EI.id("large_electric_macerator"), arrayOf<ShapeTemplate>(shape),
//                        MachineTier.LV,
//                        MIMachineRecipeTypes.MACERATOR,
//                        EI.config().batchingMachines().largeElectricMaceratorSize(),
//                        EuCostTransformers.percentage { EI.config().batchingMachines().largeElectricMaceratorEU() }
//                    )
//                }
//            )
//            ReiMachineRecipes.registerMultiblockShape(EI.id("large_electric_macerator"), shape)
//            ReiMachineRecipes.registerWorkstation(MI.id("bronze_macerator"), EI.id("large_electric_macerator"))
//            ReiMachineRecipes.registerWorkstation(MI.id("steel_macerator"), EI.id("large_electric_macerator"))
//            ReiMachineRecipes.registerWorkstation(MI.id("electric_macerator"), EI.id("large_electric_macerator"))
//        }
//
//        hook.register(
//            "Tesla Tower", "tesla_tower", "tesla_tower",
//            CLEAN_STAINLESS_STEEL, true, false, false,
//            { TeslaTowerBlockEntity() }
//        )
//    }

//    fun singleBlockCrafting(hook: SingleBlockCraftingMachinesMIHookContext) {
//
////        hook.register(
////            "Mono Loot Fabricator", "mono_loot_fabricator", MIMachineRecipeTypes.COMPRESSOR,
////            1, 1, 0, 0,
////            { _ -> },
////            ProgressBar.Parameters(78, 34, "long_arrow"),
////            RecipeEfficiencyBar.Parameters(38, 66),
////            EnergyBar.Parameters(14, 35),
////            { items -> items.addSlot(58, 27).addSlot(102, 27) },
////            { _ ->  },
////            true, false, false,
////            TIER_ELECTRIC,
////            0
////        )
//
////        hook.register(
////            "Bending Machiner", "bending_machiner", MIMachineRecipeTypes.COMPRESSOR,
////            1, 1, 0, 0,
////            { params -> },
////            ProgressBar.Parameters(77, 34, "compress"),
////            RecipeEfficiencyBar.Parameters(38, 62),
////            EnergyBar.Parameters(18, 30),
////            { items -> items.addSlot(56, 35).addSlot(102, 35) },
////            { fluids -> },
////            true, false, false,
////            TIER_BRONZE or TIER_STEEL or TIER_ELECTRIC,
////            16
////        )
//
//    }

    fun singleBlockSpecial(hook: SingleBlockSpecialMachinesMIHookContext) {

        hook.register(
            "Mono Loot Fabricator", "mono_loot_fabricator", "mono_loot_fabricator",
            CableTier.LV.casing, true, false, false, true,
            ::MonoLootFabricatorBlockEntity,
            MonoLootFabricatorBlockEntity::registerEnergyApi
        )

    }

}