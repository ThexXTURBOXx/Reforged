package org.silvercatcher.reforged.items.others;

import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.items.ExtendedItem;

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
				 'f', Items.FLINT,
				 's', Items.STICK,
				 'v', Items.FEATHER); break;
			
		case "hunger": GameRegistry.addShapedRecipe(new ItemStack(this),
				 " p ",
				 " d ",
				 "   ",
				 'd', ReforgedAdditions.DART_NORMAL,
				 'p', new ItemStack(Items.POTIONITEM, 1, 16)); break;
			
		case "poison": GameRegistry.addShapedRecipe(new ItemStack(this),
				 " p ",
				 " d ",
				 "   ",
				 'd', ReforgedAdditions.DART_NORMAL,
				 'p', new ItemStack(Items.POTIONITEM, 1, 8196)); break;
			
		case "poison_strong": GameRegistry.addShapedRecipe(new ItemStack(this),
				 " p ",
				 " d ",
				 "   ",
				 'd', ReforgedAdditions.DART_POISON,
				 'p', new ItemStack(Items.POTIONITEM, 1, 8196));
		 		GameRegistry.addShapedRecipe(new ItemStack(this),
				 " p ",
				 " d ",
				 "   ",
				 'd', ReforgedAdditions.DART_NORMAL,
				 'p', new ItemStack(Items.POTIONITEM, 1, 8228));break;
			
		case "slowness": GameRegistry.addShapedRecipe(new ItemStack(this),
				 " p ",
				 " d ",
				 "   ",
				 'd', ReforgedAdditions.DART_NORMAL,
				 'p', new ItemStack(Items.POTIONITEM, 1, 16394)); break;
			
		case "wither": GameRegistry.addShapedRecipe(new ItemStack(this),
				 " p ",
				 " d ",
				 " b ",
				 'd', ReforgedAdditions.DART_NORMAL,
				 'p', new ItemStack(Items.POTIONITEM, 1, 16428),
				 'b', new ItemStack(Items.POTIONITEM, 1, 8196)); break;
			
		default: throw new IllegalArgumentException("Could not register recipe of the item: " + getUnlocalizedName());
		
		}
	}

	@Override
	public float getHitDamage() {
		return 1f;
	}
}