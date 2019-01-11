package org.silvercatcher.reforged.items.weapons;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.silvercatcher.reforged.api.AReloadable;
import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.entities.EntityBulletBlunderbuss;

public class ItemBlunderbuss extends AReloadable {

	public ItemBlunderbuss() {
		super(new Item.Builder(), "blunderbuss", "shotgun_shoot");
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

		return 40;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		setAmmo(ReforgedAdditions.BLUNDERBUSS_SHOT);
		return super.onItemRightClick(worldIn, playerIn, hand);
	}

	@Override
	public void shoot(World worldIn, EntityLivingBase playerIn, ItemStack stack) {

		for (int i = 1; i < 12; i++) {
			worldIn.spawnEntity(new EntityBulletBlunderbuss(worldIn, playerIn, stack));
		}
	}
}