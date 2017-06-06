package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.api.ExtendedItem;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
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
		super();
		setUnlocalizedName("firerod");
		setMaxStackSize(32);
	}

	@Override
	public float getHitDamage() {
		return 1.5f;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase entity, EntityLivingBase player) {
		if (!entity.isImmuneToFire()) {
			if (entity instanceof EntityCreeper) {
				((EntityCreeper) entity).ignite();
			} else {
				entity.setFire(FIRE_DURATION);
			}
		}
		if (!(player instanceof EntityPlayer) || !((EntityPlayer) player).capabilities.isCreativeMode) {
			--stack.stackSize;
		}
		return true;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side,
			float hitX, float hitY, float hitZ) {

		if (world.getBlockState(pos).getBlock().isFlammable(world, pos, side)) {

			BlockPos target = pos.offset(side);

			if (!(world.canBlockSeeSky(pos) && world.isRaining()) && world.isAirBlock(target)) {
				world.setBlockState(target, Blocks.fire.getDefaultState());
				--stack.stackSize;
			}
		}
		return true;
	}

	@Override
	public void registerRecipes() {

		GameRegistry.addRecipe(new ItemStack(this), "  c", " s ", "s  ", 'c', new ItemStack(Items.coal, 1, 0), 's',
				Items.stick);

		GameRegistry.addRecipe(new ItemStack(this), "  c", " s ", "s  ", 'c', new ItemStack(Items.coal, 1, 1), 's',
				Items.stick);
	}
}
