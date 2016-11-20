package org.silvercatcher.reforged.items.weapons;

import java.util.List;
import java.util.Random;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.*;
import org.silvercatcher.reforged.entities.EntityCrossbowBolt;

import com.google.common.collect.Multimap;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
	
	public static final byte empty		= 0;
	public static final byte loading	= 1;
	public static final byte loaded		= 2;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced) {
		
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

		if(loadState == loading) {
			if(ReforgedMod.battlegearDetected) return EnumAction.BOW;
			else return EnumAction.BLOCK;
		}
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
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		NBTTagCompound compound = giveCompound(itemStack);
		
		byte loadState = compound.getByte(CompoundTags.AMMUNITION);
		
		if(loadState == empty) {
			if(player.capabilities.isCreativeMode || player.inventory.consumeInventoryItem(ReforgedAdditions.CROSSBOW_BOLT)) {
				loadState = loading;
				if(compound.getByte(CompoundTags.AMMUNITION) == empty) {
					compound.setInteger(CompoundTags.STARTED, player.ticksExisted + getReloadTotal());				
				}
			} else {
				world.playSoundAtEntity(player, "item.fireCharge.use", 1.0f, 0.7f);
			}
		}
		
		compound.setByte(CompoundTags.AMMUNITION, loadState);
		
		player.setItemInUse(itemStack, getMaxItemUseDuration(itemStack));
		
		return itemStack;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int timeLeft) {
		if(!world.isRemote) {
			NBTTagCompound compound = giveCompound(stack);
			
			byte loadState = compound.getByte(CompoundTags.AMMUNITION);
			if(loadState == loaded) {
				
				world.playSoundAtEntity(player, "reforged:crossbow_shoot", 1f, 1f);
				shoot(world, player, stack);
				if(!player.capabilities.isCreativeMode && (stack.getItem().isDamageable() && stack.attemptDamageItem(5, itemRand))) {
					player.renderBrokenItemStack(stack);
					player.destroyCurrentEquippedItem();
				}
				compound.setByte(CompoundTags.AMMUNITION, empty);
				compound.setInteger(CompoundTags.STARTED, -1);
			}		
		}
	}
	
	public void shoot(World world, EntityLivingBase player, ItemStack stack) {
		EntityCrossbowBolt a = new EntityCrossbowBolt(world, player);
		a.canBePickedUp = new Random().nextInt(2);
		a.setDamage(8.0D);
		world.spawnEntityInWorld(a);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
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