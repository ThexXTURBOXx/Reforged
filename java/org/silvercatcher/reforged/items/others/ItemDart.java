package org.silvercatcher.reforged.items.others;

import java.util.HashMap;
import java.util.List;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.items.ExtendedItem;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemDart extends ExtendedItem {

	private final static String [] dartNames = {

			"normal",
			"poison",
			"poison_strong",
			"slowness",
			"hunger",
			"wither"
	};

	
	public static int dartVariants() {
		return dartNames.length;
	}
	
	public static String getDartModelName(int id) {
		
		return ReforgedMod.ID +  ":dart_" + dartNames[id];
	}
	
	public ItemDart() {
		
		super();
		
		setUnlocalizedName("dart");
		
		setMaxStackSize(64);
		setHasSubtypes(true);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		
		return super.getUnlocalizedName() + "_" + dartNames[stack.getMetadata()];
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
		
		for(int i = 0; i < dartNames.length; i++) {
			subItems.add(new ItemStack(itemIn, 1, i));
		}
	}
}
