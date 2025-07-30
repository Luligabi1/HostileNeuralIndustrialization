package me.luligabi.hostile_neural_industrialization.common.block.machine.loot_fabricator.mono

import aztech.modern_industrialization.MICapabilities
import aztech.modern_industrialization.api.energy.EnergyApi
import aztech.modern_industrialization.api.machine.holder.CrafterComponentHolder
import aztech.modern_industrialization.api.machine.holder.EnergyComponentHolder
import aztech.modern_industrialization.inventory.ConfigurableFluidStack
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
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntityType
import net.neoforged.neoforge.capabilities.Capabilities
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

                event.registerBlockEntity(
                    Capabilities.ItemHandler.BLOCK,
                    bet
                ) { be, _ -> (be as MonoLootFabricatorBlockEntity).inventory.inventory.itemStorage.itemHandler }

                event.registerBlockEntity(
                    Capabilities.FluidHandler.BLOCK,
                    bet
                ) { be, _ -> (be as MonoLootFabricatorBlockEntity).inventory.inventory.fluidStorage.fluidHandler }
            }
        }
    }

    val inventory = buildInventory()
    private val crafter = CrafterComponent(this, inventory, this)
    private val isActiveComponent = IsActiveComponent()

    private val redstoneControl = RedstoneControlComponent()
    private val casing = CasingComponent()
    private val upgrades = UpgradeComponent()
    private val overdrive = OverdriveComponent()

    private val energy = EnergyComponent(this) { casing.euCapacity }
    private val insertable = energy.buildInsertable { tier -> casing.canInsertEu(tier) }

    val lootSelector = LootSelectorComponent({ this })

    constructor(bep: BEP): this(bep,
        MachineGuiParameters.Builder(ID, true).build(),
        OrientationComponent.Params(true, true, false)
    ) {
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
            ProgressBar.Server(
                ProgressBar.Parameters(78, 34, "compress")
            ) { crafter.progress }
        )

        registerGuiComponent(
            SlotPanel.Server(this)
                .withRedstoneControl(redstoneControl)
                .withUpgrades(upgrades)
                .withCasing(casing)
                .withOverdrive(overdrive)
        )

        registerGuiComponent(AutoExtract.Server(orientation))
        registerGuiComponent(LootSelector.Server(
            object : LootSelector.Behavior {

                override fun handleClick(id: ResourceLocation) {
                    lootSelector.selectedLootId = id
                }

            },
            { lootSelector.selectedLootId },
            { getInputFabDrops() ?: emptyList() }
        ))

        registerComponents(
            energy,
            redstoneControl, casing, upgrades, overdrive,
            inventory, crafter, isActiveComponent,
            lootSelector
        )
    }

    override fun onCraft() {
        if (inventory.inventory.itemStacks[0].isEmpty) return

        val drops = getInputFabDrops()?.map { it.item } ?: return
        if (!drops.contains(BuiltInRegistries.ITEM.get(lootSelector.selectedLootId))) {
            lootSelector.selectedLootId = null
        }

    }

    private fun getInputFabDrops(): List<ItemStack>? {
        val prediction = inventory.inventory.itemStacks[0].toStack()
        val model = DataModelItem.getStoredModel(prediction).optional

        return if (model.isPresent) model.get().fabDrops else null
    }

    private fun buildInventory(): MachineInventoryComponent {

        val itemInputs = listOf(ConfigurableItemStack.standardInputSlot())
        val itemOutputs = listOf(ConfigurableItemStack.standardOutputSlot())

        val fluidInputs = listOf(ConfigurableFluidStack.standardInputSlot(16_000))
        val fluidOutputs = listOf(ConfigurableFluidStack.standardOutputSlot(16_000))

        val itemPositions = SlotPositions.Builder()
            .addSlot(56, 27) // input
            .addSlot(102, 27) // output
            .build()

        val fluidPositions = SlotPositions.Builder()
            .addSlot(56, 45) // input
            .addSlot(102, 45) // output
            .build()

        return MachineInventoryComponent(itemInputs, itemOutputs, fluidInputs, fluidOutputs, itemPositions, fluidPositions)
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


    private var inputListenerLoaded = false
    override fun tick() {
        if(level?.isClientSide == true) return

        // ugly hack due to listeners not working for machines placed before the current session... ugh
        if (!inputListenerLoaded) {
            println("inputListenerLoaded")
            lootSelector.initInputStackListener(false)
            inputListenerLoaded = true
        }

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