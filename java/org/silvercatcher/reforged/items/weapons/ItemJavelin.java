package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.entities.EntityJavelin;
import org.silvercatcher.reforged.items.ItemExtension;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;

import com.google.common.collect.Multimap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemJavelin extends AWeapon {
	
	public ItemJavelin() {
		
		setUnlocalizedName("javelin");
		setMaxStackSize(8);
		setMaxDamage(32);
		
		setCreativeTab(ReforgedMod.tabReforged);
	}

	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
        net.minecraftforge.event.entity.player.ArrowNockEvent event = new net.minecraftforge.event.entity.player.ArrowNockEvent(playerIn, itemStackIn);
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return event.result;

        	if (playerIn.capabilities.isCreativeMode || playerIn.inventory.hasItem(this))
        		{
        			playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
        		}

        return itemStackIn;
    }
	

	@Override
	public void registerRecipes() {
		
	}


	@Override
	public float getHitDamage() {
		return 3f;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
    {
        return ItemExtension.USE_DURATON;
    }
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft) {

		ItemStack throwStack = stack.copy();
		
		if(timeLeft <= getMaxItemUseDuration(stack) - 7 && (playerIn.capabilities.isCreativeMode || playerIn.inventory.consumeInventoryItem(this))) {
			
			worldIn.playSoundAtEntity(playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

			if (!worldIn.isRemote) {
	        	
	        	worldIn.spawnEntityInWorld(new EntityJavelin(worldIn, playerIn, throwStack, stack.getMaxItemUseDuration() - timeLeft));
	        }
	    }
    }
}
