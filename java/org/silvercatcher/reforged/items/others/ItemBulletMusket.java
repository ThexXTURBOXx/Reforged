package org.silvercatcher.reforged.items.others;

import org.silvercatcher.reforged.items.ExtendedItem;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemBulletMusket extends ExtendedItem {
	
	public ItemBulletMusket() {
		super();
		setMaxStackSize(64);
		setUnlocalizedName("musket_bullet");
	}

	@Override
	public void registerRecipes() {
		
		GameRegistry.addShapedRecipe(new ItemStack(this, 8),
				" i ",
				" g ",
				" p ",
				'i', Items.iron_ingot,
				'g', Items.gunpowder,
				'p', Items.paper);
	}


	@Override
	public float getHitDamage() {
		return 0;
	}
}