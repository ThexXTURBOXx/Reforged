package org.silvercatcher.reforged.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.LanguageRegistry;

public class EnchantmentGoalseeker extends Enchantment {
	
	public EnchantmentGoalseeker(int enchID) {
		super(enchID, new ResourceLocation("goalseeker"), 1, EnumEnchantmentType.BOW);
	}
	
	@Override
	public String getName() {
		return LanguageRegistry.instance().getStringLocalization("enchantment.goalseeker");
	}
	
	@Override
	public boolean canApply(ItemStack stack) {
		return false;
	}
	
	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return 1;
	}
	
}