package org.silvercatcher.reforged.items.weapons.nob;

import org.silvercatcher.reforged.ReforgedItems;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class NestOfBeesLoadRecipe implements IRecipe {

	private ItemStack nest;
	
	@Override
	public boolean matches(InventoryCrafting inventory, World world) {
	
		nest = null;
		
		ItemStack center = inventory.getStackInRowAndColumn(1, 1);
		
		if(center == null) return false;
		
		if(center.getItem() == ReforgedItems.NEST_OF_BEES_EMPTY) {
			nest = new ItemStack(ReforgedItems.NEST_OF_BEES);
			return true;
		}
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {
		
		return nest;
	}

	@Override
	public int getRecipeSize() {
		return 1;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return nest;
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inventory) {
		
		ItemStack [] remaining = new ItemStack[inventory.getSizeInventory()];
		
		for(int i = 0; i < remaining.length; i++) {
			
			remaining[i] = ForgeHooks.getContainerItem(inventory.getStackInSlot(i));
		}
		return remaining;
	}
}
