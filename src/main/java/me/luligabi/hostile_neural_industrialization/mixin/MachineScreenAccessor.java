package me.luligabi.hostile_neural_industrialization.mixin;

import aztech.modern_industrialization.machines.gui.ClientComponentRenderer;
import aztech.modern_industrialization.machines.gui.MachineScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = MachineScreen.class, remap = false)
public interface MachineScreenAccessor {

    @Accessor("renderers")
    List<ClientComponentRenderer> getRenderers();

}
