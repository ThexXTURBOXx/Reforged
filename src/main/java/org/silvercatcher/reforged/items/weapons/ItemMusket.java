package org.silvercatcher.reforged.items.weapons;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.silvercatcher.reforged.api.AReloadable;
import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.entities.EntityBulletMusket;

public class ItemMusket extends AReloadable {

	public ItemMusket() {
		super("musket", "musket_shoot");
	}

	@Override
	public float getHitDamage() {
		return 2f;
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {

		return (repair.getItem() == Items.IRON_INGOT);
	}

	@Override
	public int getItemEnchantability() {

		return ItemTier.IRON.getEnchantability();
	}

	@Override
	public int getItemEnchantability(ItemStack stack) {
		return ItemTier.IRON.getEnchantability();
	}

	@Override
	public int getReloadTotal() {
		return 45;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (stack.getItem().isDamageable())
			stack.damageItem(1, attacker);
		return true;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (hand == EnumHand.MAIN_HAND) {
			setAmmo(ReforgedAdditions.MUSKET_BULLET);
		}
		return super.onItemRightClick(worldIn, playerIn, hand);
	}

	@Override
	public void shoot(World worldIn, EntityLivingBase playerIn, ItemStack stack) {
		worldIn.spawnEntity(new EntityBulletMusket(worldIn, playerIn, stack));
	}
}