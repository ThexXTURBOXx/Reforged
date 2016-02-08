package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.items.ExtendedItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemFireRod extends ExtendedItem {

	public static final int FIRE_DURATION = 10;
	
	public ItemFireRod() {
		
		setUnlocalizedName("firerod");
		setMaxStackSize(32);
	}


	@Override
	public void registerRecipes() {
	
		GameRegistry.addRecipe(new ItemStack(this),
				"  c",
				" s ",
				"s  ",
				'c', new ItemStack(Items.coal, 1, 0),
				's', Items.stick);
		
		GameRegistry.addRecipe(new ItemStack(this),
				"  c",
				" s ",
				"s  ",
				'c', new ItemStack(Items.coal, 1, 1),
				's', Items.stick);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		
		if(worldIn.getBlockState(pos).getBlock().isFlammable(worldIn, pos, side)) {
			
			BlockPos target = pos.offset(side);
			
			if(!(worldIn.canBlockSeeSky(pos) && worldIn.isRaining()) &&  worldIn.isAirBlock(target)) {
				worldIn.setBlockState(target, Blocks.fire.getDefaultState());
				--stack.stackSize;
			}
		}
		return true;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		
		if(!entity.isImmuneToFire()) {
			entity.setFire(FIRE_DURATION);
		}
		if(!player.capabilities.isCreativeMode) {
			--stack.stackSize;
		}
		return false;
	}


	@Override
	public float getHitDamage() {
		return 1.5f;
	}
}
