package org.silvercatcher.reforged.items.weapons;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.silvercatcher.reforged.api.ExtendedItem;
import org.silvercatcher.reforged.entities.EntityDynamite;
import org.silvercatcher.reforged.util.Helpers;

public class ItemDynamite extends ExtendedItem {

	public ItemDynamite() {
		super();
		setUnlocalizedName("dynamite");
		setMaxStackSize(64);
	}

	@Override
	public boolean isWeapon() {
		return false;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {

		if (playerIn.isCreative() || Helpers.consumeInventoryItem(playerIn, this)) {
			if (!worldIn.isRemote) {
				worldIn.spawnEntity(new EntityDynamite(worldIn, playerIn, playerIn.getHeldItem(hand)));
			}
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
	}

}
