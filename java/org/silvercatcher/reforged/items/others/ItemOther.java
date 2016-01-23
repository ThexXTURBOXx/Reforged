package org.silvercatcher.reforged.items.others;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.items.ItemExtension;

import net.minecraft.item.Item;

/**
 * 
 * *ggrrrrr*
 * 
 * this class is just for less @override trouble...
 */
public abstract class ItemOther extends Item implements ItemExtension {

	public ItemOther() {
		
		setCreativeTab(ReforgedMod.tabReforged);
	}
	
	@Override
	public void registerRecipes() {
		
	}
	
}
