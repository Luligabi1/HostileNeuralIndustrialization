package me.luligabi.hostile_neural_industrialization.common.block.machine.sim_chamber.large

import aztech.modern_industrialization.compat.rei.machines.ReiMachineRecipes
import aztech.modern_industrialization.inventory.ConfigurableItemStack
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
import aztech.modern_industrialization.thirdparty.fabrictransfer.api.item.ItemVariant
import dev.shadowsoffire.hostilenetworks.Hostile
import dev.shadowsoffire.hostilenetworks.HostileConfig
import dev.shadowsoffire.hostilenetworks.data.DataModelInstance
import dev.shadowsoffire.hostilenetworks.item.DataModelItem
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.machine.HNIMachines
import me.luligabi.hostile_neural_industrialization.common.block.machine.HNIMultiblockShape
import me.luligabi.hostile_neural_industrialization.common.block.machine.HNIMultiblockShape.Companion.CLEAN_STEEL_CASING
import me.luligabi.hostile_neural_industrialization.common.block.machine.HNIMultiblockShape.Companion.PREDICTION_CASING
import me.luligabi.hostile_neural_industrialization.mixin.MultiblockInventoryComponentAccessor
import me.luligabi.hostile_neural_industrialization.mixin.MultiblockMachineBlockEntityAccessor

class LargeSimChamberBlockEntity(bep: BEP): AbstractElectricCraftingMultiblockBlockEntity(
    bep,
    ID,
    OrientationComponent.Params(false, false, false),
    arrayOf(SHAPE)
) {

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

        val newModel = inventory.itemInputs[index].toStack().let {

            val model = DataModelInstance(it, 0)
            val tier = model.getTier()
            if (!tier.isMax && HostileConfig.simModelUpgrade > 0) {
                val newData = model.getData() + HNI.CONFIG.largeSimChamber().inputPerRecipeAmount()
                if (HostileConfig.simModelUpgrade != 2 || newData <= model.nextTierData) {
                    model.setData(newData)
                }
            }
            DataModelItem.setIters(it, DataModelItem.getIters(it) + 1)
            it
        }

        val modelVariant = ConfigurableItemStack().apply {
            setKey(ItemVariant.of(newModel))
            amount = 1
        }


        for (hatch in (this as MultiblockMachineBlockEntityAccessor).shapeMatcher.matchedHatches) {
            if (hatch.hatchType != HatchType.ITEM_INPUT) continue

            val index = hatch.inventory.itemStacks.indexOfFirst { it.toStack().`is`(Hostile.Items.DATA_MODEL) }
            if (index == -1) continue

            hatch.inventory.itemStacks[index] = modelVariant
            break
        }

        inventory.itemInputs[index] = modelVariant
        (inventory as MultiblockInventoryComponentAccessor).invokeRebuildList(
            shapeMatcher.matchedHatches,
            inventory.itemInputs,
            HatchBlockEntity::appendItemInputs
        )

    }

    override fun recipeType() = HNIMachines.RecipeTypes.LARGE_SIM_CHAMBER

    override fun getBaseRecipeEu() = MachineTier.MULTIBLOCK.baseEu.toLong()

    override fun getMaxRecipeEu() = MachineTier.MULTIBLOCK.maxEu + upgrades.addMaxEUPerTick

    override fun isOverdriving() = overdrive.shouldOverdrive()

}