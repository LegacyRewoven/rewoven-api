package io.github.legacyrewoven.impl.registry;

import java.util.List;

import io.github.legacyrewoven.mixin.registry.IdListAccessor;
import io.github.legacyrewoven.mixin.registry.SimpleRegistryAccessor;
import org.jetbrains.annotations.ApiStatus;

import net.minecraft.util.registry.SimpleRegistry;

@ApiStatus.Internal
public class IdFinder {
	public static final int ITEM_ID_OFFSET = 432;
	public static final int BLOCK_ID_OFFSET = 198;

	public static int nextAvailableId(SimpleRegistry<?, ?> registry, int offset) {
		List<?> list = ((IdListAccessor) ((SimpleRegistryAccessor) registry).getIds()).getList();
		for (int i = offset; i < list.size(); i++) {
			if (list.get(i) == null) {
				return i;
			}
		}
		return list.size();
	}
}
