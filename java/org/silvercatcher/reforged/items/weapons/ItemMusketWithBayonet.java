package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.api.ReforgedAdditions;
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
		case "wooden": return ReforgedAdditions.WOODEN_KNIFE;
		case "stone": return ReforgedAdditions.STONE_KNIFE;
		case "golden": return ReforgedAdditions.GOLDEN_KNIFE;
		case "iron": return ReforgedAdditions.IRON_KNIFE;
		case "diamond": return ReforgedAdditions.DIAMOND_KNIFE;
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
				new ItemStack(ReforgedAdditions.MUSKET), getKnife());
	}
	
	@Override
	public float getHitDamage() {
		
		return super.getHitDamage() + ((ItemExtension) getKnife()).getHitDamage();
	}
}
