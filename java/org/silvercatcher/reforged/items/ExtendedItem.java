package org.silvercatcher.reforged.items;

import org.silvercatcher.reforged.ReforgedMod;

import com.google.common.collect.Multimap;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * 
 * *ggrrrrr*
 * 
 * this class is just for less @override trouble...
 */
public abstract class ExtendedItem extends Item implements ItemExtension {

	public ExtendedItem() {
	
		setCreativeTab(ReforgedMod.tabReforged);
	}
	
	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		return ItemExtension.super.getAttributeModifiers(stack);
	}
	
	@Override
	public float getHitDamage() {
		return 0f;
	}
}
