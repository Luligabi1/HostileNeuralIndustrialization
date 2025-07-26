package me.luligabi.hostile_neural_industrialization.common.compat.mi

import me.luligabi.hostile_neural_industrialization.common.block.machine.HNIMachines
import net.swedz.tesseract.neoforge.compat.mi.hook.MIHookEntrypoint
import net.swedz.tesseract.neoforge.compat.mi.hook.MIHookListener
import net.swedz.tesseract.neoforge.compat.mi.hook.context.listener.MachineCasingsMIHookContext
import net.swedz.tesseract.neoforge.compat.mi.hook.context.listener.MachineRecipeTypesMIHookContext
import net.swedz.tesseract.neoforge.compat.mi.hook.context.listener.MultiblockMachinesMIHookContext
import net.swedz.tesseract.neoforge.compat.mi.hook.context.listener.SingleBlockSpecialMachinesMIHookContext

@MIHookEntrypoint
class HNIHookListener: MIHookListener {

    override fun singleBlockSpecialMachines(hook: SingleBlockSpecialMachinesMIHookContext) {
        HNIMachines.singleBlockSpecial(hook)
    }

    override fun multiblockMachines(hook: MultiblockMachinesMIHookContext) {
        HNIMachines.multiblockMachines(hook)
    }

    override fun machineRecipeTypes(hook: MachineRecipeTypesMIHookContext) {
        HNIMachines.recipeTypes(hook)
    }

    override fun machineCasings(hook: MachineCasingsMIHookContext) {
        HNIMachines.machineCasings(hook)
    }

}