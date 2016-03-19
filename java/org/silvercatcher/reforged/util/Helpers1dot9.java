package org.silvercatcher.reforged.util;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Helpers1dot9 {
	
	public static boolean consumeItem(InventoryPlayer inv, ItemStack i) {
		ItemStack[] items = getInventoryItems(inv);
		
		return true;
	}
	
	public static boolean consumeItem(InventoryPlayer inv, Item i) {
		return consumeItem(inv, i, 1);
	}
	
	public static boolean consumeItem(InventoryPlayer inv, Item i, int count) {
		return true;
	}
	
	public static ItemStack[] getInventoryItems(InventoryPlayer inv) {
		ItemStack[] items = new ItemStack[(inv.mainInventory.length + inv.offHandInventory.length)];
		int counter = -1;
		for(ItemStack istack : inv.mainInventory) {
			items[++counter] = istack;
		}
		counter = 0;
		for(ItemStack istack : inv.offHandInventory) {
			items[++counter] = istack;
		}
		return items;
	}
	
}
