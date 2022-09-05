package org.silvercatcher.reforged.material;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.ResourceLocation;

/**
 * Little note by and for ThexXTURBOXx 0: Knife Item, Boomerang Texture
 */
public class MaterialManager {

    private static final HashMap<ToolMaterial, MaterialDefinition> definitionMap = new HashMap<>();
    private static final HashMap<ToolMaterial, Item[]> itemMap = new HashMap<>();
    private static final HashMap<ToolMaterial, ResourceLocation[]> textureMap = new HashMap<>();

    static {
        definitionMap.put(ToolMaterial.WOOD, new MaterialDefinition("wooden", ToolMaterial.WOOD, "plankWood"));
        definitionMap.put(ToolMaterial.STONE, new MaterialDefinition("stone", ToolMaterial.STONE, "cobblestone"));
        definitionMap.put(ToolMaterial.IRON, new MaterialDefinition("iron", ToolMaterial.IRON, "ingotIron"));
        definitionMap.put(ToolMaterial.GOLD, new MaterialDefinition("golden", ToolMaterial.GOLD, "ingotGold"));
        definitionMap.put(ToolMaterial.DIAMOND, new MaterialDefinition("diamond", ToolMaterial.DIAMOND, "gemDiamond"));
    }

    public static void addMaterialDefinition(ToolMaterial material, MaterialDefinition definition) {
        definitionMap.put(material, definition);
    }

    public static void addOthers(ToolMaterial material, ResourceLocation[] textures, Item[] items) {
        textureMap.put(material, textures);
        itemMap.put(material, items);
    }

    public static Set<Entry<ToolMaterial, MaterialDefinition>> getEntries() {
        return definitionMap.entrySet();
    }

    public static Item[] getItems(ToolMaterial material) {
        return itemMap.get(material);
    }

    public static MaterialDefinition getMaterialDefinition(ToolMaterial material) {
        return definitionMap.get(material);
    }

    public static ResourceLocation[] getTextures(ToolMaterial material) {
        return textureMap.get(material);
    }

    public static boolean isFullyAdded(ToolMaterial tm) {
        return (definitionMap.containsKey(tm) && textureMap.containsKey(tm) && itemMap.containsKey(tm));
    }
}
