package org.silvercatcher.reforged.items.others;

import org.silvercatcher.reforged.api.ExtendedItem;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemBulletBlunderbuss extends ExtendedItem {

	public ItemBulletBlunderbuss() {
		super();
		setMaxStackSize(64);
		setUnlocalizedName("blunderbuss_bullet");
	}

	@Override
	public boolean isWeapon() {
		return false;
	}

	@Override
	public void registerRecipes() {

		GameRegistry.addShapedRecipe(new ItemStack(this, 8), " h ", " g ", " p ", 'h', new ItemStack(Blocks.GRAVEL),
				'g', Items.GUNPOWDER, 'p', Items.PAPER);
	}
}