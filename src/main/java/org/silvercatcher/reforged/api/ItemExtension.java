package org.silvercatcher.reforged.api;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.Map.Entry;
import java.util.UUID;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

/**
 * Attempt to use Java 8 features against lack of foresight.
 */
public interface ItemExtension {

    UUID ATTACK_DAMAGE_MODIFIER_RF = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    UUID ATTACK_SPEED_MODIFIER_RF = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");

    int USE_DURATON = 72000;

    default double getAttackSpeed(ItemStack stack) {
        return -2.4000000953674316D;
    }

    default Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack) {

        Multimap<String, AttributeModifier> modifiers = HashMultimap.create();

        modifiers.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
                new AttributeModifier(ATTACK_DAMAGE_MODIFIER_RF, "Weapon Damage", getHitDamage(stack), 0));
        modifiers.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
                new AttributeModifier(ATTACK_SPEED_MODIFIER_RF, "Weapon modifier", getAttackSpeed(stack), 0));
        return modifiers;
    }

    default DamageSource getDamage(EntityLivingBase p) {
        if (p instanceof EntityPlayer)
            return DamageSource.causePlayerDamage((EntityPlayer) p);
        return DamageSource.causeMobDamage(p);
    }

    default float getEnchantmentBonus(ItemStack stack, Entity entity) {

        float extraDamage = 0f;

        if (entity instanceof EntityLivingBase) {

            EntityLivingBase living = (EntityLivingBase) entity;

            for (Entry<Enchantment, Integer> o : EnchantmentHelper.getEnchantments(stack).entrySet()) {

                Enchantment e = o.getKey();

                if (e instanceof EnchantmentDamage) {

                    EnchantmentDamage ed = (EnchantmentDamage) e;

                    if (ed.damageType != 0) {

                        extraDamage += e.calcDamageByCreature(EnchantmentHelper.getEnchantmentLevel(e, stack),
                                living.getCreatureAttribute());
                    }
                }
            }
        }
        return extraDamage;
    }

    float getHitDamage();

    default float getHitDamage(ItemStack stack) {
        float enchantDamage = 0f;
        for (Entry<Enchantment, Integer> o : EnchantmentHelper.getEnchantments(stack).entrySet()) {
            Enchantment e = o.getKey();
            if (e instanceof EnchantmentDamage) {
                EnchantmentDamage ed = (EnchantmentDamage) e;
                if (ed.damageType == 0) {
                    enchantDamage += ed.calcDamageByCreature(o.getValue(), null);
                }
            }
        }
        return getHitDamage() + enchantDamage;
    }
}
