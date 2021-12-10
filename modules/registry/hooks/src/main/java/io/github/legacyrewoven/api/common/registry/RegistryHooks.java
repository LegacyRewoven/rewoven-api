package io.github.legacyrewoven.api.common.registry;

import io.github.legacyrewoven.impl.registry.IdFinder;
import io.github.legacyrewoven.mixin.registry.BlockAccessor;
import io.github.legacyrewoven.mixin.registry.ItemAccessor;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public final class RegistryHooks {
	public static void registerBlock(Identifier id, Block block) {
		BlockAccessor.callRegister(IdFinder.nextAvailableId(Block.REGISTRY, IdFinder.BLOCK_ID_OFFSET), id, block);
	}

	public static void registerItem(Identifier id, Item item) {
		ItemAccessor.callRegister(IdFinder.nextAvailableId(Item.REGISTRY, IdFinder.ITEM_ID_OFFSET), id, item);
	}

	public static void registerBlockItem(Identifier id, BlockItem item) {
		ItemAccessor.getBlockItems().put(item.getBlock(), item);
		registerItem(id, item);
	}
}
