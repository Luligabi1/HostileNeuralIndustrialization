package me.luligabi.hostile_neural_industrialization.common.block.machine.sim_chamber.large

import aztech.modern_industrialization.compat.rei.machines.ReiMachineRecipes
import aztech.modern_industrialization.machines.BEP
import aztech.modern_industrialization.machines.blockentities.multiblocks.AbstractElectricCraftingMultiblockBlockEntity
import aztech.modern_industrialization.machines.components.OrientationComponent
import aztech.modern_industrialization.machines.components.OverdriveComponent
import aztech.modern_industrialization.machines.components.UpgradeComponent
import aztech.modern_industrialization.machines.guicomponents.SlotPanel
import aztech.modern_industrialization.machines.init.MachineTier
import aztech.modern_industrialization.machines.multiblocks.HatchBlockEntity
import aztech.modern_industrialization.machines.multiblocks.HatchType
import aztech.modern_industrialization.machines.multiblocks.ShapeTemplate
import aztech.modern_industrialization.machines.multiblocks.SimpleMember
import dev.shadowsoffire.hostilenetworks.Hostile
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.machine.HNIMachines
import me.luligabi.hostile_neural_industrialization.common.block.machine.HNIMultiblockShape
import me.luligabi.hostile_neural_industrialization.common.block.machine.HNIMultiblockShape.Companion.CLEAN_STEEL_CASING
import me.luligabi.hostile_neural_industrialization.common.block.machine.HNIMultiblockShape.Companion.PREDICTION_CASING
import me.luligabi.hostile_neural_industrialization.common.block.machine.sim_chamber.HNISimChamber
import me.luligabi.hostile_neural_industrialization.mixin.MultiblockInventoryComponentAccessor
import me.luligabi.hostile_neural_industrialization.mixin.MultiblockMachineBlockEntityAccessor

class LargeSimChamberBlockEntity(bep: BEP): AbstractElectricCraftingMultiblockBlockEntity(
    bep,
    ID,
    OrientationComponent.Params(false, false, false),
    arrayOf(SHAPE)
), HNISimChamber {

    companion object : HNIMultiblockShape {

        const val ID = "large_simulation_chamber"
        const val NAME = "Large Simulation Chamber"

        fun registerReiShapes() {
            ReiMachineRecipes.registerMultiblockShape(HNI.id(ID), SHAPE)
        }

        override val pattern = listOf(
            "_###_",
            "#####",
            "#####",
            "#####",
            "_###_"
        )

        override val materialRules: Map<(Char, Int) -> Boolean, SimpleMember>
            get() = mapOf(
                { _: Char, y: Int -> y == 0 || y == 3 } to PREDICTION_CASING,
                { _: Char, y: Int -> y == 1 || y == 2 } to CLEAN_STEEL_CASING
            )

        override val controllerXOffset = -2

        private val SHAPE = ShapeTemplate.Builder(HNIMachines.Casings.PREDICTION_MACHINE_CASING)
            .addLayer(0)
            .addLayer(1)
            .addLayer(2)
            .addLayer(3)
            .build()

    }

    private val upgrades = UpgradeComponent()
    private val overdrive = OverdriveComponent()

    init {
        registerComponents(upgrades, overdrive)

        registerGuiComponent(
            SlotPanel.Server(this)
                .withRedstoneControl(redstoneControl)
                .withUpgrades(upgrades)
                .withOverdrive(overdrive)
        )
    }


    override fun onCraft() {
        val index = inventory.itemInputs.indexOfFirst { it.toStack().`is`(Hostile.Items.DATA_MODEL) }
        if (index == -1) return

        // Search the model on all input hatches
        // this might use the wrong model if there's more than one... skill issue tbh
        for (hatch in (this as MultiblockMachineBlockEntityAccessor).shapeMatcher.matchedHatches) {
            if (hatch.hatchType != HatchType.ITEM_INPUT) continue

            val index = hatch.inventory.itemStacks.indexOfFirst { it.toStack().`is`(Hostile.Items.DATA_MODEL) }
            if (index == -1) continue

            hatch.inventory.itemStacks[index].setContent(getUpdatedModel(inventory.itemInputs[index]))
            break
        }

        // refresh cache
        (inventory as MultiblockInventoryComponentAccessor).invokeRebuildList(
            shapeMatcher.matchedHatches,
            inventory.itemInputs,
            HatchBlockEntity::appendItemInputs
        )
    }

    override val dataIncreaseAmount = HNI.CONFIG.largeSimChamber().dataPerRecipeAmount()

    override fun recipeType() = HNIMachines.RecipeTypes.LARGE_SIM_CHAMBER

    override fun getBaseRecipeEu() = MachineTier.MULTIBLOCK.baseEu.toLong()

    override fun getMaxRecipeEu() = MachineTier.MULTIBLOCK.maxEu + upgrades.addMaxEUPerTick

    override fun isOverdriving() = overdrive.shouldOverdrive()

}