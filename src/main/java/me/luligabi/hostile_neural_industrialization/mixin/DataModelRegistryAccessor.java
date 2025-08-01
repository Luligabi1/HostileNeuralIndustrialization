package me.luligabi.hostile_neural_industrialization.mixin;

import com.google.common.collect.Multimap;
import dev.shadowsoffire.hostilenetworks.data.DataModel;
import dev.shadowsoffire.hostilenetworks.data.DataModelRegistry;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = DataModelRegistry.class, remap = false)
public interface DataModelRegistryAccessor {

    @Accessor("modelsByType")
    Multimap<EntityType<?>, DataModel> getModelsByType();

}