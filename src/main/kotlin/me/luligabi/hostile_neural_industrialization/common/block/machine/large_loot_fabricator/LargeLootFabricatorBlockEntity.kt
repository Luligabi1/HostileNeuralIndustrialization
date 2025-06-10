package me.luligabi.hostile_neural_industrialization.common.block.machine.large_loot_fabricator

import aztech.modern_industrialization.compat.rei.machines.ReiMachineRecipes
import aztech.modern_industrialization.machines.BEP
import aztech.modern_industrialization.machines.blockentities.multiblocks.AbstractElectricCraftingMultiblockBlockEntity
import aztech.modern_industrialization.machines.components.*
import aztech.modern_industrialization.machines.guicomponents.SlotPanel
import aztech.modern_industrialization.machines.init.MachineTier
import aztech.modern_industrialization.machines.multiblocks.HatchFlags
import aztech.modern_industrialization.machines.multiblocks.HatchType
import aztech.modern_industrialization.machines.multiblocks.ShapeTemplate
import aztech.modern_industrialization.machines.multiblocks.SimpleMember
import aztech.modern_industrialization.materials.MIMaterials
import aztech.modern_industrialization.materials.part.MIParts
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.HNIBlocks
import me.luligabi.hostile_neural_industrialization.common.block.machine.HNIMachines

class LargeLootFabricatorBlockEntity(bep: BEP): AbstractElectricCraftingMultiblockBlockEntity(
    bep,
    ID,
    OrientationComponent.Params(false, false, false),
    arrayOf(SHAPE)
) {

    companion object {

        const val ID = "large_loot_fabricator"
        const val NAME = "Large Loot Fabricator"

        fun registerReiShapes() {
            ReiMachineRecipes.registerMultiblockShape(HNI.id(ID), SHAPE)
        }

        /**
         * _ -> air
         * # -> layer
         * @ -> pillar
         * */
        private val PATTERN = listOf(
            "___#@#___",
            "_#######_",
            "_#######_",
            "#########",
            "@#######@",
            "#########",
            "_#######_",
            "_#######_",
            "___#@#___"
        )

        private val PREDICTION_CASING = SimpleMember.forBlock { HNIBlocks.PREDICTION_MACHINE_CASING.get() }
        private val CLEAN_STEEL_CASING = SimpleMember.forBlock { MIMaterials.STAINLESS_STEEL.getPart(MIParts.MACHINE_CASING_SPECIAL).asBlock() }

        private val HATCHES = HatchFlags.Builder()
            .with(HatchType.ITEM_INPUT, HatchType.ITEM_OUTPUT, HatchType.ENERGY_INPUT)
            .build()

        private val SHAPE = ShapeTemplate.Builder(HNIMachines.Casings.PREDICTION_MACHINE_CASING)
            .addLayer(-1)
            .addLayer(0)
            .addLayer(1)
            .addLayer(2)
            .build()


        private fun ShapeTemplate.Builder.addLayer(y: Int): ShapeTemplate.Builder {

            for (z in PATTERN.indices) {
                val row = PATTERN[z]
                for (x in row.indices) {
                    if (row[x] == '_') continue

                    val block = when {
                        (row[x] == '@') || (y == -1 || y == 2) -> PREDICTION_CASING
                        (y == 0 || y == 1) -> CLEAN_STEEL_CASING
                        else -> continue
                    }

                    add(x - 4, y, z, block, if (block == PREDICTION_CASING) HATCHES else null)
                }
            }

            return this
        }

    }

    private var casing = CasingComponent()
    private var upgrades = UpgradeComponent()
    private var overdrive = OverdriveComponent()

    init {

        registerComponents(casing, upgrades, overdrive)

        registerGuiComponent(
            SlotPanel.Server(this)
                .withRedstoneControl(redstoneControl)
                .withUpgrades(upgrades)
                .withCasing(casing)
                .withOverdrive(overdrive)
        )
    }

    override fun recipeType() = HNIMachines.RecipeTypes.LARGE_LOOT_FABRICATOR

    override fun getBaseRecipeEu() = MachineTier.MULTIBLOCK.baseEu.toLong()

    override fun getMaxRecipeEu() = MachineTier.MULTIBLOCK.maxEu + upgrades.addMaxEUPerTick

    override fun isOverdriving() = overdrive.shouldOverdrive()

}