package org.silvercatcher.reforged.items.weapons;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.silvercatcher.reforged.api.ExtendedItem;
import org.silvercatcher.reforged.entities.EntityCannon;
import org.silvercatcher.reforged.util.Helpers;

public class ItemCannon extends ExtendedItem {

	public ItemCannon() {
		super();
		setUnlocalizedName("cannon");
		setMaxStackSize(1);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
									  EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (player.isCreative() || Helpers.consumeInventoryItem(player, this)) {
			if (!worldIn.isRemote) {
				worldIn.spawnEntity(new EntityCannon(worldIn, pos.getX(), pos.getY(), pos.getZ()));
			}
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

}