package org.silvercatcher.reforged.items.weapons;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.ExtendedItem;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;

public class ItemPike extends ExtendedItem {

	protected final MaterialDefinition materialDefinition;
	protected final boolean unbreakable;

	public ItemPike(IItemTier material) {
		this(material, false);
	}

	public ItemPike(IItemTier material, boolean unbreakable) {
		super(new Item.Properties().defaultMaxDamage((int) (material.getMaxUses() * 0.5f)));
		this.unbreakable = unbreakable;
		materialDefinition = MaterialManager.getMaterialDefinition(material);
		setRegistryName(new ResourceLocation(ReforgedMod.ID, materialDefinition.getPrefixedName("pike")));
	}

	@Override
	public float getHitDamage() {
		return materialDefinition.getDamageVsEntity() + 5f;
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
		float damage = getHitDamage();
		if (attacker instanceof EntityPlayer)
			damage = damage + getEnchantmentBonus(stack, (EntityPlayer) attacker, target);
		if (attacker.getRidingEntity() != null) {
			damage += getHitDamage() / 2;
		}
		target.attackEntityFrom(getDamage(attacker), damage);
		if (stack.getItem().isDamageable())
			stack.damageItem(1, attacker);
		return true;
	}

	@Override
	public boolean isDamageable() {
		return !unbreakable;
	}

}