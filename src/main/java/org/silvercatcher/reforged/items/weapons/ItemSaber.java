package org.silvercatcher.reforged.items.weapons;

import com.google.common.collect.Multimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.IZombieEquippable;
import org.silvercatcher.reforged.api.ItemExtension;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;

public class ItemSaber extends ItemSword implements ItemExtension, IZombieEquippable {

	protected final MaterialDefinition materialDefinition;
	protected final boolean unbreakable;

	public ItemSaber(IItemTier material) {
		this(material, false);
	}

	public ItemSaber(IItemTier material, boolean unbreakable) {
		super(material, (int) (material.getAttackDamage() + 3.5f), -2.4F,
				new Item.Properties().group(ReforgedMod.tabReforged).defaultMaxDamage(material.getMaxUses()));
		this.unbreakable = unbreakable;
		materialDefinition = MaterialManager.getMaterialDefinition(material);
		setRegistryName(new ResourceLocation(ReforgedMod.ID, materialDefinition.getPrefixedName("saber")));
	}

	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		return ItemExtension.super.getAttributeModifiers(stack);
	}

	@Override
	public float getHitDamage() {

		return materialDefinition.getDamageVsEntity() + 3.5f;
	}

	@Override
	public int getItemEnchantability(ItemStack stack) {
		return materialDefinition.getEnchantability();
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {

		if (attacker.getRidingEntity() != null) {
			float damage = getHitDamage() / 2;
			target.attackEntityFrom(getDamage(attacker), damage);
		}
		if (stack.getItem().isDamageable())
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
