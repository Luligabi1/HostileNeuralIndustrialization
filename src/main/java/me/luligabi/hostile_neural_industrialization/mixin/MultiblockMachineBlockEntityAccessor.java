package me.luligabi.hostile_neural_industrialization.mixin;

import aztech.modern_industrialization.machines.multiblocks.MultiblockMachineBlockEntity;
import aztech.modern_industrialization.machines.multiblocks.ShapeMatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = MultiblockMachineBlockEntity.class, remap = false)
public interface MultiblockMachineBlockEntityAccessor {

    @Accessor("shapeMatcher")
    ShapeMatcher getShapeMatcher();

}
