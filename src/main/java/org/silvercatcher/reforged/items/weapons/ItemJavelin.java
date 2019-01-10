package org.silvercatcher.reforged.items.weapons;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import org.silvercatcher.reforged.api.ExtendedItem;
import org.silvercatcher.reforged.api.ItemExtension;
import org.silvercatcher.reforged.entities.EntityJavelin;
import org.silvercatcher.reforged.util.Helpers;

public class ItemJavelin extends ExtendedItem {

	public ItemJavelin() {
		super();
		setUnlocalizedName("javelin");
		setMaxStackSize(8);
		setMaxDamage(32);
	}

	@Override
	public float getHitDamage() {
		return 3f;
	}

	@Override
	public int getItemEnchantability(ItemStack stack) {
		return ItemTier.STONE.getEnchantability();
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
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (hand == EnumHand.MAIN_HAND) {
			net.minecraftforge.event.entity.player.ArrowNockEvent event = new net.minecraftforge.event.entity.player.ArrowNockEvent(
					playerIn, playerIn.getHeldItemMainhand(), hand, worldIn, true);
			if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
				return event.getAction();

			if (playerIn.isCreative() || Helpers.getInventorySlotContainItem(playerIn, this) >= 0) {
				playerIn.setActiveHand(hand);
			}
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItemMainhand());
		}
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, playerIn.getHeldItemOffhand());
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase playerIn) {
		return stack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase playerInl, int timeLeft) {
		if (playerInl instanceof EntityPlayer) {

			EntityPlayer playerIn = (EntityPlayer) playerInl;

			ItemStack throwStack = stack.copy();

			if (timeLeft <= getMaxItemUseDuration(stack) - 7
					&& (playerIn.isCreative() || Helpers.consumeInventoryItem(playerIn, this))) {

				worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_ARROW_SHOOT,
						SoundCategory.MASTER, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
				if (!worldIn.isRemote) {
					if (throwStack.getCount() > 1) {
						throwStack = throwStack.splitStack(1);
					}
					worldIn.spawnEntity(
							new EntityJavelin(worldIn, playerIn, throwStack, stack.getMaxItemUseDuration() - timeLeft));
				}
			}
		}
	}

}
