package io.github.legacyrewoven.mixin.registry;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

@Mixin(Item.class)
public interface ItemAccessor {
	@Accessor("BLOCK_ITEMS")
	static Map<Block, Item> getBlockItems() {
		throw new UnsupportedOperationException();
	}

	@Invoker
	static void callRegister(int id, Identifier name, Item item) {
		throw new UnsupportedOperationException();
	}
}
