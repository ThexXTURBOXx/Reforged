package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.api.ExtendedItem;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

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
	public float getHitDamage() {
		return materialDefinition.getDamageVsEntity() + 5f;
	}

	@Override
	public int getItemEnchantability(ItemStack stack) {
		return materialDefinition.getEnchantability();
	}

	public ToolMaterial getMaterial() {
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
		if (attacker.isRiding()) {
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

	@Override
	public void registerRecipes() {

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this), "  m", " s ", "s  ", 'm', materialDefinition.getOreDictRepairMaterial(),
				's', "stickWood"));
	}

}