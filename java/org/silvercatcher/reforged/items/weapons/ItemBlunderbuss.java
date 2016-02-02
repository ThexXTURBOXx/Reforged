package org.silvercatcher.reforged.items.weapons;

import java.util.List;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.entities.EntityBulletBlunderbuss;
import org.silvercatcher.reforged.entities.EntityBulletMusket;
import org.silvercatcher.reforged.items.CompoundTags;
import org.silvercatcher.reforged.items.ItemExtension;

import com.google.common.collect.Multimap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.LanguageRegistry;

public class ItemBlunderbuss extends ItemBow implements ItemExtension {

	// let's see...
	byte empty		= 0;
	byte loading	= 1;
	byte loaded		= 2;
	
	public ItemBlunderbuss() {
		
		setMaxStackSize(1);
		setMaxDamage(100);
		setUnlocalizedName("blunderbuss");
		setCreativeTab(ReforgedMod.tabReforged);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		
		byte loadState = giveCompound(itemStackIn).getByte(CompoundTags.AMMUNITION);
		
		if(loadState == empty) {
			
			if(playerIn.capabilities.isCreativeMode ||
					playerIn.inventory.consumeInventoryItem(ReforgedRegistry.BLUNDERBUSS_SHOT)) {
				
				loadState = loading;
			
			} else {
				
				worldIn.playSoundAtEntity(playerIn, "item.fireCharge.use", 1.0f, 0.7f);
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

			worldIn.playSoundAtEntity(playerIn, "ambient.weather.thunder", 1f, 1f);

			if(!worldIn.isRemote) {

				EntityBulletBlunderbuss projectile = new EntityBulletBlunderbuss(worldIn, playerIn, stack);
				
				worldIn.spawnEntityInWorld(projectile);
				
				if(stack.attemptDamageItem(5, itemRand)) {
					playerIn.renderBrokenItemStack(stack);
					playerIn.destroyCurrentEquippedItem();
				}
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
	
	public NBTTagCompound giveCompound(ItemStack stack) {
		
		NBTTagCompound compound = CompoundTags.giveCompound(stack);
		
		if(!compound.hasKey(CompoundTags.AMMUNITION)) {
			
			compound.setByte(CompoundTags.AMMUNITION, empty);
		}
		return compound;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		
		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);
		
		tooltip.add(new LanguageRegistry().instance().getStringLocalization("item.musket.loadstate") + ": " + (loadState == empty ? 
				new LanguageRegistry().instance().getStringLocalization("item.musket.loadstate.empty")
				: (loadState == loaded ? new LanguageRegistry().instance().getStringLocalization("item.musket.loadstate.loaded") : 
					new LanguageRegistry().instance().getStringLocalization("item.musket.loadstate.loading"))));
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
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		
		return (repair.getItem() == Items.iron_ingot);
	}
	
	@Override
	public int getItemEnchantability() {
		
		return ToolMaterial.IRON.getEnchantability();
	}
	
	@Override
	public void registerRecipes() {
	
		GameRegistry.addShapelessRecipe(new ItemStack(this),
				new ItemStack(ReforgedRegistry.BLUNDERBUSS_BARREL), new ItemStack(ReforgedRegistry.GUN_STOCK));
	}

	@Override
	public float getHitDamage() {
		return 2f;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		return ItemExtension.super.getAttributeModifiers(stack);
	}
	
	@Override
	public int getItemEnchantability(ItemStack stack) {
		return ToolMaterial.IRON.getEnchantability();
	}
}