package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.api.ExtendedItem;
import org.silvercatcher.reforged.entities.EntityCannon;
import org.silvercatcher.reforged.util.Helpers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCannon extends ExtendedItem {

	public ItemCannon() {
		super();
		setUnlocalizedName("cannon");
		setMaxStackSize(1);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(player.capabilities.isCreativeMode || Helpers.consumeInventoryItem(player, this)) {
			if(!worldIn.isRemote) {
				worldIn.spawnEntity(new EntityCannon(worldIn, pos.getX(), pos.getY(), pos.getZ()));
			}
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

}