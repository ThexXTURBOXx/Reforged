package org.silvercatcher.reforged.items.others;

import org.silvercatcher.reforged.api.ExtendedItem;

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
	public boolean isWeapon() {
		return false;
	}

	@Override
	public void registerRecipes() {

		GameRegistry.addShapelessRecipe(new ItemStack(this), new ItemStack(Items.STRING), new ItemStack(Items.ARROW),
				new ItemStack(Items.ARROW), new ItemStack(Items.ARROW), new ItemStack(Items.ARROW),
				new ItemStack(Items.ARROW), new ItemStack(Items.ARROW), new ItemStack(Items.ARROW),
				new ItemStack(Items.ARROW));
	}
}
