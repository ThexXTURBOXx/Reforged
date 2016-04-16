package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.items.ExtendedItem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemFireRod extends ExtendedItem {

	public static final int FIRE_DURATION = 10;
	
	public ItemFireRod() {
		super();
		setUnlocalizedName("firerod");
		setMaxStackSize(32);
	}


	@Override
	public void registerRecipes() {
	
		GameRegistry.addRecipe(new ItemStack(this),
				"  c",
				" s ",
				"s  ",
				'c', new ItemStack(Items.COAL, 1, 0),
				's', Items.STICK);
		
		GameRegistry.addRecipe(new ItemStack(this),
				"  c",
				" s ",
				"s  ",
				'c', new ItemStack(Items.COAL, 1, 1),
				's', Items.STICK);
	}
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if(worldIn.getBlockState(pos).getBlock().isFlammable(worldIn, pos, facing)) {
			
			BlockPos target = pos.offset(facing);
			
			if(!(worldIn.canBlockSeeSky(pos) && worldIn.isRaining()) &&  worldIn.isAirBlock(target)) {
				worldIn.setBlockState(target, Blocks.FIRE.getDefaultState());
				--stack.stackSize;
			}
		}
		return EnumActionResult.SUCCESS;
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
