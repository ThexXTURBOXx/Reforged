package org.silvercatcher.reforged.items.recipes;

import org.silvercatcher.reforged.api.CompoundTags;
import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.items.weapons.ItemBoomerang;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class BoomerangEnchRecipe extends ShapelessRecipes {
	
	public BoomerangEnchRecipe(String group, ItemStack result, NonNullList<Ingredient> ingredients) {
        super(group, result, ingredients);
    }
	
	private static void printInventory(String name, InventoryCrafting inventory) {

		if (Minecraft.getMinecraft().world != null) {

			System.out.append(name);
			System.out.append(":\t[");
			for (int i = 0; i < inventory.getSizeInventory(); i++) {
				System.out.append(inventory.getStackInSlot(i) + ",");
			}
			System.out.append("]");
			System.out.println();
		}
	}

	private ItemStack output = ItemStack.EMPTY;

	@Override
	public boolean canFit(int width, int height) {
		return width >= 3 && height >= 3;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {

		// printInventory("result", inventory);

		int size = inventory.getSizeInventory();

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
	public boolean matches(InventoryCrafting inventory, World world) {
		// printInventory("match", inventory);
		int boomerangs = 0;
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if (stack != null && !stack.isEmpty()) {
				if (stack.getItem() instanceof ItemBoomerang
						&& !CompoundTags.giveCompound(stack).getBoolean(CompoundTags.ENCHANTED)) {
					output = stack.copy();
					boomerangs++;
				}
			}
		}
		return boomerangs == 1 && super.matches(inventory, world);
	}
	
}