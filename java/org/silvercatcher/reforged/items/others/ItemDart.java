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
	public void registerRecipes() {
		switch(getUnlocalizedName().substring(10)) {
		
		case "normal": GameRegistry.addShapedRecipe(new ItemStack(this),
				 " f ",
				 " s ",
				 "vsv",
				 'f', Items.flint,
				 's', Items.stick,
				 'v', Items.feather); break;
			
		case "hunger": GameRegistry.addShapelessRecipe(new ItemStack(this),
				ReforgedAdditions.DART_NORMAL,
				Items.rotten_flesh); break;
			
		case "poison": GameRegistry.addShapelessRecipe(new ItemStack(this),
				ReforgedAdditions.DART_NORMAL,
				new ItemStack(Blocks.cactus)); break;
			
		case "poison_strong": GameRegistry.addShapelessRecipe(new ItemStack(this),
				ReforgedAdditions.DART_NORMAL,
				Items.spider_eye); break;
			
		case "slowness": GameRegistry.addShapelessRecipe(new ItemStack(this),
				ReforgedAdditions.DART_NORMAL,
				Items.slime_ball); break;
			
		case "wither": GameRegistry.addShapelessRecipe(new ItemStack(this),
				ReforgedAdditions.DART_NORMAL,
				Items.spider_eye,
				Items.bone); break;
			
		default: throw new IllegalArgumentException("Could not register recipe of the item: " + getUnlocalizedName());
		
		}
	}

	@Override
	public float getHitDamage() {
		return 1f;
	}
}