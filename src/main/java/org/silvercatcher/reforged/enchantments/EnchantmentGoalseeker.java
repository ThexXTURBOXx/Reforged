package org.silvercatcher.reforged.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentGoalseeker extends Enchantment {

    public EnchantmentGoalseeker() {
        super(Rarity.VERY_RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
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
        return "enchantment.goalseeker";
    }

}
