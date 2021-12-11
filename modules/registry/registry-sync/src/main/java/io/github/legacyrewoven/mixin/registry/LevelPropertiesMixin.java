package io.github.legacyrewoven.mixin.registry;

import io.github.legacyrewoven.api.util.NbtType;
import io.github.legacyrewoven.impl.registry.RegistryData;
import io.github.legacyrewoven.impl.registry.RegistryDataAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelProperties;

@Mixin(LevelProperties.class)
public class LevelPropertiesMixin implements RegistryDataAccessor {
	@Unique
	private RegistryData registryData = new RegistryData();

	@Override
	public RegistryData getRegistryData() {
		return this.registryData;
	}

	@Inject(method = "<init>(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("TAIL"))
	public void tagConstructor(CompoundTag worldNbt, CallbackInfo ci) {
		if (worldNbt.contains("RegistryData", NbtType.COMPOUND)) {
			this.registryData = RegistryData.fromTag(worldNbt.getCompound("RegistryData"));
		} else {
			this.registryData = new RegistryData();
			this.registryData.load();
		}
	}

	@Inject(method = "<init>(Lnet/minecraft/world/level/LevelProperties;)V", at = @At("TAIL"))
	public void copyConstructor(LevelProperties copy, CallbackInfo ci) {
		this.registryData = new RegistryData(((RegistryDataAccessor) copy).getRegistryData());
	}

	@Inject(method = "putNbt", at = @At("TAIL"))
	public void injectNbt(CompoundTag worldNbt, CompoundTag playerData, CallbackInfo ci) {
		worldNbt.put("RegistryData", RegistryData.toTag(this.registryData));
	}
}
