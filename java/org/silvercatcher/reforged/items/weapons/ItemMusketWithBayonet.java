package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;

import net.minecraft.item.Item.ToolMaterial;

public class ItemMusketWithBayonet extends ItemMusket {

	protected final MaterialDefinition materialDefinition;
	
	public ItemMusketWithBayonet(ToolMaterial material) {
		
		super();
		
		this.materialDefinition = MaterialManager.getMaterialDefinition(material);
		setUnlocalizedName(materialDefinition.getPrefixedName("musket"));
	}
	
	@Override
	public float getHitDamage() {
		
		return super.getHitDamage() + materialDefinition.getDamageVsEntity();
	}
}
