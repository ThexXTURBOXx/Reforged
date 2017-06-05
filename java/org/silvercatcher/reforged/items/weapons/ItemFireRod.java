package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.api.ExtendedItem;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
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
	public float getHitDamage() {
		return 1.5f;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if(stack.getItem().isDamageable())
			stack.damageItem(2, attacker);
		if (!target.isImmuneToFire()) {
			target.setFire(FIRE_DURATION);
		}
		if (attacker instanceof EntityPlayer) {
			if (!((EntityPlayer) attacker).capabilities.isCreativeMode) {
				stack.setCount(stack.getCount() - 1);
			}
		}
		return false;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		if (worldIn.getBlockState(pos).getBlock().isFlammable(worldIn, pos, side)) {

			BlockPos target = pos.offset(side);

			if (!(worldIn.canBlockSeeSky(pos) && worldIn.isRaining()) && worldIn.isAirBlock(target)) {
				worldIn.setBlockState(target, Blocks.FIRE.getDefaultState());
				player.getHeldItemMainhand().setCount(player.getHeldItemMainhand().getCount() - 1);
			}
		}
		return EnumActionResult.SUCCESS;
	}

	@Override
	public void registerRecipes() {

		GameRegistry.addRecipe(new ItemStack(this), "  c", " s ", "s  ", 'c', new ItemStack(Items.COAL, 1, 0), 's',
				Items.STICK);

		GameRegistry.addRecipe(new ItemStack(this), "  c", " s ", "s  ", 'c', new ItemStack(Items.COAL, 1, 1), 's',
				Items.STICK);
	}
}
