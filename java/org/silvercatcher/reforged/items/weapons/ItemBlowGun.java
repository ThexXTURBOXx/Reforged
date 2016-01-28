package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.entities.EntityDart;
import org.silvercatcher.reforged.items.ExtendedItem;
import org.silvercatcher.reforged.util.InventoryIterator;

import net.minecraft.entity.player.EntityPlayer;
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
		
		if(!worldIn.isRemote) {
			int num = InventoryIterator.getNearestDart(playerIn);
			ItemStack stack = new ItemStack(playerIn.inventory.getStackInSlot(num).getItem(), 1);
			playerIn.inventory.consumeInventoryItem(stack.getItem());
			EntityDart dart = new EntityDart(worldIn, playerIn, stack);
			worldIn.spawnEntityInWorld(dart);
		}
		return itemStackIn;
	}
}
