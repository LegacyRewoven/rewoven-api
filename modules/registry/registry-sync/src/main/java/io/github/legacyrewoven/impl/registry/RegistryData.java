package io.github.legacyrewoven.impl.registry;

import java.util.IdentityHashMap;
import java.util.Set;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.github.legacyrewoven.mixin.registry.IdListAccessor;
import io.github.legacyrewoven.mixin.registry.SimpleRegistryAccessor;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.IdList;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.SimpleRegistry;

public class RegistryData {
	private final BiMap<Integer, Identifier> items;
	private final BiMap<Integer, Identifier> blocks;

	public RegistryData() {
		this.items = HashBiMap.create();
		this.blocks = HashBiMap.create();
	}

	public RegistryData(BiMap<Integer, Identifier> items, BiMap<Integer, Identifier> blocks) {
		this.items = items;
		this.blocks = blocks;
	}

	public RegistryData(RegistryData data) {
		this.items = HashBiMap.create(data.getItems());
		this.blocks = HashBiMap.create(data.getBlocks());
	}

	public BiMap<Integer, Identifier> getItems() {
		return items;
	}

	public BiMap<Integer, Identifier> getBlocks() {
		return blocks;
	}

	@SuppressWarnings("unchecked")
	public void load() {
		this.items.clear();
		this.blocks.clear();
		IdList<Item> items = (IdList<Item>) ((SimpleRegistryAccessor) Item.REGISTRY).getIds();
		IdentityHashMap<Item, Integer> itemMap = ((IdentityHashMap<Item, Integer>) ((IdListAccessor) items).getIdMap());
		itemMap.forEach((item, integer) -> this.items.put(integer, Item.REGISTRY.getIdentifier(item)));
		IdList<Block> blocks = (IdList<Block>) ((SimpleRegistryAccessor) Block.REGISTRY).getIds();
		IdentityHashMap<Block, Integer> blockMap = ((IdentityHashMap<Block, Integer>) ((IdListAccessor) blocks).getIdMap());
		blockMap.forEach((block, integer) -> this.blocks.put(integer, Block.REGISTRY.getIdentifier(block)));
	}

	public void remap() {
		remapRegistry(this.items, Item.REGISTRY, IdFinder.ITEM_ID_OFFSET);
		remapRegistry(this.blocks, Block.REGISTRY, IdFinder.BLOCK_ID_OFFSET);
	}

	@SuppressWarnings("unchecked")
	private static <T> void remapRegistry(BiMap<Integer, Identifier> map, SimpleRegistry<Identifier, T> registry, int offset) {
		BiMap<Identifier, T> entries = ((BiMap<T, Identifier>) ((SimpleRegistryAccessor) registry).getObjects()).inverse();
		IdList<T> idList = new IdList<>();
		map.forEach((integer, identifier) -> {
			T entry = entries.get(identifier);
			idList.set(entry, integer);
		});
		Set<Identifier> registeredIds = map.values();
		entries.keySet().stream().filter(id -> !registeredIds.contains(id)).forEach(id -> idList.set(entries.get(id), IdFinder.nextAvailableId(registry, offset)));
	}

	public static CompoundTag toTag(RegistryData data) {
		CompoundTag tag = new CompoundTag();
		tag.put("Items", biMapToTag(data.getItems()));
		tag.put("Blocks", biMapToTag(data.getBlocks()));
		return tag;
	}

	public static RegistryData fromTag(CompoundTag tag) {
		return new RegistryData(
				tagToBiMap(tag.getCompound("Items")),
				tagToBiMap(tag.getCompound("Blocks"))
		);
	}

	public static CompoundTag biMapToTag(BiMap<Integer, Identifier> biMap) {
		CompoundTag tag = new CompoundTag();

		biMap.keySet().forEach(integer -> {
			Identifier identifier = biMap.get(integer);
			tag.putString(integer.toString(), identifier.toString());
		});

		return tag;
	}

	public static BiMap<Integer, Identifier> tagToBiMap(CompoundTag tag) {
		BiMap<Integer, Identifier> biMap = HashBiMap.create();

		tag.getKeys().forEach(key -> {
			Identifier identifier = new Identifier(tag.getString(key));
			biMap.put(Integer.parseInt(key), identifier);
		});

		return biMap;
	}
}
