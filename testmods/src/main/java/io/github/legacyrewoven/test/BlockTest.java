package io.github.legacyrewoven.test;

import io.github.legacyrewoven.mixin.registry.BlockAccessor;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;

public class BlockTest implements ModInitializer {
	@Override
	public void onInitialize() {
		Block block = new Block(Material.CHEESE, MaterialColor.DIAMOND);
		block.setTranslationKey("test.e_block");
		BlockAccessor.callRegister(460, new Identifier("legacy-rewoven-api", "e_block"), block);
	}
}
