package io.github.legacyrewoven.mixin.registry.client;

import java.io.File;
import java.net.Proxy;

import io.github.legacyrewoven.impl.registry.RegistryDataAccessor;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.world.level.LevelGeneratorType;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
@Mixin(IntegratedServer.class)
public abstract class IntegratedServerMixin extends MinecraftServer {
	@Shadow
	@Final
	private static Logger LOGGER;

	public IntegratedServerMixin(Proxy proxy, File file) {
		super(proxy, file);
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/integrated/IntegratedServer;prepareWorlds()V"), method = "setupWorld(Ljava/lang/String;Ljava/lang/String;JLnet/minecraft/world/level/LevelGeneratorType;Ljava/lang/String;)V")
	public void beforeWorldSetup(String world, String worldName, long seed, LevelGeneratorType generatorType, String generatorOptions, CallbackInfo ci) {
		LOGGER.info("Remapping Registry!");
		((RegistryDataAccessor) this.worlds[0].getLevelProperties()).getRegistryData().remap();
	}
}
