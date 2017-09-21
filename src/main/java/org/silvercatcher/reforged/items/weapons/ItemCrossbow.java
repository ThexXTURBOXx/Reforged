package org.silvercatcher.reforged.items.weapons;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.*;
import org.silvercatcher.reforged.entities.EntityCrossbowBolt;
import org.silvercatcher.reforged.util.Helpers;

import com.google.common.collect.Multimap;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ItemCrossbow extends ItemBow implements ItemExtension {

	public static final byte empty = 0;

	public static final byte loading = 1;

	public static final byte loaded = 2;

	public ItemCrossbow() {
		setMaxStackSize(1);
		setMaxDamage(100);
		setUnlocalizedName("crossbow");
		setCreativeTab(ReforgedMod.tabReforged);
		addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter() {
			@Override
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				float mrl = 0;
				if (entityIn != null && entityIn instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) entityIn;
					if (stack.getItem() instanceof ItemCrossbow && player.getActiveHand() == EnumHand.MAIN_HAND) {
						if (getLoadState(stack) == loading) {
							int left = getReloadLeft(stack, player);
							if (left > 14) {
								mrl = 1;
							} else if (left > 9) {
								mrl = 2;
							} else if (left > 4) {
								mrl = 4;
							} else if (left >= 0) {
								mrl = 5;
							}
						} else if (getLoadState(stack) == loaded) {
							mrl = 3;
						}
					}
				}
				return mrl;
			}
		});
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {

		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);

		tooltip.add(I18n.format("item.musket.loadstate") + ": "
				+ (loadState == empty ? I18n.format("item.musket.loadstate.empty")
						: (loadState == loaded ? I18n.format("item.musket.loadstate.loaded")
								: I18n.format("item.musket.loadstate.loading"))));
	}

	private ItemStack findAmmo(EntityPlayer player) {
		if (this.isArrow(player.getHeldItem(EnumHand.OFF_HAND))) {
			return player.getHeldItem(EnumHand.OFF_HAND);
		} else if (this.isArrow(player.getHeldItem(EnumHand.MAIN_HAND))) {
			return player.getHeldItem(EnumHand.MAIN_HAND);
		} else {
			for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
				ItemStack itemstack = player.inventory.getStackInSlot(i);
				if (this.isArrow(itemstack)) {
					return itemstack;
				}
			}
			return ItemStack.EMPTY;
		}
	}

	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		return ItemExtension.super.getAttributeModifiers(stack);
	}

	@Override
	public float getHitDamage() {
		return 2f;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {

		byte loadState = getLoadState(stack);

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

	public byte getLoadState(ItemStack stack) {
		return giveCompound(stack).getByte(CompoundTags.AMMUNITION);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {

		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);

		if (loadState == loading)
			return getReloadTotal();

		return super.getMaxItemUseDuration(stack);
	}

	public int getReloadLeft(ItemStack stack, EntityPlayer player) {
		return (getReloadTotal() - getReloadTime(stack));
	}

	public int getReloadTime(ItemStack stack) {
		return giveCompound(stack).getInteger(CompoundTags.TIME);
	}

	public int getReloadTotal() {
		return 20;
	}

	public NBTTagCompound giveCompound(ItemStack stack) {

		NBTTagCompound compound = CompoundTags.giveCompound(stack);

		if (!compound.hasKey(CompoundTags.AMMUNITION)) {

			compound.setByte(CompoundTags.AMMUNITION, empty);
		}
		return compound;
	}

	@Override
	protected boolean isArrow(ItemStack stack) {
		return stack.getItem() instanceof ItemArrow;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (hand == EnumHand.MAIN_HAND) {
			NBTTagCompound compound = giveCompound(playerIn.getHeldItemMainhand());

			byte loadState = compound.getByte(CompoundTags.AMMUNITION);

			if (loadState == empty) {
				if (playerIn.capabilities.isCreativeMode
						|| Helpers.consumeInventoryItem(playerIn, ReforgedAdditions.CROSSBOW_BOLT)) {
					loadState = loading;
					if (compound.getByte(CompoundTags.AMMUNITION) == empty) {
						compound.setBoolean(CompoundTags.STARTED, true);
						compound.setInteger(CompoundTags.TIME, 0);
					}
				} else {
					Helpers.playSound(worldIn, playerIn, "crossbow_reload", 1.0f, 0.7f);
				}
			}

			compound.setByte(CompoundTags.AMMUNITION, loadState);

			if (compound.getInteger(CompoundTags.TIME) <= 0 || !worldIn.isRemote
					|| (worldIn.isRemote && compound.getInteger(CompoundTags.TIME) >= getReloadTotal() - 1)) {
				playerIn.setActiveHand(hand);
			}

			return new ActionResult(EnumActionResult.SUCCESS, playerIn.getHeldItemMainhand());
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
			EntityPlayer playerIn = (EntityPlayer) playerInl;
			NBTTagCompound compound = giveCompound(stack);
			byte loadState = compound.getByte(CompoundTags.AMMUNITION);
			if (loadState == loaded) {
				Helpers.playSound(worldIn, playerIn, "crossbow_shoot", 1f, 1f);
				shoot(worldIn, playerIn, new ItemStack(ReforgedAdditions.CROSSBOW_BOLT));
				if (!playerIn.capabilities.isCreativeMode
						&& (stack.getItem().isDamageable() && stack.attemptDamageItem(5, itemRand))) {
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
		if (giveCompound(stack).getBoolean(CompoundTags.STARTED) && getLoadState(stack) == loading) {
			giveCompound(stack).setInteger(CompoundTags.TIME, getReloadTime(stack) + 1);
		}
	}

	@Override
	public void registerRecipes() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this), "bii", "iw ", "i w", 'b', Items.BOW, 'i', "ingotIron",
				'w', "plankWood"));
	}

	public void shoot(World worldIn, EntityLivingBase playerIn, ItemStack stack) {
		EntityCrossbowBolt a = new EntityCrossbowBolt(worldIn, playerIn);
		a.setAim(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, getArrowVelocity(40) * 3.0F, 1.0F);
		a.pickupStatus = PickupStatus.getByOrdinal(new Random().nextInt(2));
		a.setDamage(8.0D);
		worldIn.spawnEntity(a);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return !slotChanged;
	}

}