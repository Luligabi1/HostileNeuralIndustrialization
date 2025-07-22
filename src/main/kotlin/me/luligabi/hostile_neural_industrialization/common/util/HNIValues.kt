package me.luligabi.hostile_neural_industrialization.common.util

import dev.shadowsoffire.hostilenetworks.data.DataModel
import me.luligabi.hostile_neural_industrialization.common.HNI

// FIXME unbalanced af
/** Electric Simulation Chamber */
val DataModel.electricSimChamberCost: Int
    get() = (simCost * HNI.CONFIG.electricSimChamber().energyMultiplier()).toInt()

/** Large Simulation Chamber */
val DataModel.largeSimChamberCost: Int
    get() = (simCost * HNI.CONFIG.largeSimChamber().energyMultiplier()).toInt()

/** Mono Loot Fabricator */
val DataModel.monoLootFabricatorCost: Int
    get() = (simCost * HNI.CONFIG.monoLootFabricator().energyMultiplier()).toInt()

/** Large Loot Fabricator */
val DataModel.largeLootFabricatorCost: Int
    get() = (simCost * HNI.CONFIG.largeLootFabricator().energyMultiplier()).toInt()

