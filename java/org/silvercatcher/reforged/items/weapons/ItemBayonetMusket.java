package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;

import net.minecraft.item.Item.ToolMaterial;

public class ItemBayonetMusket extends ItemMusket {

	protected final MaterialDefinition materialDefinition;
	
	public ItemBayonetMusket(ToolMaterial material) {
		
		super();
		
		this.materialDefinition = MaterialManager.getMaterialDefinition(material);
	
	}
}
