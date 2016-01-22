package org.silvercatcher.reforged.items.others;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.entities.EntityBulletMusket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBulletMusket extends Item {
	
	public ItemBulletMusket() {
		
		setMaxStackSize(64);
		setUnlocalizedName("musket_bullet");
		
		setCreativeTab(ReforgedMod.tabReforged);
	}

	public void registerRecipes() {
		
	}


	public float getHitDamage() {
		return 0;
	}
}