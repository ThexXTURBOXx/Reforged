package org.silvercatcher.reforged.items.others;

import org.silvercatcher.reforged.api.ExtendedItem;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapelessOreRecipe;

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

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(this), "string", new ItemStack(Items.ARROW),
				new ItemStack(Items.ARROW), new ItemStack(Items.ARROW), new ItemStack(Items.ARROW),
				new ItemStack(Items.ARROW), new ItemStack(Items.ARROW), new ItemStack(Items.ARROW),
				new ItemStack(Items.ARROW)));
	}
}
