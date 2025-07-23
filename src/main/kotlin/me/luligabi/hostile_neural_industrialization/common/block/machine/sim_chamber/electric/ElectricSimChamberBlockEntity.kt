package me.luligabi.hostile_neural_industrialization.common.block.machine.sim_chamber.electric

import aztech.modern_industrialization.MICapabilities
import aztech.modern_industrialization.api.energy.EnergyApi
import aztech.modern_industrialization.api.energy.MIEnergyStorage
import aztech.modern_industrialization.api.machine.holder.CrafterComponentHolder
import aztech.modern_industrialization.api.machine.holder.EnergyComponentHolder
import aztech.modern_industrialization.inventory.ConfigurableItemStack
import aztech.modern_industrialization.inventory.MIInventory
import aztech.modern_industrialization.inventory.SlotPositions
import aztech.modern_industrialization.machines.BEP
import aztech.modern_industrialization.machines.MachineBlockEntity
import aztech.modern_industrialization.machines.components.*
import aztech.modern_industrialization.machines.gui.MachineGuiParameters
import aztech.modern_industrialization.machines.guicomponents.*
import aztech.modern_industrialization.machines.init.MachineTier
import aztech.modern_industrialization.machines.models.MachineModelClientData
import aztech.modern_industrialization.machines.multiblocks.HatchBlockEntity
import aztech.modern_industrialization.machines.multiblocks.HatchType
import aztech.modern_industrialization.thirdparty.fabrictransfer.api.item.ItemVariant
import aztech.modern_industrialization.util.Simulation
import aztech.modern_industrialization.util.Tickable
import dev.shadowsoffire.hostilenetworks.Hostile
import dev.shadowsoffire.hostilenetworks.HostileConfig
import dev.shadowsoffire.hostilenetworks.data.DataModelInstance
import dev.shadowsoffire.hostilenetworks.item.DataModelItem
import me.luligabi.hostile_neural_industrialization.common.HNI
import me.luligabi.hostile_neural_industrialization.common.block.machine.HNIMachines
import me.luligabi.hostile_neural_industrialization.common.block.machine.sim_chamber.HNISimChamber
import me.luligabi.hostile_neural_industrialization.mixin.AbstractConfigurableStackAccessor
import me.luligabi.hostile_neural_industrialization.mixin.MultiblockInventoryComponentAccessor
import me.luligabi.hostile_neural_industrialization.mixin.MultiblockMachineBlockEntityAccessor
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.block.entity.BlockEntityType
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent

class ElectricSimChamberBlockEntity private constructor(
    bep: BEP,
    guiParams: MachineGuiParameters,
    orientationParams: OrientationComponent.Params
): MachineBlockEntity(bep, guiParams, orientationParams), EnergyComponentHolder, Tickable, CrafterComponentHolder, CrafterComponent.Behavior, HNISimChamber {

    companion object {

        const val ID = "electric_simulation_chamber"
        const val NAME = "Electric Simulation Chamber"

        fun registerEnergyApi(bet: BlockEntityType<*>) {

            MICapabilities.onEvent { event: RegisterCapabilitiesEvent ->
                event.registerBlockEntity(
                    EnergyApi.SIDED,
                    bet
                ) { be, _ -> (be as ElectricSimChamberBlockEntity).insertable }
            }
        }
    }

    private lateinit var inventory: MachineInventoryComponent
    private lateinit var crafter: CrafterComponent
    lateinit var isActiveComponent: IsActiveComponent
        private set

    private lateinit var redstoneControl: RedstoneControlComponent
    private lateinit var casing: CasingComponent
    private lateinit var upgrades: UpgradeComponent
    private lateinit var overdrive: OverdriveComponent

    private lateinit var energy: EnergyComponent
    private lateinit var insertable: MIEnergyStorage

    constructor(bep: BEP): this(bep,
        MachineGuiParameters.Builder(ID, true).build(),
        OrientationComponent.Params(true, true, false)
    ) {

        inventory = buildInventory()
        crafter = CrafterComponent(this, inventory, this)
        isActiveComponent = IsActiveComponent()

        redstoneControl = RedstoneControlComponent()
        casing = CasingComponent()
        upgrades = UpgradeComponent()
        overdrive = OverdriveComponent()

        energy = EnergyComponent(this) { casing.euCapacity }
        insertable = energy.buildInsertable { tier -> casing.canInsertEu(tier) }

        registerGuiComponent(
            EnergyBar.Server(
                EnergyBar.Parameters(14, 35),
                { energy.eu },
                { energy.capacity })
        )
        registerGuiComponent(
            RecipeEfficiencyBar.Server(
                RecipeEfficiencyBar.Parameters(
                    38,
                    66
                ), crafter
            )
        )
        registerGuiComponent(
            SlotPanel.Server(this)
                .withRedstoneControl(redstoneControl)
                .withUpgrades(upgrades)
                .withCasing(casing)
                .withOverdrive(overdrive)
        )

        registerGuiComponent(
            ProgressBar.Server(
                ProgressBar.Parameters(78, 34, "compress")
            ) { crafter.progress }
        )

        registerGuiComponent(AutoExtract.Server(orientation))

        registerComponents(
            energy,
            redstoneControl, casing, upgrades, overdrive,
            inventory, crafter, isActiveComponent
        )

    }

    override fun onCraft() {
        val input = inventory.itemInputs[0]
        if (!input.toStack().`is`(Hostile.Items.DATA_MODEL)) return

        inventory.itemInputs[0].setContent(getUpdatedModel(input))
    }

    private fun buildInventory(): MachineInventoryComponent {

        val itemInputs = listOf(ConfigurableItemStack.standardInputSlot(), ConfigurableItemStack.standardInputSlot())
        val itemOutputs = listOf(ConfigurableItemStack.standardOutputSlot(), ConfigurableItemStack.standardInputSlot())

        val itemPositions = SlotPositions.Builder()
            .addSlots(56, 27, 1, 2) // input
            .addSlots(102, 27, 1, 2) // output
            .build()

        return MachineInventoryComponent(itemInputs, itemOutputs, emptyList(), emptyList(), itemPositions, SlotPositions.empty())
    }

    override fun getInventory(): MIInventory = inventory.inventory

    override fun recipeType() = HNIMachines.RecipeTypes.ELECTRIC_SIM_CHAMBER

    override fun getMachineModelData(): MachineModelClientData {

        val data = MachineModelClientData(casing.casing).apply {
            orientation.writeModelData(this)
            isActive = isActiveComponent.isActive
        }
        return data
    }

    override fun tick() {
        if(level?.isClientSide == true) return

        val active = crafter.tickRecipe()
        isActiveComponent.updateActive(active, this)

        if(orientation.extractItems) {
            getInventory().autoExtractItems(level, worldPosition, orientation.outputDirection)
        }

        setChanged()
    }

    override fun consumeEu(max: Long, simulation: Simulation) = energy.consumeEu(max, simulation)

    override fun getBaseRecipeEu() = MachineTier.LV.baseEu.toLong()

    override fun getMaxRecipeEu() = MachineTier.LV.maxEu + upgrades.addMaxEUPerTick

    override fun getEnergyComponent() = energy

    override fun getCrafterComponent() = crafter

    override fun getCrafterWorld() = level as? ServerLevel

    override fun getOwnerUuid() = placedBy.placerId

}