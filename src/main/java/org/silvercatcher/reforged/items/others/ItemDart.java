package org.silvercatcher.reforged.items.others;

import org.silvercatcher.reforged.api.ExtendedItem;
import org.silvercatcher.reforged.api.ReforgedAdditions;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ItemDart extends ExtendedItem {

	public ItemDart(String effect) {
		super();
		setUnlocalizedName("dart_" + effect);
		setMaxStackSize(64);
	}

	@Override
	public float getHitDamage() {
		return 1f;
	}

	@Override
	public void registerRecipes() {
		switch (getUnlocalizedName().substring(10)) {

		case "normal":
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this), " f ", " s ", "vsv", 'f', Items.FLINT, 's',
					"stickWood", 'v', "feather"));
			break;

		case "hunger":
			GameRegistry.addShapelessRecipe(new ItemStack(this), ReforgedAdditions.DART_NORMAL, Items.ROTTEN_FLESH);
			break;

		case "poison":
			GameRegistry.addRecipe(
					new ShapelessOreRecipe(new ItemStack(this), ReforgedAdditions.DART_NORMAL, "blockCactus"));
			break;

		case "poison_strong":
			GameRegistry.addShapelessRecipe(new ItemStack(this), ReforgedAdditions.DART_NORMAL, Items.SPIDER_EYE);
			break;

		case "slowness":
			GameRegistry
					.addRecipe(new ShapelessOreRecipe(new ItemStack(this), ReforgedAdditions.DART_NORMAL, "slimeball"));
			break;

		case "wither":
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(this), ReforgedAdditions.DART_NORMAL,
					Items.SPIDER_EYE, "bone"));
			break;

		default:
			throw new IllegalArgumentException("Could not register recipe of the item: " + getUnlocalizedName());

		}
	}
}