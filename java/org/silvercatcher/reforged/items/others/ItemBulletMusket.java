package org.silvercatcher.reforged.items.others;

import org.silvercatcher.reforged.entities.EntityBulletMusket;
import org.silvercatcher.reforged.items.ItemReforged;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBulletMusket extends ItemReforged
{
	public ItemBulletMusket()
	{
		super("bullet_musket");
	}

	@Override
	public void registerRecipes() {
		
	}

	@Override
	public float getHitDamage() {
		return 0;
	}
}