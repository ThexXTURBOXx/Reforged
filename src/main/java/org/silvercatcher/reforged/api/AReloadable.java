package org.silvercatcher.reforged.api;

import java.util.List;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.util.Helpers;

import com.google.common.collect.Multimap;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AReloadable extends ItemBow implements ItemExtension {

	public static final byte empty = 0;
	public static final byte loading = 1;

	public static final byte loaded = 2;

	private Item ammo;
	private String shootsound;

	public AReloadable(String name, String shootsound) {
		setMaxStackSize(1);
		setMaxDamage(100);
		setUnlocalizedName(name);
		setCreativeTab(ReforgedMod.tabReforged);
		this.shootsound = shootsound;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag advanced) {

		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);

		tooltip.add(I18n.format("item.musket.loadstate") + ": "
				+ (loadState == empty ? I18n.format("item.musket.loadstate.empty")
						: (loadState == loaded ? I18n.format("item.musket.loadstate.loaded")
								: I18n.format("item.musket.loadstate.loading"))));
	}

	private Item getAmmo() {
		return ammo;
	}

	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		return ItemExtension.super.getAttributeModifiers(stack);
	}

	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

		if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
					new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", getHitDamage(), 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
					new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", getAttackSpeed(null), 0));
		}

		return multimap;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {

		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);

		if (loadState == loading) {
			if (ReforgedMod.battlegearDetected)
				return EnumAction.BOW;
			else
				return EnumAction.BLOCK;
		}
		if (loadState == loaded)
			return EnumAction.BOW;
		return EnumAction.NONE;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {

		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);

		if (loadState == loading)
			return getReloadTotal();

		return super.getMaxItemUseDuration(stack);
	}

	public int getReloadTime(ItemStack stack) {
		return giveCompound(stack).getInteger(CompoundTags.TIME);
	}

	public abstract int getReloadTotal();

	public NBTTagCompound giveCompound(ItemStack stack) {

		NBTTagCompound compound = CompoundTags.giveCompound(stack);

		if (!compound.hasKey(CompoundTags.AMMUNITION)) {

			compound.setByte(CompoundTags.AMMUNITION, empty);
		}
		return compound;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (hand == EnumHand.MAIN_HAND) {
			NBTTagCompound compound = giveCompound(playerIn.getHeldItemMainhand());

			byte loadState = compound.getByte(CompoundTags.AMMUNITION);

			if (loadState == empty) {
				if (playerIn.capabilities.isCreativeMode || Helpers.consumeInventoryItem(playerIn, getAmmo())) {

					loadState = loading;
					if (compound.getByte(CompoundTags.AMMUNITION) == empty) {
						compound.setBoolean(CompoundTags.STARTED, true);
						compound.setInteger(CompoundTags.TIME, 0);
					}
				} else {
					Helpers.playSound(worldIn, playerIn, "shotgun_reload", 1.0f, 0.7f);
				}
			}

			compound.setByte(CompoundTags.AMMUNITION, loadState);

			if (compound.getInteger(CompoundTags.TIME) <= 0 || !worldIn.isRemote
					|| (worldIn.isRemote && compound.getInteger(CompoundTags.TIME) >= getReloadTotal() - 1)) {
				playerIn.setActiveHand(hand);
			}

			return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItemMainhand());
		}
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, playerIn.getHeldItemOffhand());
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		return EnumActionResult.PASS;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase playerIn) {

		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);

		if (loadState == loading) {
			loadState = loaded;
		}
		giveCompound(stack).setByte(CompoundTags.AMMUNITION, loadState);
		return stack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase playerInl, int timeLeft) {
		if (!worldIn.isRemote && playerInl instanceof EntityPlayer) {
			EntityPlayerMP playerIn = (EntityPlayerMP) playerInl;
			NBTTagCompound compound = giveCompound(stack);
			byte loadState = compound.getByte(CompoundTags.AMMUNITION);
			if (loadState == loaded) {
				Helpers.playSound(worldIn, playerIn, shootsound, 1f, 1f);
				shoot(worldIn, playerIn, stack);
				if (!playerIn.capabilities.isCreativeMode && stack.getItem().isDamageable()
						&& stack.attemptDamageItem(5, itemRand, playerIn)) {
					playerIn.renderBrokenItemStack(stack);
					Helpers.destroyCurrentEquippedItem(playerIn);
				}
				compound.setByte(CompoundTags.AMMUNITION, empty);
				compound.setBoolean(CompoundTags.STARTED, false);
			}
			compound.setInteger(CompoundTags.TIME, -1);
		}
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		if (!(entityIn instanceof EntityLivingBase))
			return;
		if (giveCompound(stack).getBoolean(CompoundTags.STARTED)
				&& giveCompound(stack).getByte(CompoundTags.AMMUNITION) == loading
				&& ItemStack.areItemStacksEqual(stack, ((EntityLivingBase) entityIn).getActiveItemStack())) {
			giveCompound(stack).setInteger(CompoundTags.TIME, getReloadTime(stack) + 1);
		}
	}

	protected void setAmmo(Item ammo) {
		this.ammo = ammo;
	}

	public abstract void shoot(World worldIn, EntityLivingBase playerIn, ItemStack stack);

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return slotChanged;
	}

}