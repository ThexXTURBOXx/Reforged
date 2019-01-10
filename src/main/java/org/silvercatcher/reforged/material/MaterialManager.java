package org.silvercatcher.reforged.material;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.util.ResourceLocation;

/**
 * Little note by and for ThexXTURBOXx 0: Knife Item, Boomerang Texture
 */
public class MaterialManager {

	private static final HashMap<IItemTier, MaterialDefinition> definitionMap = new HashMap<>();
	private static final HashMap<IItemTier, Item[]> itemMap = new HashMap<>();
	private static final HashMap<IItemTier, ResourceLocation[]> textureMap = new HashMap<>();

	static {
		definitionMap.put(ItemTier.WOOD, new MaterialDefinition("wooden", ItemTier.WOOD));
		definitionMap.put(ItemTier.STONE, new MaterialDefinition("stone", ItemTier.STONE));
		definitionMap.put(ItemTier.IRON, new MaterialDefinition("iron", ItemTier.IRON));
		definitionMap.put(ItemTier.GOLD, new MaterialDefinition("golden", ItemTier.GOLD));
		definitionMap.put(ItemTier.DIAMOND, new MaterialDefinition("diamond", ItemTier.DIAMOND));
	}

	public static void addMaterialDefinition(IItemTier material, MaterialDefinition definition) {
		definitionMap.put(material, definition);
	}

	public static void addOthers(IItemTier material, ResourceLocation[] textures, Item[] items) {
		textureMap.put(material, textures);
		itemMap.put(material, items);
	}

	public static Set<Entry<IItemTier, MaterialDefinition>> getEntries() {
		return definitionMap.entrySet();
	}

	public static Item[] getItems(IItemTier material) {
		return itemMap.get(material);
	}

	public static MaterialDefinition getMaterialDefinition(IItemTier material) {
		return definitionMap.get(material);
	}

	public static ResourceLocation[] getTextures(IItemTier material) {
		return textureMap.get(material);
	}

	public static boolean isFullyAdded(IItemTier tm) {
		return (definitionMap.containsKey(tm) && textureMap.containsKey(tm) && itemMap.containsKey(tm));
	}
}
