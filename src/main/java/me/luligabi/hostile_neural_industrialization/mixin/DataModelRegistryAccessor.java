package me.luligabi.hostile_neural_industrialization.mixin;

import dev.shadowsoffire.hostilenetworks.data.DataModel;
import dev.shadowsoffire.hostilenetworks.data.DataModelRegistry;
import dev.shadowsoffire.hostilenetworks.data.ModelTier;
import dev.shadowsoffire.hostilenetworks.data.ModelTierRegistry;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.LinkedList;
import java.util.Map;

@Mixin(value = DataModelRegistry.class, remap = false)
public interface DataModelRegistryAccessor {

    @Accessor("modelsByType")
    Map<EntityType<?>, DataModel> getModelsByType();

}
