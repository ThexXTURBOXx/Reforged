package org.silvercatcher.reforged.items.weapons;

import net.minecraft.item.ItemStack;

public interface IReloadable {
	
	int getReloadTotal();
	
	long getReloadStarted(ItemStack stack);
}
