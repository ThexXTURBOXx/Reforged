package org.silvercatcher.reforged.items.recipes;

import java.util.HashMap;
import java.util.Map;

import org.silvercatcher.reforged.api.CompoundTags;
import org.silvercatcher.reforged.api.ReforgedAdditions;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class NestOfBeesLoadRecipe implements IRecipe {
	
	private ItemStack[] input;
	private ItemStack output = ItemStack.EMPTY;
	private Map<Integer, Integer> aBs, usedaBs;
	private int NoB;
	
	private static void printInventory(String name, InventoryCrafting inventory) {
	
		if(Minecraft.getMinecraft().world != null) {
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
		NoB = -1;
		aBs = new HashMap<Integer, Integer>();
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if(stack != null && !stack.equals(ItemStack.EMPTY)) {
				if(stack.getItem() == ReforgedAdditions.ARROW_BUNDLE) {
					aBs.put(i, stack.getCount());
				} else if(stack.getItem() == ReforgedAdditions.NEST_OF_BEES &&
						stack.getTagCompound().getInteger(CompoundTags.AMMUNITION) + 1 <= 32 && NoB == -1) {
					NoB = i;
					output = stack.copy();
				} else {
					return false;
				}
			}
		}
		if(NoB == -1 || aBs.isEmpty()) {
			return false;
		}
		return true;
	}
	
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {
		usedaBs = new HashMap<Integer, Integer>();
		input = new ItemStack[inventory.getSizeInventory()];
		input[NoB] = inventory.getStackInSlot(NoB);
		for(int index : aBs.keySet()) {
			input[index] = inventory.getStackInSlot(index);
		}
		NBTTagCompound compound = CompoundTags.giveCompound(output);
		int arrows = compound.getInteger(CompoundTags.AMMUNITION);
		for(int index : aBs.keySet()) {
			inventory.getStackInSlot(index);
			int stsize = aBs.get(index);
			if(stsize > 4) stsize = 4;
			arrows += stsize * 8;
			if(arrows > 32) arrows = 32;
			usedaBs.put(index, stsize);
		}
		compound.setInteger(CompoundTags.AMMUNITION, arrows);
		return output;
	}
	
	@Override
	public int getRecipeSize() {
		return 9;
	}
	
	@Override
	public ItemStack getRecipeOutput() {
		return output;
	}
	
	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inventory) {
		for(int index : usedaBs.keySet()) {
			if(inventory.getStackInSlot(index).getCount() == usedaBs.get(index)) {
				inventory.decrStackSize(index, usedaBs.get(index));
			} else {
				inventory.decrStackSize(index, usedaBs.get(index) - 1);
			}
		}
		return ForgeHooks.defaultRecipeGetRemainingItems(inventory);
	}
}