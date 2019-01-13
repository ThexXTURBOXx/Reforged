package org.silvercatcher.reforged.api;

import com.google.common.collect.Multimap;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.util.Helpers;

public abstract class AReloadable extends ItemBow implements ItemExtension {

	public static final byte empty = 0;
	public static final byte loading = 1;

	public static final byte loaded = 2;

	private Item ammo;
	private String shootsound;

	public AReloadable(Item.Builder builder, String name, String shootsound) {
		super(builder.defaultMaxDamage(100).group(ReforgedMod.tabReforged));
		setRegistryName(new ResourceLocation(ReforgedMod.ID, name));
		this.shootsound = shootsound;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);
		tooltip.add(new TextComponentString(I18n.format("item.reforged.musket.loadstate") + ": "
				+ (loadState == empty ? I18n.format("item.reforged.musket.loadstate.empty")
				: (loadState == loaded ? I18n.format("item.reforged.musket.loadstate.loaded")
				: I18n.format("item.reforged.musket.loadstate.loading")))));
	}

	private Item getAmmo() {
		return ammo;
	}

	protected void setAmmo(Item ammo) {
		this.ammo = ammo;
	}

	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		return ItemExtension.super.getAttributeModifiers(stack);
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot);
		if (slot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
					new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", getHitDamage(), 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
					new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", getAttackSpeed(null), 0));
		}
		return multimap;
	}

	@Override
	public EnumAction getUseAction(ItemStack stack) {
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
	public int getUseDuration(ItemStack stack) {
		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);

		if (loadState == loading)
			return getReloadTotal();

		return super.getUseDuration(stack);
	}

	public int getReloadTime(ItemStack stack) {
		return giveCompound(stack).getInt(CompoundTags.TIME);
	}

	public abstract int getReloadTotal();

	public NBTTagCompound giveCompound(ItemStack stack) {

		NBTTagCompound compound = CompoundTags.giveCompound(stack);

		if (!compound.hasUniqueId(CompoundTags.AMMUNITION)) {

			compound.putByte(CompoundTags.AMMUNITION, empty);
		}
		return compound;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (hand == EnumHand.MAIN_HAND) {
			NBTTagCompound compound = giveCompound(playerIn.getHeldItemMainhand());

			byte loadState = compound.getByte(CompoundTags.AMMUNITION);

			if (loadState == empty) {
				if (playerIn.isCreative() || Helpers.consumeInventoryItem(playerIn, getAmmo())) {

					loadState = loading;
					if (compound.getByte(CompoundTags.AMMUNITION) == empty) {
						compound.putBoolean(CompoundTags.STARTED, true);
						compound.putInt(CompoundTags.TIME, 0);
					}
				} else {
					Helpers.playSound(worldIn, playerIn, "shotgun_reload", 1.0f, 0.7f);
				}
			}

			compound.putByte(CompoundTags.AMMUNITION, loadState);

			if (compound.getInt(CompoundTags.TIME) <= 0 || !worldIn.isRemote
					|| (compound.getInt(CompoundTags.TIME) >= getReloadTotal() - 1)) {
				playerIn.setActiveHand(hand);
			}

			return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItemMainhand());
		}
		return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItemOffhand());
	}

	@Override
	public EnumActionResult onItemUse(ItemUseContext p_195939_1_) {
		return EnumActionResult.PASS;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase playerIn) {
		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);

		if (loadState == loading) {
			loadState = loaded;
		}
		giveCompound(stack).putByte(CompoundTags.AMMUNITION, loadState);
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
				if (!playerIn.isCreative() && stack.getItem().isDamageable()
						&& stack.attemptDamageItem(5, random, playerIn)) {
					playerIn.renderBrokenItemStack(stack);
					Helpers.destroyCurrentEquippedItem(playerIn);
				}
				compound.putByte(CompoundTags.AMMUNITION, empty);
				compound.putBoolean(CompoundTags.STARTED, false);
			}
			compound.putInt(CompoundTags.TIME, -1);
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
		if (!(entityIn instanceof EntityLivingBase))
			return;
		if (giveCompound(stack).getBoolean(CompoundTags.STARTED)
				&& giveCompound(stack).getByte(CompoundTags.AMMUNITION) == loading
				&& ItemStack.areItemStacksEqual(stack, ((EntityLivingBase) entityIn).getActiveItemStack())) {
			giveCompound(stack).putInt(CompoundTags.TIME, getReloadTime(stack) + 1);
		}
	}

	public abstract void shoot(World worldIn, EntityLivingBase playerIn, ItemStack stack);

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return slotChanged;
	}

}