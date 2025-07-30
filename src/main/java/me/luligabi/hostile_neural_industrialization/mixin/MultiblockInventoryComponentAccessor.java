package me.luligabi.hostile_neural_industrialization.mixin;

import aztech.modern_industrialization.inventory.AbstractConfigurableStack;
import aztech.modern_industrialization.machines.components.MultiblockInventoryComponent;
import aztech.modern_industrialization.machines.multiblocks.HatchBlockEntity;
import aztech.modern_industrialization.thirdparty.fabrictransfer.api.storage.TransferVariant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;
import java.util.function.BiConsumer;

@Mixin(value = MultiblockInventoryComponent.class, remap = false)
public interface MultiblockInventoryComponentAccessor {

    @Invoker("rebuildList")
    <T, Stack extends AbstractConfigurableStack<T, ? extends TransferVariant<T>>> void invokeRebuildList(
            List<HatchBlockEntity> sortedHatches, List<Stack> stacks, BiConsumer<HatchBlockEntity, List<Stack>> appender);

}
