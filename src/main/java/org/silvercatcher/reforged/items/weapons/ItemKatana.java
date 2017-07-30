package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.IZombieEquippable;
import org.silvercatcher.reforged.api.ItemExtension;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;

import com.google.common.collect.Multimap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemKatana extends ItemSword implements ItemExtension, IZombieEquippable {

	protected final MaterialDefinition materialDefinition;
	protected final boolean unbreakable;

	public ItemKatana(ToolMaterial material) {
		this(material, false);
	}

	public ItemKatana(ToolMaterial material, boolean unbreakable) {
		super(material);

		this.unbreakable = unbreakable;
		materialDefinition = MaterialManager.getMaterialDefinition(material);

		setUnlocalizedName(materialDefinition.getPrefixedName("katana"));
		setMaxDamage(materialDefinition.getMaxUses());
		setMaxStackSize(1);
		setCreativeTab(ReforgedMod.tabReforged);
	}

	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		return ItemExtension.super.getAttributeModifiers(stack);
	}

	@Override
	public float getHitDamage() {
		return materialDefinition.getDamageVsEntity() + 2f;
	}

	@Override
	public int getItemEnchantability(ItemStack stack) {
		return materialDefinition.getEnchantability();
	}

	public ToolMaterial getMaterial() {

		return materialDefinition.getMaterial();
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {

		int armorvalue = 0;

		for (int i = 3; i < 6; i++) {

			ItemStack armorStack = target.getItemStackFromSlot(EntityEquipmentSlot.values()[i]);
			if (armorStack != null && !armorStack.isEmpty() && armorStack.getItem() instanceof ItemArmor) {
				armorvalue += ((ItemArmor) armorStack.getItem()).damageReduceAmount;
			}
		}

		float damage = getHitDamage();

		if (armorvalue < 12) {

			damage *= 0.25f;
			target.hurtResistantTime = 0;
			target.attackEntityFrom(getDamage(attacker), damage);
		}

		if (armorvalue > 6) {
			if (stack.getItem().isDamageable())
				stack.damageItem(2, target);
		}

		if (stack.getCount() >= 1 && stack.getItem().isDamageable())
			stack.damageItem(1, attacker);

		return true;
	}

	@Override
	public boolean isDamageable() {
		return !unbreakable;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
		if (stack.getItem().isDamageable() && state.getBlockHardness(worldIn, pos) != 0.0D) {
			stack.damageItem(2, entityLiving);
		}
		return true;
	}

	@Override
	public float zombieSpawnChance() {
		switch (materialDefinition.getMaterial()) {
		case GOLD:
			return 1;
		case IRON:
			return 2;
		case STONE:
			return 3;
		case WOOD:
			return 4;
		default:
			return 0;
		}
	}
}
