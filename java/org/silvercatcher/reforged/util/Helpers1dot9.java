package org.silvercatcher.reforged.util;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Helpers1dot9 {
	
	public static boolean consumeItem(InventoryPlayer inv, Item i) {
		return consumeItem(inv, i, 1);
	}
	
	public static boolean consumeItem(InventoryPlayer inv, Item i, int count) {
		int index = inv.getSlotFor(new ItemStack(i));
		if(inv.getStackInSlot(index).isItemEqual(new ItemStack(i)) && inv.getStackInSlot(index).stackSize >= count) {
			inv.decrStackSize(index, count);
			return true;
		} else {
			return false;
		}
	}
	
	public static HashMap<Integer, ItemStack> getInventoryItems(InventoryPlayer inv) {
		HashMap<Integer, ItemStack> items = new HashMap<Integer, ItemStack>();
		for(int c = 1; c < inv.mainInventory.length; c++) {
			items.put(c, inv.mainInventory[c]);
		}
		for(int c = items.size(); c < inv.offHandInventory.length + items.size(); c++) {
			items.put(c, inv.mainInventory[c]);
		}
		return items;
	}
	
	public static void destroyCurrentEquippedItem(EntityPlayer player) {
		player.inventory.decrStackSize(28 + player.inventory.currentItem, 1);
	}
	
}
