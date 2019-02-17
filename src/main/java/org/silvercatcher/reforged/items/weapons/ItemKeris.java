package org.silvercatcher.reforged.items.weapons;

import com.google.common.collect.Multimap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTier;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.ItemExtension;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;

public class ItemKeris extends ItemSword implements ItemExtension {

	protected final MaterialDefinition materialDefinition;

	public ItemKeris() {
		super(ItemTier.GOLD, 2 + (int) ItemTier.GOLD.getAttackDamage(), -2.4F,
				new Item.Properties().defaultMaxDamage(ItemTier.GOLD.getMaxUses()).group(ReforgedMod.tabReforged));
		materialDefinition = MaterialManager.getMaterialDefinition(ItemTier.GOLD);
		setRegistryName(new ResourceLocation(ReforgedMod.ID, "keris"));
	}

	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		return ItemExtension.super.getAttributeModifiers(stack);
	}

	@Override
	public float getAttackDamage() {
		return getHitDamage();
	}

	@Override
	public float getHitDamage() {
		return materialDefinition.getDamageVsEntity() + 2f;
	}

	@Override
	public int getItemEnchantability(ItemStack stack) {
		// Sunconure11 wanted high enchantability ^^
		return materialDefinition.getEnchantability() + 8;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (target.getHealth() <= getHitDamage()) {
			World w = target.getEntityWorld();
			if (!w.isRemote) {
				int amount = ((EntityPlayer) attacker).experienceLevel / 2;
				while (amount > 0) {
					int j = EntityXPOrb.getXPSplit(amount);
					amount -= j;
					w.spawnEntity(
							new EntityXPOrb(w, attacker.posX + 0.5D, attacker.posY + 0.5D, attacker.posZ + 0.5D, j));
				}
			}
		}
		if (stack.getItem().isDamageable())
			stack.damageItem(1, attacker);
		return true;
	}
}