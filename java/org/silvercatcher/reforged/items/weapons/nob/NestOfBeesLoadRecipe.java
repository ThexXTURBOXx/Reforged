package org.silvercatcher.reforged.items.weapons.nob;

import org.silvercatcher.reforged.ReforgedItems;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class NestOfBeesLoadRecipe implements IRecipe {

	private ItemStack output;
	private ItemStack [] input;
	
	@Override
	public boolean matches(InventoryCrafting inventory, World world) {
		
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			
			ItemStack stack = inventory.getStackInSlot(i);
			
			if(stack != null && stack.getItem() == ReforgedItems.NEST_OF_BEES) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {

		
		output = new ItemStack(ReforgedItems.NEST_OF_BEES);

		return output;
	}

	@Override
	public int getRecipeSize() {
		
		return input.length;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return output;
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inventory) {
		
		ItemStack [] remaining = new ItemStack[inventory.getSizeInventory()];
		
		for(int i = 0; i < remaining.length; i++) {
			
			if(input[i] != output) {
				remaining[i] = input[i];
			}
		}
		return remaining;
	}
}
