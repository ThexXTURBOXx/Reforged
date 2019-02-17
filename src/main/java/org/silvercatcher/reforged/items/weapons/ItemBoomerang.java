package org.silvercatcher.reforged.items.weapons;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.ExtendedItem;
import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;
import org.silvercatcher.reforged.util.Helpers;

public class ItemBoomerang extends ExtendedItem {

	protected final MaterialDefinition materialDefinition;
	protected final boolean unbreakable;

	public ItemBoomerang(IItemTier material) {
		this(material, false);
	}

	public ItemBoomerang(IItemTier material, boolean unbreakable) {
		super(new Item.Properties().defaultMaxDamage((int) (material.getMaxUses() * 0.8f)));
		this.unbreakable = unbreakable;
		materialDefinition = MaterialManager.getMaterialDefinition(material);
		setRegistryName(new ResourceLocation(ReforgedMod.ID, materialDefinition.getPrefixedName("boomerang")));
	}

	@Override
	public float getHitDamage() {
		//Melee damage!!!
		return Math.max(1f, (0.5f + materialDefinition.getDamageVsEntity() * 0.5f));
	}

	@Override
	public int getItemEnchantability(ItemStack stack) {
		return materialDefinition.getEnchantability();
	}

	public IItemTier getMaterial() {
		return materialDefinition.getMaterial();
	}

	public MaterialDefinition getMaterialDefinition() {
		return materialDefinition;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (stack.getItem().isDamageable())
			stack.damageItem(1, attacker);
		return true;
	}

	@Override
	public boolean isDamageable() {
		return !unbreakable;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (hand == EnumHand.MAIN_HAND) {
			// import, otherwise references will cause chaos!
			ItemStack throwStack = playerIn.getHeldItemMainhand().copy();
			if (playerIn.isCreative() || Helpers.consumeInventoryItem(playerIn, this)) {
				Helpers.playSound(worldIn, playerIn, "boomerang_throw", 0.5F, 1.0);
				if (!worldIn.isRemote) {
					EntityBoomerang boomerang = new EntityBoomerang(worldIn, playerIn, throwStack);
					worldIn.spawnEntity(boomerang);
				}
			}
			return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItemMainhand());
		}
		return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItemOffhand());
	}

}