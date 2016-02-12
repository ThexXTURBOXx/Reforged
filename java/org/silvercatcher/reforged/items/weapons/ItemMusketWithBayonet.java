package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.items.ItemExtension;
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
	
	public ItemExtension getKnife() {
		switch(materialDefinition.getPrefix()) {
		case "wooden": return (ItemExtension) ReforgedRegistry.WOODEN_KNIFE;
		case "stone": return (ItemExtension) ReforgedRegistry.STONE_KNIFE;
		case "golden": return (ItemExtension) ReforgedRegistry.GOLDEN_KNIFE;
		case "iron": return (ItemExtension) ReforgedRegistry.IRON_KNIFE;
		case "diamond": return (ItemExtension) ReforgedRegistry.DIAMOND_KNIFE;
		default: throw new IllegalArgumentException("The ToolMaterial called " + materialDefinition.getPrefix() + " couldn't be found");
		}
	}
	
	@Override
	public void registerRecipes() {
		GameRegistry.addShapelessRecipe(new ItemStack(this),
				new ItemStack(ReforgedRegistry.MUSKET), getKnife());
	}
	
	@Override
	public float getHitDamage() {
		
		return super.getHitDamage() + getKnife().getHitDamage();
	}
}
