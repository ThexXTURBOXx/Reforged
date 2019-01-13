package org.silvercatcher.reforged.items.weapons;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.CompoundTags;
import org.silvercatcher.reforged.api.ExtendedItem;
import org.silvercatcher.reforged.api.ItemExtension;

public class ItemNestOfBees extends ExtendedItem {

	private static int shot_delay = 4;
	private static int buildup = 25;

	public ItemNestOfBees() {
		super(new Item.Builder().defaultMaxDamage(80));
		setRegistryName(new ResourceLocation(ReforgedMod.ID, "nest_of_bees"));
		addPropertyOverride(new ResourceLocation("empty"), (stack, world, entity) -> {
			float mrl = 1;
			if (stack.getItem() instanceof ItemNestOfBees
					&& CompoundTags.giveCompound(stack).getInt(CompoundTags.AMMUNITION) > 0) {
				mrl = 0;
			}
			return mrl;
		});
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TextComponentString(I18n.format("item.reforged.nestofbees.arrows") + ": "
				+ CompoundTags.giveCompound(stack).getInt(CompoundTags.AMMUNITION)));
	}

	@Override
	public float getHitDamage() {

		return 0f;
	}

	@Override
	public EnumAction getUseAction(ItemStack stack) {
		NBTTagCompound compound = CompoundTags.giveCompound(stack);

		if (compound.getBoolean(CompoundTags.ACTIVATED)) {
			return EnumAction.BOW;
		}
		return EnumAction.NONE;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
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
			return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItemMainhand());
		}
		// System.out.println(playerIn.getItemInUseDuration());
		return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItemOffhand());
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase playerIn) {

		NBTTagCompound compound = CompoundTags.giveCompound(stack);

		if (compound.getInt(CompoundTags.AMMUNITION) > 0) {
			compound.putBoolean(CompoundTags.ACTIVATED, true);

			worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ITEM_FIRECHARGE_USE,
					SoundCategory.MASTER, 1.0f, 1.0f);
		}
		return stack;
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (entityIn instanceof EntityPlayer && isSelected) {

			EntityPlayer player = (EntityPlayer) entityIn;

			NBTTagCompound compound = CompoundTags.giveCompound(stack);

			// System.out.println(compound.getBoolean(CompoundTags.ACTIVATED));

			int delay = compound.getInt(CompoundTags.DELAY);

			if (delay == 0) {

				int arrows = compound.getInt(CompoundTags.AMMUNITION);

				if (arrows == 0)
					compound.putBoolean(CompoundTags.ACTIVATED, false);

				if (compound.getBoolean(CompoundTags.ACTIVATED)) {
					shoot(worldIn, player);
					if (stack.getItem().isDamageable())
						stack.damageItem(1, player);
					arrows--;
				}

				compound.putInt(CompoundTags.AMMUNITION, arrows);
				compound.putInt(CompoundTags.DELAY, shot_delay);

			} else if (compound.getBoolean(CompoundTags.ACTIVATED)) {

				compound.putInt(CompoundTags.DELAY, Math.max(0, delay - 1));
			}
		}
	}

	protected void shoot(World world, EntityPlayer shooter) {

		if (!world.isRemote) {
			EntityArrow arrow = new ItemArrow(new Item.Builder()).createArrow(world, new ItemStack(Items.ARROW), shooter);
			arrow.shoot(shooter, shooter.rotationPitch, shooter.rotationYaw, 0.0F, ItemBow.getArrowVelocity(40) * 3.0F,
					1.0F);
			arrow.setDamage(2);
			arrow.shoot(arrow.motionX, arrow.motionY, arrow.motionZ, 3 + random.nextFloat() / 2f, 1.5f);
			world.spawnEntity(arrow);
		}
		world.playSound(null, shooter.posX, shooter.posY, shooter.posZ, SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH,
				SoundCategory.MASTER, 3.0f, 1.0f);
	}
}
