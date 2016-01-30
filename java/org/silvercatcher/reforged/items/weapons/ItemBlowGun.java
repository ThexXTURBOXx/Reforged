package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.entities.EntityDart;
import org.silvercatcher.reforged.items.ExtendedItem;
import org.silvercatcher.reforged.items.others.ItemDart;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemBlowGun extends ExtendedItem {

	public ItemBlowGun() {
		
		super();
		setUnlocalizedName("blowgun");
		setMaxStackSize(1);
		setMaxDamage(40);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		EntityDart dart;
		if(!worldIn.isRemote) {
		if(playerIn.inventory.hasItem(ReforgedRegistry.DART_NORMAL)) {
			dart = new EntityDart(worldIn, playerIn, new ItemStack(ReforgedRegistry.DART_NORMAL));
			if(playerIn.capabilities.isCreativeMode || playerIn.inventory.consumeInventoryItem(ReforgedRegistry.DART_NORMAL));
		} else if(playerIn.inventory.hasItem(ReforgedRegistry.DART_HUNGER)) {
			dart = new EntityDart(worldIn, playerIn, new ItemStack(ReforgedRegistry.DART_HUNGER));
			if(playerIn.capabilities.isCreativeMode || playerIn.inventory.consumeInventoryItem(ReforgedRegistry.DART_HUNGER));
		} else if(playerIn.inventory.hasItem(ReforgedRegistry.DART_POISON)) {
			dart = new EntityDart(worldIn, playerIn, new ItemStack(ReforgedRegistry.DART_POISON));
			if(playerIn.capabilities.isCreativeMode || playerIn.inventory.consumeInventoryItem(ReforgedRegistry.DART_POISON));
		} else if(playerIn.inventory.hasItem(ReforgedRegistry.DART_POISON_2)) {
			dart = new EntityDart(worldIn, playerIn, new ItemStack(ReforgedRegistry.DART_POISON_2));
			if(playerIn.capabilities.isCreativeMode || playerIn.inventory.consumeInventoryItem(ReforgedRegistry.DART_POISON_2));
		} else if(playerIn.inventory.hasItem(ReforgedRegistry.DART_SLOW)) {
			dart = new EntityDart(worldIn, playerIn, new ItemStack(ReforgedRegistry.DART_SLOW));
			if(playerIn.capabilities.isCreativeMode || playerIn.inventory.consumeInventoryItem(ReforgedRegistry.DART_SLOW));
		} else if(playerIn.inventory.hasItem(ReforgedRegistry.DART_WITHER)) {
			dart = new EntityDart(worldIn, playerIn, new ItemStack(ReforgedRegistry.DART_WITHER));
			if(playerIn.capabilities.isCreativeMode || playerIn.inventory.consumeInventoryItem(ReforgedRegistry.DART_WITHER));
		} else {
			dart = null;
		}
		if(dart != null) {
			worldIn.spawnEntityInWorld(dart);
			itemStackIn.attemptDamageItem(1, this.itemRand);
			if(itemStackIn.getItemDamage() >= 40) {
				playerIn.inventory.consumeInventoryItem(itemStackIn.getItem());
			}
		}
		}
		return itemStackIn;
	}
	
	@Override
	public void registerRecipes() {
		GameRegistry.addShapedRecipe(new ItemStack(this),
				 "scs",
				 "c c",
				 "scs",
				 's', Items.string,
				 'c', Items.reeds);
	}
}
