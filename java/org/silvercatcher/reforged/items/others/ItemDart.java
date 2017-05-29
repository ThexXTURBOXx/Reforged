package org.silvercatcher.reforged.items.others;

import org.silvercatcher.reforged.api.ExtendedItem;
import org.silvercatcher.reforged.api.ReforgedAdditions;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
			GameRegistry.addShapedRecipe(new ItemStack(this), " f ", " s ", "vsv", 'f', Items.FLINT, 's', Items.STICK,
					'v', Items.FEATHER);
			break;

		case "hunger":
			GameRegistry.addShapelessRecipe(new ItemStack(this), ReforgedAdditions.DART_NORMAL, Items.ROTTEN_FLESH);
			break;

		case "poison":
			GameRegistry.addShapelessRecipe(new ItemStack(this), ReforgedAdditions.DART_NORMAL,
					new ItemStack(Blocks.CACTUS));
			break;

		case "poison_strong":
			GameRegistry.addShapelessRecipe(new ItemStack(this), ReforgedAdditions.DART_NORMAL, Items.SPIDER_EYE);
			break;

		case "slowness":
			GameRegistry.addShapelessRecipe(new ItemStack(this), ReforgedAdditions.DART_NORMAL, Items.SLIME_BALL);
			break;

		case "wither":
			GameRegistry.addShapelessRecipe(new ItemStack(this), ReforgedAdditions.DART_NORMAL, Items.SPIDER_EYE,
					Items.BONE);
			break;

		default:
			throw new IllegalArgumentException("Could not register recipe of the item: " + getUnlocalizedName());

		}
	}
}