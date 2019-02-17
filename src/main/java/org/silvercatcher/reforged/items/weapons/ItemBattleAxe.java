package org.silvercatcher.reforged.items.weapons;

import com.google.common.collect.Multimap;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.IZombieEquippable;
import org.silvercatcher.reforged.api.ItemExtension;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;

public class ItemBattleAxe extends ItemAxe implements ItemExtension, IZombieEquippable {

	protected final MaterialDefinition materialDefinition;
	protected final boolean unbreakable;

	public ItemBattleAxe(IItemTier material) {
		this(material, false);
	}

	public ItemBattleAxe(IItemTier material, boolean unbreakable) {
		super(material, material.getAttackDamage() * 1.5f + 4f, -3.0F,
				new Item.Properties().defaultMaxDamage(material.getMaxUses())
						.group(ReforgedMod.tabReforged));

		this.unbreakable = unbreakable;
		materialDefinition = MaterialManager.getMaterialDefinition(material);
		setRegistryName(new ResourceLocation(ReforgedMod.ID, materialDefinition.getPrefixedName("battleaxe")));
	}

	protected boolean effectiveAgainst(IBlockState target) {

		Material material = target.getMaterial();
		return (material == Material.WOOD || material == Material.PLANTS || material == Material.VINE);
	}

	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		return ItemExtension.super.getAttributeModifiers(stack);
	}

	@Override
	public float getHitDamage() {

		return materialDefinition.getDamageVsEntity() * 1.5f + 4f;
	}

	@Override
	public int getItemEnchantability(ItemStack stack) {
		return materialDefinition.getEnchantability();
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		return effectiveAgainst(state) ? materialDefinition.getEfficiencyOnProperMaterial() + 0.5f : 1f;
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
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState blockIn, BlockPos pos,
									EntityLivingBase playerIn) {

		if (stack.getItem().isDamageable())
			stack.damageItem(effectiveAgainst(blockIn) ? 2 : 3, playerIn);
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
