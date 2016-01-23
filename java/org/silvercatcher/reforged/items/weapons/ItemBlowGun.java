package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.items.ExtendedItem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
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
			EntityArrow dart = new EntityArrow(worldIn, playerIn, 0.5f);
			worldIn.spawnEntityInWorld(dart);
		}
		return itemStackIn;
	}
}
