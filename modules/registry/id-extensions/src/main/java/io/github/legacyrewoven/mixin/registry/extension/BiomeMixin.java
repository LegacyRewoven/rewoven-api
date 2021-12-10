package io.github.legacyrewoven.mixin.registry.extension;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;

@Mixin(Block.class)
public abstract class BiomeMixin {
	@Shadow
	private static void register(int id, String name, Block block) {
	}

	@Inject(method = "setup", at = @At(value = "HEAD"))
	private static void init(CallbackInfo ci) {
		register(2000, "e_block", new Block(Material.CHEESE, MaterialColor.DIAMOND));
	}
}
