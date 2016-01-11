package org.silvercatcher.reforged.weapons;

import org.silvercatcher.reforged.ReforgedMod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class ReforgedItem extends Item {

	protected final String name;
	
	public ReforgedItem(String name) {

		super();
		this.name = name;
		setCreativeTab(ReforgedMod.tabReforged);
		setUnlocalizedName(name);
	}
	
	public final String getName() { return name; }
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		
		playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
		return itemStackIn;
	}
	
	public abstract void registerRecipes();
	
	public abstract float getHitDamage();

}
