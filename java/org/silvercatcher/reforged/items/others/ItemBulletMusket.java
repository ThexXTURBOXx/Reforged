package org.silvercatcher.reforged.items.others;

import org.silvercatcher.reforged.entities.EntityBulletMusket;
import org.silvercatcher.reforged.items.ReforgedItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBulletMusket extends ReforgedItem
{
	public ItemBulletMusket()
	{
		super("bullet_musket");
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,EntityPlayer par3EntityPlayer) {
	   
		if(par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.consumeInventoryItem(this))
	    {
	        par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
	        if (!par2World.isRemote) {
	        	
	        	par2World.spawnEntityInWorld(new EntityBulletMusket(par2World, par3EntityPlayer, par1ItemStack));
	        }
	    }
	    return par1ItemStack;
	}

	@Override
	public void registerRecipes() {
		
	}

	@Override
	public float getHitDamage() {
		return 0;
	}
}