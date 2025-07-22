package me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.large

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
import me.luligabi.hostile_neural_industrialization.common.block.machine.HNIMultiblockShape
import me.luligabi.hostile_neural_industrialization.common.block.machine.HNIMultiblockShape.Companion.CLEAN_STEEL_CASING
import me.luligabi.hostile_neural_industrialization.common.block.machine.HNIMultiblockShape.Companion.PREDICTION_CASING

class LargeLootFabricatorBlockEntity(bep: BEP): AbstractElectricCraftingMultiblockBlockEntity(
    bep,
    ID,
    OrientationComponent.Params(false, false, false),
    arrayOf(SHAPE)
) {

    companion object : HNIMultiblockShape {

        const val ID = "large_loot_fabricator"
        const val NAME = "Large Loot Fabricator"

        fun registerReiShapes() {
            ReiMachineRecipes.registerMultiblockShape(HNI.id(ID), SHAPE)
        }

        override val pattern = listOf(
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

        override val materialRules: Map<(Char, Int) -> Boolean, SimpleMember>
            get() = mapOf(
                { char: Char, y: Int -> char == '@' } to PREDICTION_CASING,
                { _: Char, y: Int -> y == -1 || y == 2 } to PREDICTION_CASING,
                { _: Char, y: Int -> y == 0 || y == 1 } to CLEAN_STEEL_CASING
            )

        override val controllerXOffset = -4

        private val SHAPE = ShapeTemplate.Builder(HNIMachines.Casings.PREDICTION_MACHINE_CASING)
            .addLayer(-1)
            .addLayer(0)
            .addLayer(1)
            .addLayer(2)
            .build()

    }

    private var upgrades = UpgradeComponent()
    private var overdrive = OverdriveComponent()

    init {
        registerComponents(upgrades, overdrive)

        registerGuiComponent(
            SlotPanel.Server(this)
                .withRedstoneControl(redstoneControl)
                .withUpgrades(upgrades)
                .withOverdrive(overdrive)
        )
    }

    override fun recipeType() = HNIMachines.RecipeTypes.LARGE_LOOT_FABRICATOR

    override fun getBaseRecipeEu() = MachineTier.MULTIBLOCK.baseEu.toLong()

    override fun getMaxRecipeEu() = MachineTier.MULTIBLOCK.maxEu + upgrades.addMaxEUPerTick

    override fun isOverdriving() = overdrive.shouldOverdrive()

}