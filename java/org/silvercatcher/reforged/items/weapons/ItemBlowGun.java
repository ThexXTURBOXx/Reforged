package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.entities.EntityDart;
import org.silvercatcher.reforged.items.ExtendedItem;
import org.silvercatcher.reforged.items.ItemExtension;
import org.silvercatcher.reforged.util.Helpers1dot9;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemBlowGun extends ExtendedItem {

	public ItemBlowGun() {
		
		super();
		setUnlocalizedName("blowgun");
		setMaxStackSize(1);
		setMaxDamage(40);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (playerIn.capabilities.isCreativeMode || playerIn.inventory.hasItemStack(new ItemStack(this))) {
			playerIn.setActiveHand(hand);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
			//playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
		}
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase player, int timeLeft) {
		if(timeLeft <= getMaxItemUseDuration(stack) - 15) {
			EntityDart dart;
			EntityPlayer playerIn = null;
			if(player instanceof EntityPlayer) playerIn = (EntityPlayer) player;
			if(!worldIn.isRemote) {
				if(playerIn.inventory.hasItemStack(new ItemStack(ReforgedAdditions.DART_NORMAL))) {
					dart = new EntityDart(worldIn, playerIn, new ItemStack(ReforgedAdditions.DART_NORMAL));
					if(playerIn.capabilities.isCreativeMode || Helpers1dot9.consumeItem(playerIn.inventory, ReforgedAdditions.DART_NORMAL));
				} else if(playerIn.inventory.hasItemStack(new ItemStack(ReforgedAdditions.DART_HUNGER))) {
					dart = new EntityDart(worldIn, playerIn, new ItemStack(ReforgedAdditions.DART_HUNGER));
					if(playerIn.capabilities.isCreativeMode || Helpers1dot9.consumeItem(playerIn.inventory, ReforgedAdditions.DART_HUNGER));
				} else if(playerIn.inventory.hasItemStack(new ItemStack(ReforgedAdditions.DART_POISON))) {
					dart = new EntityDart(worldIn, playerIn, new ItemStack(ReforgedAdditions.DART_POISON));
					if(playerIn.capabilities.isCreativeMode || Helpers1dot9.consumeItem(playerIn.inventory, ReforgedAdditions.DART_POISON));
				} else if(playerIn.inventory.hasItemStack(new ItemStack(ReforgedAdditions.DART_POISON_STRONG))) {
					dart = new EntityDart(worldIn, playerIn, new ItemStack(ReforgedAdditions.DART_POISON_STRONG));
					if(playerIn.capabilities.isCreativeMode || Helpers1dot9.consumeItem(playerIn.inventory, ReforgedAdditions.DART_POISON_STRONG));
				} else if(playerIn.inventory.hasItemStack(new ItemStack(ReforgedAdditions.DART_SLOW))) {
					dart = new EntityDart(worldIn, playerIn, new ItemStack(ReforgedAdditions.DART_SLOW));
					if(playerIn.capabilities.isCreativeMode || Helpers1dot9.consumeItem(playerIn.inventory, ReforgedAdditions.DART_SLOW));
				} else if(playerIn.inventory.hasItemStack(new ItemStack(ReforgedAdditions.DART_WITHER)) || playerIn.capabilities.isCreativeMode) {
					dart = new EntityDart(worldIn, playerIn, new ItemStack(ReforgedAdditions.DART_WITHER));
					if(playerIn.capabilities.isCreativeMode || Helpers1dot9.consumeItem(playerIn.inventory, ReforgedAdditions.DART_WITHER));
				} else {
					dart = null;
				}
				if(dart != null) {
					worldIn.spawnEntityInWorld(dart);
					if(!playerIn.capabilities.isCreativeMode) stack.attemptDamageItem(1, itemRand);
					if(stack.getItemDamage() >= 40) {
						Helpers1dot9.consumeItem(playerIn.inventory, stack.getItem());
					}
				}
			}
		}
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
    {
        return ItemExtension.USE_DURATON;
    }
	
	@Override
	public void registerRecipes() {
		GameRegistry.addShapedRecipe(new ItemStack(this),
				 "r  ",
				 " r ",
				 "  r",
				 'r', Items.REEDS);
	}
}
