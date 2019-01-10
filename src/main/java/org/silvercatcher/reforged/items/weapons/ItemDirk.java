package org.silvercatcher.reforged.items.weapons;

import com.google.common.collect.Multimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.IZombieEquippable;
import org.silvercatcher.reforged.api.ItemExtension;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;

public class ItemDirk extends ItemSword implements ItemExtension, IZombieEquippable {

	protected final MaterialDefinition materialDefinition;
	protected final boolean unbreakable;

	public ItemDirk(IItemTier material) {
		this(material, false);
	}

	public ItemDirk(IItemTier material, boolean unbreakable) {
		super(material);

		setCreativeTab(ReforgedMod.tabReforged);

		this.unbreakable = unbreakable;
		materialDefinition = MaterialManager.getMaterialDefinition(material);

		setUnlocalizedName(materialDefinition.getPrefixedName("dirk"));
		setMaxDamage(materialDefinition.getMaxUses());
		setMaxStackSize(1);
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

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (attacker.isSneaking()) {
			target.attackEntityFrom(getDamage(attacker), 2f);
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