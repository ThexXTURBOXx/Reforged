package org.silvercatcher.reforged.weapons;

import org.silvercatcher.reforged.ReforgedItems;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.proxy.CommonProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import scala.reflect.api.Trees.SelectFromTypeTreeExtractor;

public class ItemBoomerang extends MaterialItem
{
	public ItemBoomerang(ToolMaterial material)
	{
		super("boomerang", material);
		setMaxDamage(getMaxDamageForMaterial(material));
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,EntityPlayer par3EntityPlayer) {
	   
		if(par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.consumeInventoryItem(this))
	    {
	        par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
	        if (!par2World.isRemote) {
	        	
	        	par2World.spawnEntityInWorld(new EntityBoomerang(par2World, par3EntityPlayer, par1ItemStack));
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
		switch(material) {
		
		case EMERALD: return 100;
		
		case GOLD: return 40;
		
		case IRON: return 70;
		
		case STONE: return 50;
		
		case WOOD: return 40;
		
		default: return -1;		
		}
	}

	@Override
	public int getHitDamage() {
		
		//todo: consider material
		return 4;
	}
}