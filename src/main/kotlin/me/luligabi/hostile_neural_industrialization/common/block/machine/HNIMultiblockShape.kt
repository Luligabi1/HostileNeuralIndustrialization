package me.luligabi.hostile_neural_industrialization.common.block.machine

import aztech.modern_industrialization.machines.multiblocks.HatchFlags
import aztech.modern_industrialization.machines.multiblocks.HatchType
import aztech.modern_industrialization.machines.multiblocks.ShapeTemplate
import aztech.modern_industrialization.machines.multiblocks.SimpleMember
import aztech.modern_industrialization.materials.MIMaterials
import aztech.modern_industrialization.materials.part.MIParts
import me.luligabi.hostile_neural_industrialization.common.block.HNIBlocks

interface HNIMultiblockShape {


    companion object {

        val ITEM_AND_ENERGY_HATCHES: HatchFlags
            get() = HatchFlags.Builder()
                .with(HatchType.ITEM_INPUT, HatchType.ITEM_OUTPUT, HatchType.ENERGY_INPUT)
                .build()

        val PREDICTION_CASING = SimpleMember.forBlock { HNIBlocks.PREDICTION_MACHINE_CASING.get() }
        val CLEAN_STEEL_CASING = SimpleMember.forBlock { MIMaterials.STAINLESS_STEEL.getPart(MIParts.MACHINE_CASING_SPECIAL).asBlock() }

    }

    /**
     * _ -> air
     * # -> layer
     * @ -> pillar
     */
    val pattern: List<String>


    val materialRules: Map<(Char, Int) -> Boolean, SimpleMember>
        get() = mapOf(
            { type: Char, y: Int -> true } to PREDICTION_CASING
        )

    val controllerXOffset: Int
        get() = 0
    val controllerZOffset: Int
        get() = 0

    fun ShapeTemplate.Builder.addLayer(y: Int): ShapeTemplate.Builder {

        for (z in pattern.indices) {
            val row = pattern[z]
            for (x in row.indices) {
                if (row[x] == '_') continue

                val block = materialRules.entries.find { it.key(row[x], y) }?.value ?: continue
                add(x + controllerXOffset, y, z + controllerZOffset, block, if (block == PREDICTION_CASING) ITEM_AND_ENERGY_HATCHES else null)
            }
        }

        return this
    }

}