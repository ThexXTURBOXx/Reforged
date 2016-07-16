package org.silvercatcher.reforged.items.weapons;

import java.util.List;
import java.util.Random;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.*;

import com.google.common.collect.Multimap;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.LanguageRegistry;

public class ItemCrossbow extends ItemBow implements ItemExtension {
	
	public ItemCrossbow() {
		setMaxStackSize(1);
		setMaxDamage(100);
		setUnlocalizedName("crossbow");
		setCreativeTab(ReforgedMod.tabReforged);
	}
	
	byte empty		= 0;
	byte loading	= 1;
	byte loaded		= 2;
	
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
		
		byte loadState = getLoadState(stack);
		
		if(loadState == loading) return EnumAction.BLOCK;
		if(loadState == loaded) return EnumAction.BOW;
		return EnumAction.NONE;
	}
	
	public byte getLoadState(ItemStack stack) {
		return giveCompound(stack).getByte(CompoundTags.AMMUNITION);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		
		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);
		
		if(loadState == loading) return 40;
		
		return super.getMaxItemUseDuration(stack);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		
		NBTTagCompound compound = giveCompound(itemStackIn);
		
		byte loadState = compound.getByte(CompoundTags.AMMUNITION);
		
		if(loadState == empty) {
			if(playerIn.capabilities.isCreativeMode || playerIn.inventory.consumeInventoryItem(ReforgedAdditions.CROSSBOW_BOLT)) {
				loadState = loading;
				if(compound.getByte(CompoundTags.AMMUNITION) == empty) {
					if(worldIn.isRemote) {
						System.out.println("modified");
						compound.setInteger(CompoundTags.STARTED, playerIn.ticksExisted + getReloadTotal());
					}
				}
			} else {
				
				worldIn.playSoundAtEntity(playerIn, "item.fireCharge.use", 1.0f, 0.7f);
			}
		}
		
		compound.setByte(CompoundTags.AMMUNITION, loadState);
		
		playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
		
		return itemStackIn;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft) {
		NBTTagCompound compound = giveCompound(stack);
		
		byte loadState = compound.getByte(CompoundTags.AMMUNITION);
		if(loadState == loaded) {
			
			worldIn.playSoundAtEntity(playerIn, "reforged:crossbow_shoot", 1f, 1f);
			if(!worldIn.isRemote) shoot(worldIn, playerIn, stack);
			if(!playerIn.capabilities.isCreativeMode && (stack.getItem().isDamageable() && stack.attemptDamageItem(5, itemRand))) {
				playerIn.renderBrokenItemStack(stack);
				playerIn.destroyCurrentEquippedItem();
			}
			compound.setByte(CompoundTags.AMMUNITION, empty);
			System.out.println("modified");
			if(worldIn.isRemote) compound.setInteger(CompoundTags.STARTED, -1);
		}
	}
	
	public void shoot(World worldIn, EntityLivingBase playerIn, ItemStack stack) {
		EntityArrow a = new EntityArrow(worldIn, playerIn, 2.1F);
		a.canBePickedUp = new Random().nextInt(2);
		worldIn.spawnEntityInWorld(a);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);
		if(loadState == loading) {
			loadState = loaded;
		}
		giveCompound(stack).setByte(CompoundTags.AMMUNITION, loadState);
		return stack;
	}
	
	public int getReloadStarted(ItemStack stack) {
		return giveCompound(stack).getInteger(CompoundTags.STARTED);
	}
	
	public int getReloadTotal() {
		return 20;
	}
	
	public NBTTagCompound giveCompound(ItemStack stack) {
		
		NBTTagCompound compound = CompoundTags.giveCompound(stack);
		
		if(!compound.hasKey(CompoundTags.AMMUNITION)) {
			
			compound.setByte(CompoundTags.AMMUNITION, empty);
		}
		return compound;
	}
	
	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		return ItemExtension.super.getAttributeModifiers(stack);
	}
	
	@Override
	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining) {
        ModelResourceLocation mrl = new ModelResourceLocation(ReforgedMod.ID + ":crossbow", "inventory");
		if(stack.getItem() == this && player.getItemInUse() != null) {
			if(getLoadState(stack) == loading) {
				int left = getReloadLeft(stack, player);
				/*
				 * The tE is everytime right, ofc
				 * the ir becomes 0 after some ticks
				 * the left depends on both of them, so it gets negative after ir becomes 0
				 */
				System.out.println("tE: " + player.ticksExisted);
				System.out.println("ir: " + getReloadStarted(stack));
				System.out.println("le: " + left);
				if(left > 10) {
					mrl = new ModelResourceLocation(ReforgedMod.ID + ":crossbow_1", "inventory");
				} else if(left > 0) {
					mrl = new ModelResourceLocation(ReforgedMod.ID + ":crossbow_2", "inventory");
				} else if(left > -10) {
					mrl = new ModelResourceLocation(ReforgedMod.ID + ":crossbow_4", "inventory");
				} else {
					mrl = new ModelResourceLocation(ReforgedMod.ID + ":crossbow_5", "inventory");
				}
			} else if(getLoadState(stack) == loaded) {
				mrl = new ModelResourceLocation(ReforgedMod.ID + ":crossbow_3", "inventory");
			}
		}
		return mrl;
	}
	
	@Override
	public void registerRecipes() {
		GameRegistry.addShapedRecipe(new ItemStack(this), "bii",
														  "iw ",
														  "i w",
														  'b', Items.bow,
														  'i', Items.iron_ingot,
														  'w', new ItemStack(Blocks.planks));
	}
	
	public int getReloadLeft(ItemStack stack, EntityPlayer player) {
		return (getReloadStarted(stack) - player.ticksExisted);
	}
	
	@Override
	public float getHitDamage() {
		return 2f;
	}
	
}