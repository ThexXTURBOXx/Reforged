package org.silvercatcher.reforged.items.weapons.nob;

import java.util.LinkedList;

import org.silvercatcher.reforged.ReforgedItems;
import org.silvercatcher.reforged.items.CompoundTags;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class NestOfBeesLoadRecipe implements IRecipe {

	private ItemStack output;
	private ItemStack input [];
	
	private static void printInventory(String name, InventoryCrafting inventory) {
	
		if(Minecraft.getMinecraft().theWorld != null) {
			
			System.out.append(name);
			System.out.append(":\t[");
			for(int i = 0; i < inventory.getSizeInventory(); i++) {
				System.out.append(inventory.getStackInSlot(i) + ",");
			}
			System.out.append("]");
			System.out.println();
		}
	}
	
	@Override
	public boolean matches(InventoryCrafting inventory, World world) {
		
		printInventory("match", inventory);
		
		int nestsOfBees = 0;
		int arrowBundles = 1;
		
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			
			ItemStack stack = inventory.getStackInSlot(i);
			
			if(stack != null) {
				if(stack.getItem() == ReforgedItems.NEST_OF_BEES) {
					nestsOfBees++;
				} else if(stack.getItem() == ReforgedItems.ARROW_BUNDLE) {
					arrowBundles++;
				} else {
					// we don't want any other stuff!
					return false;
				}
			}
		}
		
		return nestsOfBees == 1 && arrowBundles > 0;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {

		printInventory("result", inventory);
		
		int size = inventory.getSizeInventory();
		// we can assume that the ingredients match, at least one NoB has to be there...I hope
		ItemStack nestOfBees = null;
		
		// remember where to get arrow bundles....better idea?
		LinkedList<Integer> arrowBundleIndizes = new LinkedList<Integer>();
		
		for(int i = 0; i < size; i++) {
			
			ItemStack stack = inventory.getStackInSlot(i);
			
			if(stack != null) {
				Item item = stack.getItem();
				if(item == ReforgedItems.NEST_OF_BEES) {
					nestOfBees = stack;
				} else if(item == ReforgedItems.ARROW_BUNDLE) {
					arrowBundleIndizes.add(i);
				}
			}
		}
		
		NBTTagCompound compound = ReforgedItems.NEST_OF_BEES.giveCompound(nestOfBees);
		
		int arrows = compound.getInteger(CompoundTags.AMMUNITION);
		
		// please terminate, please terminate,...
		while(!arrowBundleIndizes.isEmpty() && arrows < 32 - 7) {
			arrows += 8;
			input[arrowBundleIndizes.removeFirst()] = null;
		}
		
		compound.setInteger(CompoundTags.AMMUNITION, arrows);
		input = new ItemStack[inventory.getSizeInventory()];

		return nestOfBees;
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
		
		printInventory("remain", inventory);
		
		return input;
	}
}
