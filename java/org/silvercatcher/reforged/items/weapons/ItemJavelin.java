package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.entities.EntityJavelin;
import org.silvercatcher.reforged.items.ItemDefault;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemJavelin extends Item {

	public ItemJavelin() {
		
		setMaxStackSize(8);
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
	

	public void registerRecipes() {
		
	}


	public float getHitDamage() {
		return 4;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
    {
        return ItemDefault.USE_DURATON;
    }
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft) {
		
		if(playerIn.capabilities.isCreativeMode || playerIn.inventory.consumeInventoryItem(this)) {
			
			worldIn.playSoundAtEntity(playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

			if (!worldIn.isRemote) {
	        	
	        	worldIn.spawnEntityInWorld(new EntityJavelin(worldIn, playerIn, stack));
	        }
	    }
    }
}
