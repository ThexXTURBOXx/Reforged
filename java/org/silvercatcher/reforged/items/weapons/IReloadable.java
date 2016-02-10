package org.silvercatcher.reforged.items.weapons;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IReloadable {
	
	int getReloadTotal();
	
	long getReloadStarted(ItemStack stack);
}
