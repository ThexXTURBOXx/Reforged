package org.silvercatcher.reforged.items.others;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.items.ExtendedItem;

public class ItemBulletMusket extends ExtendedItem {
	
	public ItemBulletMusket() {
		
		setMaxStackSize(64);
		setUnlocalizedName("musket_bullet");
		
		setCreativeTab(ReforgedMod.tabReforged);
	}

	@Override
	public void registerRecipes() {
		
	}


	@Override
	public float getHitDamage() {
		return 0;
	}
}