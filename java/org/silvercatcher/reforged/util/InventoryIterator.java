package org.silvercatcher.reforged.util;

import org.silvercatcher.reforged.items.others.ItemDart;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class InventoryIterator {
	
	public static int getNearestDart(EntityPlayer pl) {
		InventoryPlayer inv = pl.inventory;
		for(int c = 1; c<=36; c++) {
			if(inv.getStackInSlot(c).getItem() instanceof ItemDart) {
				return c;
			}
		}
		return 0;
	}
}