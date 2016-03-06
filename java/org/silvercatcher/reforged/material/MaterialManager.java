package org.silvercatcher.reforged.material;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.item.Item.ToolMaterial;

public class MaterialManager {	

	private static final HashMap<ToolMaterial, MaterialDefinition> definitionMap = new HashMap<ToolMaterial, MaterialDefinition>();
	
	static {
		definitionMap.put(ToolMaterial.WOOD,
				new MaterialDefinition("wooden", ToolMaterial.WOOD));
		definitionMap.put(ToolMaterial.STONE,
				new MaterialDefinition("stone", ToolMaterial.STONE));
		definitionMap.put(ToolMaterial.IRON,
				new MaterialDefinition("iron", ToolMaterial.IRON));
		definitionMap.put(ToolMaterial.GOLD,
				new MaterialDefinition("golden", ToolMaterial.GOLD));
		definitionMap.put(ToolMaterial.EMERALD,
				new MaterialDefinition("diamond", ToolMaterial.EMERALD));
	}
	
	public static Set<Entry<ToolMaterial, MaterialDefinition>> getEntries() {
		return definitionMap.entrySet();
	}
	
	public static MaterialDefinition getMaterialDefinition(ToolMaterial material) {
		return definitionMap.get(material);
	}
	
	public static void addMaterialDefinition(ToolMaterial material, MaterialDefinition definition) {
		definitionMap.put(material, definition);
	}
}
