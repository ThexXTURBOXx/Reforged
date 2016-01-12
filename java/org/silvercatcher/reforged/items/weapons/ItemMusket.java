package org.silvercatcher.reforged.items.weapons;

import java.util.List;

import org.silvercatcher.reforged.items.CompoundTags;
import org.silvercatcher.reforged.items.ReforgedItem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemMusket extends ReforgedItem {

	// let's see...
	byte empty		= 0;
	byte loading	= 1;
	byte loaded		= 2;
	
	public ItemMusket() {
		
		super("musket");
		setMaxDamage(100);
		setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		
		byte loadState = giveCompound(itemStackIn).getByte(CompoundTags.AMMUNITION);
		
		if(loadState == empty) {
			
			if(playerIn.inventory.consumeInventoryItem(Items.arrow)) {
				
				loadState = loading;
			
			} else {
				
				worldIn.playSoundAtEntity(playerIn, "item.fireCharge.use", 2.0f, 1.0f);
			}
		}
		
		giveCompound(itemStackIn).setByte(CompoundTags.AMMUNITION, loadState);
		
		playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
		
		return itemStackIn;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft) {
		
		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);
		
		if(loadState == loaded) {
			
			if(!worldIn.isRemote) {
				
				EntityArrow projectile = new EntityArrow(worldIn, playerIn, 2.5f);
				
				worldIn.spawnEntityInWorld(projectile);
			}
			giveCompound(stack).setByte(CompoundTags.AMMUNITION, empty);
		}
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
	
	@Override
	public NBTTagCompound giveCompound(ItemStack stack) {
		
		NBTTagCompound compound = super.giveCompound(stack);
		
		if(!compound.hasKey(CompoundTags.AMMUNITION)) {
			
			compound.setByte(CompoundTags.AMMUNITION, empty);
		}
		return compound;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		
		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);
		
		tooltip.add("Loadstate: " + loadState);
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		
		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);

		if(loadState == loading) return EnumAction.BLOCK;
		if(loadState == loaded) return EnumAction.BOW;
		return EnumAction.NONE;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		
		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);
		
		if(loadState == loading) return 40;

		return super.getMaxItemUseDuration(stack);
	}
	
	@Override
	public void registerRecipes() {
	
	}

	@Override
	public float getHitDamage() {
		return 2f;
	}
}
