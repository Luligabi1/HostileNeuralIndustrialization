package me.luligabi.hostile_neural_industrialization.mixin;

import aztech.modern_industrialization.inventory.AbstractConfigurableStack;
import aztech.modern_industrialization.inventory.ChangeListener;
import aztech.modern_industrialization.machines.components.MultiblockInventoryComponent;
import aztech.modern_industrialization.machines.multiblocks.HatchBlockEntity;
import aztech.modern_industrialization.thirdparty.fabrictransfer.api.storage.TransferVariant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;
import java.util.function.BiConsumer;

@Mixin(value = AbstractConfigurableStack.class, remap = false)
public interface AbstractConfigurableStackAccessor {

    @Invoker("notifyListeners")
    void invokeNotifyListeners();

}