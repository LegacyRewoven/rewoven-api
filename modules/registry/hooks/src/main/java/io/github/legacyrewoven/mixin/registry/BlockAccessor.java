package io.github.legacyrewoven.mixin.registry;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

@Mixin(Block.class)
public interface BlockAccessor {
	@Invoker
	static void callRegister(int id, Identifier identifier, Block block) {
		throw new UnsupportedOperationException();
	}
}
