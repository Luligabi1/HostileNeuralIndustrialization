package me.luligabi.hostile_neural_industrialization.mixin;

import aztech.modern_industrialization.compat.rei.machines.MachineCategoryParams;
import aztech.modern_industrialization.inventory.AbstractConfigurableStack;
import aztech.modern_industrialization.inventory.ChangeListener;
import aztech.modern_industrialization.machines.components.MultiblockInventoryComponent;
import aztech.modern_industrialization.machines.multiblocks.HatchBlockEntity;
import aztech.modern_industrialization.machines.recipe.MachineRecipeType;
import aztech.modern_industrialization.thirdparty.fabrictransfer.api.storage.TransferVariant;
import net.swedz.tesseract.neoforge.compat.mi.hack.HackedMachineRegistrationHelper;
import net.swedz.tesseract.neoforge.compat.mi.hook.MIHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;
import java.util.function.BiConsumer;

@Mixin(value = HackedMachineRegistrationHelper.class, remap = false)
public interface HackedMachineRegistrationHelperAccessor {

    @Invoker("registerReiTiers")
    static void invokeRegisterReiTiers(MIHook hook, String englishName, String machine, MachineRecipeType recipeType, MachineCategoryParams categoryParams, int tiers) {
        throw new AssertionError();
    }
}
