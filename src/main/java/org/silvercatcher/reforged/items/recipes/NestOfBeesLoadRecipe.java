package org.silvercatcher.reforged.items.recipes;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.CompoundTags;
import org.silvercatcher.reforged.api.ReforgedAdditions;

public class NestOfBeesLoadRecipe extends ShapelessRecipe {

	private ItemStack output = ItemStack.EMPTY;
	private int aB;
	private int NoB;

	public NestOfBeesLoadRecipe(String group, ItemStack result, NonNullList<Ingredient> ingredients) {
		super(new ResourceLocation(ReforgedMod.ID, "nob_load"), group, result, ingredients);
	}

	private static void printInventory(String name, InventoryCrafting inventory) {

		if (Minecraft.getInstance().world != null) {
			System.out.append(name);
			System.out.append(":\t[");
			for (int i = 0; i < inventory.getSizeInventory(); i++) {
				System.out.append(inventory.getStackInSlot(i) + ",");
			}
			System.out.append("]");
			System.out.println();
		}
	}

	@Override
	public boolean canFit(int width, int height) {
		return width >= 3 && height >= 3;
	}

	@Override
	public ItemStack getCraftingResult(IInventory inv) {
		NBTTagCompound compound = CompoundTags.giveCompound(output);
		int arrows = compound.getInt(CompoundTags.AMMUNITION);
		arrows = arrows + 8;
		compound.putInt(CompoundTags.AMMUNITION, arrows);
		return output;
	}

	@Override
	public ItemStack getRecipeOutput() {
		ItemStack output = new ItemStack(ReforgedAdditions.NEST_OF_BEES);
		NBTTagCompound compound = CompoundTags.giveCompound(output);
		compound.putInt(CompoundTags.AMMUNITION, 8);
		return !this.output.isEmpty() ? this.output : output;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(IInventory inv) {
		NonNullList<ItemStack> ret = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
		for (int i = 0; i < ret.size(); i++)
			ret.set(i, ForgeHooks.getContainerItem(inv.getStackInSlot(i)));
		return ret;
	}

	@Override
	public boolean matches(IInventory inv, World worldIn) {
		NoB = -1;
		aB = -1;
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null && !stack.isEmpty()) {
				if (stack.getItem() == ReforgedAdditions.ARROW_BUNDLE && aB == -1) {
					aB = i;
				} else if (stack.getItem() == ReforgedAdditions.NEST_OF_BEES
						&& stack.getTag().getInt(CompoundTags.AMMUNITION) + 8 <= 32 && NoB == -1) {
					NoB = i;
					output = stack.copy();
				} else {
					return false;
				}
			}
		}
		return NoB != -1 && aB != -1;
	}

}