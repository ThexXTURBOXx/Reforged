package org.silvercatcher.reforged.items;

import org.silvercatcher.reforged.ReforgedMod;

import com.google.common.collect.Multimap;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		
		applyHitEnchantments(stack, player, entity);
		
		return false;
	}
	
	@Override
	public float getHitDamage() {
		return 0f;
	}
}
