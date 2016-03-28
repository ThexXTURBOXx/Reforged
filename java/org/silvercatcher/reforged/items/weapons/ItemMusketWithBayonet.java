package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.api.ItemExtension;
import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemMusketWithBayonet extends ItemMusket {

	protected final MaterialDefinition materialDefinition;
	protected final boolean unbreakable;
	
	public ItemMusketWithBayonet(ToolMaterial material) {
		this(material, false);
	}
	
	public ItemMusketWithBayonet(ToolMaterial material, boolean unbreakable) {
		super();
		
		this.unbreakable = unbreakable;
		this.materialDefinition = MaterialManager.getMaterialDefinition(material);
		setUnlocalizedName(materialDefinition.getPrefixedName("musket"));
	}
	
	@Override
	public boolean isDamageable() {
		if(unbreakable) {
			return false;
		} else {
			return true;
		}
	}
	
	public ItemExtension getKnife() {
		switch(materialDefinition.getPrefix()) {
		case "wooden": return (ItemExtension) ReforgedAdditions.WOODEN_KNIFE;
		case "stone": return (ItemExtension) ReforgedAdditions.STONE_KNIFE;
		case "golden": return (ItemExtension) ReforgedAdditions.GOLDEN_KNIFE;
		case "iron": return (ItemExtension) ReforgedAdditions.IRON_KNIFE;
		case "diamond": return (ItemExtension) ReforgedAdditions.DIAMOND_KNIFE;
		default:
			if(MaterialManager.isFullyAdded(materialDefinition.getMaterial())) {
				return (ItemExtension) MaterialManager.getItems(materialDefinition.getMaterial())[0];
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
		
		return super.getHitDamage() + getKnife().getHitDamage();
	}
}
