package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.entities.EntityDart;
import org.silvercatcher.reforged.items.ExtendedItem;
import org.silvercatcher.reforged.items.others.ItemDart;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlowGun extends ExtendedItem {

	public ItemBlowGun() {
		
		super();
		setUnlocalizedName("blowgun");
		setMaxStackSize(1);
		setMaxDamage(40);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		
		if(!worldIn.isRemote && playerIn.inventory.hasItem(ReforgedRegistry.DART)) {
			playerIn.inventory.consumeInventoryItem(ReforgedRegistry.DART);
			ItemStack stack = null; //Add the ItemStack from above, I mean the ReforgedRegistry.DART with Metadata
			EntityDart dart = new EntityDart(worldIn, playerIn, stack);
			worldIn.spawnEntityInWorld(dart);
			itemStackIn.attemptDamageItem(1, this.itemRand);
			if(itemStackIn.getMaxDamage() - itemStackIn.getItemDamage() >= 0) {
				playerIn.inventory.consumeInventoryItem(itemStackIn.getItem());
			}
		}
		return itemStackIn;
	}
}
