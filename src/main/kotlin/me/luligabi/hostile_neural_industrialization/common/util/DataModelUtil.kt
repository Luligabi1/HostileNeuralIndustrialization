package me.luligabi.hostile_neural_industrialization.common.util

import dev.shadowsoffire.hostilenetworks.Hostile
import dev.shadowsoffire.hostilenetworks.data.DataModel
import me.luligabi.hostile_neural_industrialization.common.HNI
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.material.Fluid

val DataModel.electricSimChamberCost: Int
    get() = (simCost * HNI.CONFIG.electricSimChamber().energyMultiplier()).toInt()

val DataModel.largeSimChamberCost: Int
    get() = (simCost * HNI.CONFIG.largeSimChamber().energyMultiplier()).toInt()

val DataModel.largeLootFabricatorCost: Int
    get() = (simCost * HNI.CONFIG.largeLootFabricator().energyMultiplier()).toInt()

fun DataModel.getDimensionFluid(
    overworldFluid: String, overworldAmount: Int, overworldProbability: Float,
    netherFluid: String, netherAmount: Int, netherProbability: Float,
    theEndFluid: String, theEndAmount: Int, theEndProbability: Float,
    twilightFluid: String, twilightAmount: Int, twilightProbability: Float
): Triple<Fluid, Int, Float>? {
    if (overworldFluid.isBlank() && netherFluid.isBlank()
        && theEndFluid.isBlank() && twilightFluid.isBlank()) return null

    return when {
        baseDrop.`is`(Hostile.Items.OVERWORLD_PREDICTION) -> parseFluid(overworldFluid, overworldAmount, overworldProbability)
        baseDrop.`is`(Hostile.Items.NETHER_PREDICTION) -> parseFluid(netherFluid, netherAmount, netherProbability)
        baseDrop.`is`(Hostile.Items.END_PREDICTION) -> parseFluid(theEndFluid, theEndAmount, theEndProbability)
        baseDrop.`is`(Hostile.Items.TWILIGHT_PREDICTION) -> parseFluid(twilightFluid, twilightAmount, twilightProbability)
        else -> null
    }
}

private fun parseFluid(fluidId: String, amount: Int, probability: Float): Triple<Fluid, Int, Float>? {
    if (fluidId.isBlank()) return null
    val id = ResourceLocation.tryParse(fluidId) ?: return null

    val fluid = BuiltInRegistries.FLUID.get(ResourceKey.create(BuiltInRegistries.FLUID.key(), id))
    if (fluid == null) return null

    return Triple(fluid, amount, probability)
}