package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemMusketWithBayonet extends ItemMusket {

	protected final MaterialDefinition materialDefinition;
	
	public ItemMusketWithBayonet(ToolMaterial material) {
		
		super();
		
		this.materialDefinition = MaterialManager.getMaterialDefinition(material);
		setUnlocalizedName(materialDefinition.getPrefixedName("musket"));
	}
	
	@Override
	public void registerRecipes() {
	
		// todo: make it work with knifes
		GameRegistry.addShapelessRecipe(new ItemStack(this),
				new ItemStack(ReforgedRegistry.MUSKET), materialDefinition.getRepairMaterial());
	}
	
	@Override
	public float getHitDamage() {
		
		return super.getHitDamage() + materialDefinition.getDamageVsEntity();
	}
}
