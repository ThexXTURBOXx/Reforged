package org.silvercatcher.reforged.weapons;

import org.silvercatcher.reforged.ReforgedItems;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.proxy.CommonProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import scala.reflect.api.Trees.SelectFromTypeTreeExtractor;

public class ItemBoomerang extends ReforgedItem
{
	public ItemBoomerang(String name)
	{
		super(name);
		setCreativeTab(ReforgedMod.tabReforged);
		setUnlocalizedName(name);
		}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,EntityPlayer par3EntityPlayer) {
	    if(par3EntityPlayer.capabilities.isCreativeMode)
	    {
	        par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
	        if (!par2World.isRemote)
	        {
	            par2World.spawnEntityInWorld(new EntityBoomerang(par2World, par3EntityPlayer));
	        }
	    } else if (par3EntityPlayer.inventory.consumeInventoryItem(this)){
	    	par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
	        if (!par2World.isRemote)
	        {
	            par2World.spawnEntityInWorld(new EntityBoomerang(par2World, par3EntityPlayer));
	        }
	    }
	    return par1ItemStack;
	}
	
	public void registerTexture()
	{	
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation(ReforgedMod.ID + ":" 
				+ this.getUnlocalizedName().substring(5), "inventory"));
	}

	@Override
	public void registerRecipes() {
		
	}
}