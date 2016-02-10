package org.silvercatcher.reforged.items.others;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.items.ExtendedItem;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemArrowBundle extends ExtendedItem {

	
	public ItemArrowBundle() {
		super();
		setMaxStackSize(16);
		setUnlocalizedName("arrow_bundle");
	}
	
	@Override
	public void registerRecipes() {
		
		GameRegistry.addShapelessRecipe(new ItemStack(this),
			new ItemStack(Items.string), new ItemStack(Items.arrow), 
			new ItemStack(Items.arrow), new ItemStack(Items.arrow), 
			new ItemStack(Items.arrow), new ItemStack(Items.arrow), 
			new ItemStack(Items.arrow), new ItemStack(Items.arrow), 
			new ItemStack(Items.arrow));
	}


	@Override
	public float getHitDamage() {
		return 0f;
	}
}
