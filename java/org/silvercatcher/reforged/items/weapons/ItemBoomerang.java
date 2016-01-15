package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.items.MaterialItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemBoomerang extends MaterialItem
{
	public ItemBoomerang(ToolMaterial material)
	{
		super("boomerang", material);
		setMaxStackSize(1);
		setMaxDamage(getMaxDamageForMaterial(material));
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,EntityPlayer par3EntityPlayer) {
	   
		// import, otherwise references will cause chaos!
		ItemStack throwStack = par1ItemStack.copy();
		
		if(par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.consumeInventoryItem(this))
	    {
	        par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        	
	        if (!par2World.isRemote) {
	        	
	        	EntityBoomerang boomerang = new EntityBoomerang(par2World, par3EntityPlayer, throwStack);
	        	par2World.spawnEntityInWorld(boomerang);
	        }
	    }
	    return par1ItemStack;
	}

	@Override
	public void registerRecipes() {
		GameRegistry.addRecipe(new ItemStack(this),
			"xww",
			"  w",
			"  x",
			'x', material.getRepairItemStack(),
			'w', Items.stick);
		
	}

	@Override
	protected int getMaxDamageForMaterial(ToolMaterial material) {
		
		return (int) (material.getMaxUses() * 0.8f);
	}

	/**
	 * this is weak melee combat damage!
	 * for ranged combat damage, see {@link EntityBoomerang#getImpactDamage}
	 */
	@Override
	public float getHitDamage() {
		
		return Math.max(1f, (0.5f + material.getDamageVsEntity() * 0.5f));
	}
}