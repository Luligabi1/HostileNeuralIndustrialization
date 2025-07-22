package me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.mono

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
import aztech.modern_industrialization.util.Simulation
import aztech.modern_industrialization.util.Tickable
import dev.shadowsoffire.hostilenetworks.item.DataModelItem
import me.luligabi.hostile_neural_industrialization.common.block.machine.HNIMachines
import me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.mono.loot_selector.LootSelector
import me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.mono.loot_selector.LootSelectorComponent
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.block.entity.BlockEntityType
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent

class MonoLootFabricatorBlockEntity private constructor(
    bep: BEP,
    guiParams: MachineGuiParameters,
    orientationParams: OrientationComponent.Params
): MachineBlockEntity(bep, guiParams, orientationParams), EnergyComponentHolder, Tickable, CrafterComponentHolder, CrafterComponent.Behavior {

    companion object {

        const val ID = "mono_loot_fabricator"
        const val NAME = "Mono Loot Fabricator"

        fun registerEnergyApi(bet: BlockEntityType<*>) {

            MICapabilities.onEvent { event: RegisterCapabilitiesEvent ->
                event.registerBlockEntity(
                    EnergyApi.SIDED,
                    bet
                ) { be, _ -> (be as MonoLootFabricatorBlockEntity).insertable }
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

    lateinit var lootSelector: LootSelectorComponent
        private set

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

        lootSelector = LootSelectorComponent()

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
                ProgressBar.Parameters(78, 38, "compress")
            ) { crafter.progress }
        )

        registerGuiComponent(AutoExtract.Server(orientation))
        registerGuiComponent(LootSelector.Server(
            object : LootSelector.Behavior {

                override fun handleClick(index: Int) {
                    lootSelector.selectedLootIndex = index
                }

            },
            { lootSelector.selectedLootIndex },
            {
                val prediction = inventory.inventory.itemStacks[0].toStack()
                val model = DataModelItem.getStoredModel(prediction).optional

                if (model.isPresent) model.get().fabDrops else emptyList()
            }
        ))

        registerComponents(
            energy,
            redstoneControl, casing, upgrades, overdrive,
            inventory, crafter, isActiveComponent,
            lootSelector
        )

    }

    private fun buildInventory(): MachineInventoryComponent {

        val itemInputs = listOf(MonoLootFabricatorInputConfigurableItemStack.create({ this }))
        val itemOutputs = listOf(ConfigurableItemStack.standardOutputSlot())

        val itemPositions = SlotPositions.Builder()
            .addSlot(56, 39) // input
            .addSlot(102, 39) // output
            .build()

        return MachineInventoryComponent(itemInputs, itemOutputs, emptyList(), emptyList(), itemPositions, SlotPositions.empty())
    }

    override fun getInventory(): MIInventory = inventory.inventory

    override fun recipeType() = HNIMachines.RecipeTypes.MONO_LOOT_FABRICATOR

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