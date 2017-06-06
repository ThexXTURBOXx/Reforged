package org.silvercatcher.reforged.enchantments;

import org.silvercatcher.reforged.ReforgedReferences.GlobalValues;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.LanguageRegistry;

public class EnchantmentGoalseeker extends Enchantment {

	public EnchantmentGoalseeker() {
		super(GlobalValues.GOALSEEKERID, new ResourceLocation("goalseeker"), 1, EnumEnchantmentType.BOW);
	}

	@Override
	public boolean canApply(ItemStack stack) {
		return false;
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return 1;
	}

	@Override
	public String getName() {
		return LanguageRegistry.instance().getStringLocalization("enchantment.goalseeker");
	}

}