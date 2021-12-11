package io.github.legacyrewoven.mixin.registry;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.util.IdList;
import net.minecraft.util.registry.SimpleRegistry;

@Mixin(SimpleRegistry.class)
public interface SimpleRegistryAccessor {
	@Accessor
	IdList<?> getIds();

	@Accessor
	Map<?, ?> getObjects();

	@Mutable
	@Accessor
	void setIds(IdList<?> ids);

	@Mutable
	@Accessor
	void setObjects(Map<?, ?> objects);

	@Invoker
	Map<?, ?> callCreateMap();
}
