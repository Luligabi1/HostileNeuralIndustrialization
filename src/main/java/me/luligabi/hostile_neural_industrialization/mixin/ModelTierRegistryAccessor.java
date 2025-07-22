package me.luligabi.hostile_neural_industrialization.mixin;

import dev.shadowsoffire.hostilenetworks.data.ModelTier;
import dev.shadowsoffire.hostilenetworks.data.ModelTierRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.LinkedList;

@Mixin(value = ModelTierRegistry.class, remap = false)
public interface ModelTierRegistryAccessor {

    @Accessor("sorted")
    LinkedList<ModelTier> getSorted();

}
