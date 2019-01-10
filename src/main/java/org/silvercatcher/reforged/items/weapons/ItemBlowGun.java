package org.silvercatcher.reforged.items.weapons;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.silvercatcher.reforged.api.ExtendedItem;
import org.silvercatcher.reforged.api.ItemExtension;
import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.entities.EntityDart;
import org.silvercatcher.reforged.util.Helpers;

public class ItemBlowGun extends ExtendedItem {

	public ItemBlowGun() {
		super();
		setUnlocalizedName("blowgun");
		setMaxStackSize(1);
		setMaxDamage(40);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return ItemExtension.USE_DURATON;
	}

	@Override
	public boolean isWeapon() {
		return false;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (hand == EnumHand.MAIN_HAND) {
			net.minecraftforge.event.entity.player.ArrowNockEvent event = new net.minecraftforge.event.entity.player.ArrowNockEvent(
					playerIn, playerIn.getHeldItemMainhand(), hand, worldIn, true);
			if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
				return event.getAction();

			if (playerIn.isCreative() || Helpers.getInventorySlotContainItem(playerIn, this) != -1) {
				playerIn.setActiveHand(hand);
			}
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItemMainhand());
		}
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, playerIn.getHeldItemOffhand());
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase playerInl, int timeLeft) {
		if (timeLeft <= getMaxItemUseDuration(stack) - 15 && !worldIn.isRemote && playerInl instanceof EntityPlayer) {
			EntityDart dart;
			EntityPlayer playerIn = (EntityPlayer) playerInl;
			if (playerIn.inventory.hasItemStack(new ItemStack(ReforgedAdditions.DART_WITHER))) {
				dart = new EntityDart(worldIn, playerIn, new ItemStack(ReforgedAdditions.DART_WITHER));
				if (playerIn.isCreative()
						|| Helpers.consumeInventoryItem(playerIn, ReforgedAdditions.DART_WITHER))
					;
			} else if (playerIn.inventory.hasItemStack(new ItemStack(ReforgedAdditions.DART_POISON_STRONG))) {
				dart = new EntityDart(worldIn, playerIn, new ItemStack(ReforgedAdditions.DART_POISON_STRONG));
				if (playerIn.isCreative()
						|| Helpers.consumeInventoryItem(playerIn, ReforgedAdditions.DART_POISON_STRONG))
					;
			} else if (playerIn.inventory.hasItemStack(new ItemStack(ReforgedAdditions.DART_POISON))) {
				dart = new EntityDart(worldIn, playerIn, new ItemStack(ReforgedAdditions.DART_POISON));
				if (playerIn.isCreative()
						|| Helpers.consumeInventoryItem(playerIn, ReforgedAdditions.DART_POISON))
					;
			} else if (playerIn.inventory.hasItemStack(new ItemStack(ReforgedAdditions.DART_SLOW))) {
				dart = new EntityDart(worldIn, playerIn, new ItemStack(ReforgedAdditions.DART_SLOW));
				if (playerIn.isCreative()
						|| Helpers.consumeInventoryItem(playerIn, ReforgedAdditions.DART_SLOW))
					;
			} else if (playerIn.inventory.hasItemStack(new ItemStack(ReforgedAdditions.DART_HUNGER))) {
				dart = new EntityDart(worldIn, playerIn, new ItemStack(ReforgedAdditions.DART_HUNGER));
				if (playerIn.isCreative()
						|| Helpers.consumeInventoryItem(playerIn, ReforgedAdditions.DART_HUNGER))
					;
			} else if (playerIn.inventory.hasItemStack(new ItemStack(ReforgedAdditions.DART_NORMAL))) {
				dart = new EntityDart(worldIn, playerIn, new ItemStack(ReforgedAdditions.DART_NORMAL));
				if (playerIn.isCreative()
						|| Helpers.consumeInventoryItem(playerIn, ReforgedAdditions.DART_NORMAL))
					;
			} else if (playerIn.isCreative()) {
				dart = new EntityDart(worldIn, playerIn, new ItemStack(ReforgedAdditions.DART_WITHER));
			} else {
				dart = null;
			}
			if (dart != null) {
				worldIn.spawnEntity(dart);
				if (!playerIn.isCreative())
					if (stack.getItem().isDamageable())
						stack.attemptDamageItem(1, itemRand, null);
				if (stack.getItemDamage() >= 40) {
					Helpers.consumeInventoryItem(playerIn, stack.getItem());
				}
			}
		}
	}

}
