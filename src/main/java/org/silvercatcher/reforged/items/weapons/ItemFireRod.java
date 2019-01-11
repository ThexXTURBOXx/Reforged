package org.silvercatcher.reforged.items.weapons;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.ExtendedItem;

public class ItemFireRod extends ExtendedItem {

	public static final int FIRE_DURATION = 10;

	public ItemFireRod() {
		super(new Item.Builder().maxStackSize(32));
		setRegistryName(new ResourceLocation(ReforgedMod.ID, "firerod"));
	}

	@Override
	public float getHitDamage() {
		return 1.5f;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (!target.isImmuneToFire()) {
			if (target instanceof EntityCreeper) {
				((EntityCreeper) target).ignite();
			} else {
				target.setFire(FIRE_DURATION);
			}
		}
		if (attacker instanceof EntityPlayer) {
			if (!((EntityPlayer) attacker).isCreative()) {
				stack.shrink(1);
			}
		} else {
			stack.shrink(1);
		}
		return true;
	}

	@Override
	public EnumActionResult onItemUse(ItemUseContext useContext) {
		EntityPlayer player = useContext.getPlayer();
		if (player != null && player.swingingHand == EnumHand.MAIN_HAND) {
			World world = useContext.getWorld();
			EnumFacing side = useContext.getFace();
			BlockPos pos = useContext.getPos();
			if (world.getBlockState(pos).isFlammable(world, pos, side)) {
				BlockPos target = pos.offset(side);
				if (!(world.canBlockSeeSky(pos) && world.isRaining()) && world.isAirBlock(target)) {
					world.setBlockState(target, Blocks.FIRE.getDefaultState());
					if (!player.isCreative())
						player.getHeldItemMainhand().shrink(1);
				}
			}
		}
		return EnumActionResult.SUCCESS;
	}

}
