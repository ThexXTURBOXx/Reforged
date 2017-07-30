package org.silvercatcher.reforged.items.weapons;

import java.util.List;

import javax.annotation.Nullable;

import org.silvercatcher.reforged.api.*;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemNestOfBees extends ExtendedItem {

	private static int shot_delay = 4;
	private static int buildup = 25;

	public ItemNestOfBees() {
		super();
		setUnlocalizedName("nest_of_bees");
		setMaxDamage(80);
		setMaxStackSize(1);
		addPropertyOverride(new ResourceLocation("empty"), new IItemPropertyGetter() {
			@Override
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				float mrl = 1;
				if (stack.getItem() instanceof ItemNestOfBees
						&& CompoundTags.giveCompound(stack).getInteger(CompoundTags.AMMUNITION) > 0) {
					mrl = 0;
				}
				return mrl;
			}
		});
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag advanced) {

		tooltip.add(I18n.format("item.nestofbees.arrows") + ": "
				+ CompoundTags.giveCompound(stack).getInteger(CompoundTags.AMMUNITION));
	}

	@Override
	public float getHitDamage() {

		return 0f;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {

		NBTTagCompound compound = CompoundTags.giveCompound(stack);

		if (compound.getBoolean(CompoundTags.ACTIVATED)) {
			return EnumAction.BOW;
		}
		return EnumAction.NONE;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {

		return CompoundTags.giveCompound(stack).getBoolean(CompoundTags.ACTIVATED) ? ItemExtension.USE_DURATON
				: buildup;
	}

	@Override
	public boolean isWeapon() {
		return false;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (hand == EnumHand.MAIN_HAND) {
			playerIn.setActiveHand(hand);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItemMainhand());
		}
		// System.out.println(playerIn.getItemInUseDuration());
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, playerIn.getHeldItemOffhand());
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase playerIn) {

		NBTTagCompound compound = CompoundTags.giveCompound(stack);

		if (compound.getInteger(CompoundTags.AMMUNITION) > 0) {
			compound.setBoolean(CompoundTags.ACTIVATED, true);

			worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ITEM_FIRECHARGE_USE,
					SoundCategory.MASTER, 1.0f, 1.0f);
		}
		return stack;
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {

		if (entityIn instanceof EntityPlayer && isSelected) {

			EntityPlayer player = (EntityPlayer) entityIn;

			NBTTagCompound compound = CompoundTags.giveCompound(stack);

			// System.out.println(compound.getBoolean(CompoundTags.ACTIVATED));

			int delay = compound.getInteger(CompoundTags.DELAY);

			if (delay == 0) {

				int arrows = compound.getInteger(CompoundTags.AMMUNITION);

				if (arrows == 0)
					compound.setBoolean(CompoundTags.ACTIVATED, false);

				if (compound.getBoolean(CompoundTags.ACTIVATED)) {
					shoot(worldIn, player);
					if (stack.getItem().isDamageable())
						stack.damageItem(1, player);
					arrows--;
				}

				compound.setInteger(CompoundTags.AMMUNITION, arrows);
				compound.setInteger(CompoundTags.DELAY, shot_delay);

			} else if (compound.getBoolean(CompoundTags.ACTIVATED)) {

				compound.setInteger(CompoundTags.DELAY, Math.max(0, delay - 1));
			}
		}
	}

	protected void shoot(World world, EntityPlayer shooter) {

		if (!world.isRemote) {
			EntityArrow arrow = new ItemArrow().createArrow(world, new ItemStack(Items.ARROW), shooter);
			arrow.setAim(shooter, shooter.rotationPitch, shooter.rotationYaw, 0.0F, ItemBow.getArrowVelocity(40) * 3.0F,
					1.0F);
			arrow.setDamage(2);
			arrow.setThrowableHeading(arrow.motionX, arrow.motionY, arrow.motionZ, 3 + itemRand.nextFloat() / 2f, 1.5f);
			world.spawnEntity(arrow);
		}
		world.playSound(null, shooter.posX, shooter.posY, shooter.posZ, SoundEvents.ENTITY_FIREWORK_LAUNCH,
				SoundCategory.MASTER, 3.0f, 1.0f);
	}
}
