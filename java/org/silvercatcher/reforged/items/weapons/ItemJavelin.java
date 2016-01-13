package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.entities.EntityJavelin;
import org.silvercatcher.reforged.items.ReforgedItem;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemJavelin extends ReforgedItem {

	public ItemJavelin() {
		super("spear");
		setMaxStackSize(64);
	}
	
	@Override
	protected void mapEnchantments() {
		
		
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,EntityPlayer par3EntityPlayer) {
	   
		if(par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.consumeInventoryItem(this))
	    {
	        par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
	        if (!par2World.isRemote) {
	        	
	        	par2World.spawnEntityInWorld(new EntityJavelin(par2World, par3EntityPlayer, par1ItemStack));
	        }
	    }
	    return par1ItemStack;
	}
	
	@Override
	public void registerRecipes() {
		
	}

	@Override
	public float getHitDamage() {
		return 4;
	}
}
