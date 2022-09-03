package org.silvercatcher.reforged.items.recipes;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import org.silvercatcher.reforged.api.CompoundTags;
import org.silvercatcher.reforged.api.ReforgedAdditions;

public class NestOfBeesLoadRecipe extends ShapelessRecipes {

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

    private int aB;
    private int NoB;

    public NestOfBeesLoadRecipe(String group, ItemStack result, NonNullList<Ingredient> ingredients) {
        super(group, result, ingredients);
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        NBTTagCompound compound = CompoundTags.giveCompound(output);
        int arrows = compound.getInteger(CompoundTags.AMMUNITION);
        arrows = arrows + 8;
        compound.setInteger(CompoundTags.AMMUNITION, arrows);
        return output;
    }

    @Override
    public ItemStack getRecipeOutput() {
        ItemStack output = new ItemStack(ReforgedAdditions.NEST_OF_BEES);
        NBTTagCompound compound = CompoundTags.giveCompound(output);
        compound.setInteger(CompoundTags.AMMUNITION, 8);
        return !this.output.isEmpty() ? this.output : output;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inventory) {
        return ForgeHooks.defaultRecipeGetRemainingItems(inventory);
    }

    @Override
    public boolean matches(InventoryCrafting inventory, World world) {
        NoB = -1;
        aB = -1;
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack != null && !stack.isEmpty()) {
                if (stack.getItem() == ReforgedAdditions.ARROW_BUNDLE && aB == -1) {
                    aB = i;
                } else if (stack.getItem() == ReforgedAdditions.NEST_OF_BEES
                        && stack.getTagCompound().getInteger(CompoundTags.AMMUNITION) + 8 <= 32 && NoB == -1) {
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
