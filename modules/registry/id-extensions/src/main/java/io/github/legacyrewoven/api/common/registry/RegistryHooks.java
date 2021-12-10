package io.github.legacyrewoven.api.common.registry;

import io.github.legacyrewoven.mixin.registry.extension.ItemAccessor;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public final class RegistryHooks {
	public static int registerBlock(Identifier id, Block block) {
		// TODO
		return -1;
	}

	public static int registerItem(Identifier id, Item item) {
		// TODO
		return -1;
	}

	public static int registerBlockItem(Identifier id, BlockItem item) {
		ItemAccessor.getBlockItems().put(item.getBlock(), item);
		return registerItem(id, item);
	}
}
