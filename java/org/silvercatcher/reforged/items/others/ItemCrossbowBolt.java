package org.silvercatcher.reforged.items.others;

import org.silvercatcher.reforged.api.ExtendedItem;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemCrossbowBolt extends ExtendedItem {
	
	public ItemCrossbowBolt() {
		super();
		setMaxStackSize(64);
		setUnlocalizedName("crossbow_bolt");
	}
	
	@Override
	public void registerRecipes() {
		GameRegistry.addShapedRecipe(new ItemStack(this, 4), "i ",
															 "f ",
															 'i', Items.IRON_INGOT,
															 'f', Items.FEATHER);
	}
	
}