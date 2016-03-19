package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.items.ItemExtension;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemMusketWithBayonet extends ItemMusket {

	protected final MaterialDefinition materialDefinition;
	
	public ItemMusketWithBayonet(ToolMaterial material) {
		
		super();
		
		this.materialDefinition = MaterialManager.getMaterialDefinition(material);
		setUnlocalizedName(materialDefinition.getPrefixedName("musket"));
	}
	
	public Item getKnife() {
		switch(materialDefinition.getPrefix()) {
		case "wooden": return ReforgedRegistry.WOODEN_KNIFE;
		case "stone": return ReforgedRegistry.STONE_KNIFE;
		case "golden": return ReforgedRegistry.GOLDEN_KNIFE;
		case "iron": return ReforgedRegistry.IRON_KNIFE;
		case "diamond": return ReforgedRegistry.DIAMOND_KNIFE;
		default: if(MaterialManager.isFullyAdded(materialDefinition.getMaterial())) {
			return MaterialManager.getItems(materialDefinition.getMaterial())[0];
		} else {
			throw new IllegalArgumentException("The ToolMaterial called " + materialDefinition.getPrefix() + " couldn't be found");
		}
		}
	}
	
	@Override
	public void registerRecipes() {
		GameRegistry.addShapelessRecipe(new ItemStack(this),
				new ItemStack(ReforgedRegistry.MUSKET), getKnife());
	}
	
	@Override
	public float getHitDamage() {
		
		return super.getHitDamage() + ((ItemExtension) getKnife()).getHitDamage();
	}
}
