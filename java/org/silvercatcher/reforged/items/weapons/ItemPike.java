package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.api.ExtendedItem;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemPike extends ExtendedItem {

	protected final MaterialDefinition materialDefinition;
	protected final boolean unbreakable;

	public ItemPike(ToolMaterial material) {
		this(material, false);
	}

	public ItemPike(ToolMaterial material, boolean unbreakable) {
		super();
		this.unbreakable = unbreakable;
		setMaxStackSize(1);
		materialDefinition = MaterialManager.getMaterialDefinition(material);
		setMaxDamage((int) (materialDefinition.getMaxUses() * 0.5f));
		setUnlocalizedName(materialDefinition.getPrefixedName("pike"));
	}

	@Override
	public boolean isDamageable() {
		if (unbreakable)
			return false;
		else
			return true;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		super.onLeftClickEntity(stack, player, entity);
		float damage = getHitDamage(stack) + getEnchantmentBonus(stack, player, entity);
		if (entity.isRiding()) {
			damage += getHitDamage() / 2;
		}
		entity.attackEntityFrom(DamageSource.causePlayerDamage(player), damage);
		return true;
	}

	@Override
	public void registerRecipes() {

		GameRegistry.addRecipe(new ItemStack(this), "  m", " s ", "s  ", 'm', materialDefinition.getRepairMaterial(),
				's', Items.stick);
	}

	@Override
	public float getHitDamage() {
		return materialDefinition.getDamageVsEntity() + 5f;
	}

	public ToolMaterial getMaterial() {
		return materialDefinition.getMaterial();
	}

	public MaterialDefinition getMaterialDefinition() {
		return materialDefinition;
	}

	@Override
	public int getItemEnchantability(ItemStack stack) {
		return materialDefinition.getEnchantability();
	}

}