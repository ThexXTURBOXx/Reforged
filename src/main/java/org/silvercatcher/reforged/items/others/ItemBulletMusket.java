package org.silvercatcher.reforged.items.others;

import org.silvercatcher.reforged.api.ExtendedItem;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ItemBulletMusket extends ExtendedItem {

	public ItemBulletMusket() {
		super();
		setMaxStackSize(64);
		setUnlocalizedName("musket_bullet");
	}

	@Override
	public boolean isWeapon() {
		return false;
	}

	@Override
	public void registerRecipes() {

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 8), "i", "g", "p", 'i', "ingotIron", 'g',
				"gunpowder", 'p', "paper"));
	}
}