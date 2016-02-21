package org.silvercatcher.reforged.api.items;

import org.silvercatcher.reforged.api.APIExtendedItem;
import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class ThaumcraftBoomerang extends APIExtendedItem {
	
	protected final MaterialDefinition materialDefinition;
	
	public ThaumcraftBoomerang(ToolMaterial material) {
		
		super();
		setMaxStackSize(1);
		materialDefinition = MaterialManager.getMaterialDefinition(material);
		setMaxDamage((int) (materialDefinition.getMaxUses() * 0.8f));
		setUnlocalizedName(materialDefinition.getPrefixedName("boomerang"));
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
					"tss",
					"  s",
					"  t",
					't', materialDefinition.getRepairMaterial(),
					's', Items.stick);
	}
	
	/**
	 * this is weak melee combat damage!
	 * for ranged combat damage, see {@link EntityBoomerang#getImpactDamage}
	 */
	@Override
	public float getHitDamage() {
		
		return Math.max(1f, (0.5f + materialDefinition.getDamageVsEntity() * 0.5f));
	}
	
	public ToolMaterial getMaterial() {
		
		return materialDefinition.getMaterial();
	}
	
	@Override
	public int getItemEnchantability(ItemStack stack) {
		return materialDefinition.getEnchantability();
	}
}