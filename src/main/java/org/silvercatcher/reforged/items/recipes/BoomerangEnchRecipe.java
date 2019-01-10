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
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.CompoundTags;
import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.items.weapons.ItemBoomerang;

public class BoomerangEnchRecipe extends ShapelessRecipe {

	private ItemStack output = ItemStack.EMPTY;

	public BoomerangEnchRecipe(String group, ItemStack result, NonNullList<Ingredient> ingredients) {
		super(new ResourceLocation(ReforgedMod.ID, "boomerang_ench"), group, result, ingredients);
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
		// printInventory("result", inventory);

		int size = inv.getSizeInventory();

		NBTTagCompound compound = CompoundTags.giveCompound(output);

		output.addEnchantment(ReforgedAdditions.goalseeker, 1);
		compound.setBoolean(CompoundTags.ENCHANTED, true);
		return output;
	}

	@Override
	public ItemStack getRecipeOutput() {
		ItemStack output = new ItemStack(ReforgedAdditions.DIAMOND_BOOMERANG);
		NBTTagCompound compound = CompoundTags.giveCompound(output);
		output.addEnchantment(ReforgedAdditions.goalseeker, 1);
		compound.setBoolean(CompoundTags.ENCHANTED, true);
		return !this.output.isEmpty() ? this.output : output;
	}

	@Override
	public boolean matches(IInventory inv, World worldIn) {
		// printInventory("match", inventory);
		int boomerangs = 0;
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null && !stack.isEmpty()) {
				if (stack.getItem() instanceof ItemBoomerang
						&& !CompoundTags.giveCompound(stack).getBoolean(CompoundTags.ENCHANTED)) {
					output = stack.copy();
					boomerangs++;
				}
			}
		}
		return boomerangs == 1 && super.matches(inv, worldIn);
	}

}