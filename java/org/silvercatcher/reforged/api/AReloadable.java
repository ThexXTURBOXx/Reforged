package org.silvercatcher.reforged.api;

import java.util.List;

import org.silvercatcher.reforged.ReforgedMod;

import com.google.common.collect.Multimap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.LanguageRegistry;

public abstract class AReloadable extends ItemBow implements ItemExtension {
	
	private Item ammo;
	private String shootsound;
	
	public AReloadable(String name, String shootsound) {
		setMaxStackSize(1);
		setMaxDamage(100);
		setUnlocalizedName(name);
		setCreativeTab(ReforgedMod.tabReforged);
		this.shootsound = shootsound;
	}
	
	public static final byte empty		= 0;
	public static final byte loading	= 1;
	public static final byte loaded		= 2;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		
		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);
		
		LanguageRegistry lr = LanguageRegistry.instance();
		
		tooltip.add(lr.getStringLocalization("item.musket.loadstate") + ": " + (loadState == empty ? 
				lr.getStringLocalization("item.musket.loadstate.empty")
				: (loadState == loaded ? lr.getStringLocalization("item.musket.loadstate.loaded") : 
					lr.getStringLocalization("item.musket.loadstate.loading"))));
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		
		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);
		
		if(loadState == loading) {
			if(ReforgedMod.battlegearDetected) return EnumAction.BOW;
			else return EnumAction.BLOCK;
		}
		if(loadState == loaded) return EnumAction.BOW;
		return EnumAction.NONE;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		
		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);
		
		if(loadState == loading) return getReloadTotal();

		return super.getMaxItemUseDuration(stack);
	}
	
	protected void setAmmo(Item ammo) {
		this.ammo = ammo;
	}
	
	private Item getAmmo() {
		return ammo;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World world, EntityPlayer playerIn) {
		
		NBTTagCompound compound = giveCompound(itemStackIn);
		
		byte loadState = compound.getByte(CompoundTags.AMMUNITION);
		
		if(loadState == empty) {
			if(playerIn.capabilities.isCreativeMode || playerIn.inventory.consumeInventoryItem(getAmmo())) {
				
				loadState = loading;
				if(compound.getByte(CompoundTags.AMMUNITION) == empty) {
					compound.setInteger(CompoundTags.STARTED, playerIn.ticksExisted + getReloadTotal());
				}
			} else {
				
				world.playSoundAtEntity(playerIn, "reforged:shotgun_reload", 1.0f, 0.7f);
			}
		}
		
		compound.setByte(CompoundTags.AMMUNITION, loadState);
		
		playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
		
		return itemStackIn;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer playerIn, int timeLeft) {
		if(!world.isRemote) {
			NBTTagCompound compound = giveCompound(stack);
			
			byte loadState = compound.getByte(CompoundTags.AMMUNITION);
			
			if(loadState == loaded) {
				
				world.playSoundAtEntity(playerIn, shootsound, 1f, 1f);
				
				shoot(world, playerIn, stack);
				if(!playerIn.capabilities.isCreativeMode && stack.getItem().isDamageable() && stack.attemptDamageItem(5, itemRand)) {
					playerIn.renderBrokenItemStack(stack);
					playerIn.destroyCurrentEquippedItem();
				}
				
				compound.setByte(CompoundTags.AMMUNITION, empty);
				compound.setInteger(CompoundTags.STARTED, -1);
			}
		}
	}
	
	public abstract void shoot(World world, EntityLivingBase playerIn, ItemStack stack);
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer playerIn) {
		
		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);
		
		if(loadState == loading) {
			loadState = loaded;
		}
		giveCompound(stack).setByte(CompoundTags.AMMUNITION, loadState);
		return stack;
	}
	
	public abstract int getReloadTotal();
	
	public NBTTagCompound giveCompound(ItemStack stack) {
		
		NBTTagCompound compound = CompoundTags.giveCompound(stack);
		
		if(!compound.hasKey(CompoundTags.AMMUNITION)) {
			
			compound.setByte(CompoundTags.AMMUNITION, empty);
		}
		return compound;
	}
	
	public int getReloadStarted(ItemStack stack) {
		return giveCompound(stack).getInteger(CompoundTags.STARTED);
	}
	
	public int getReloadLeft(ItemStack stack, EntityPlayer player) {
		return (getReloadStarted(stack) - player.ticksExisted);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		return ItemExtension.super.getAttributeModifiers(stack);
	}
	
}