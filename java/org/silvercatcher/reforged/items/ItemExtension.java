package org.silvercatcher.reforged.items;

import java.util.Map.Entry;
import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Attempt to use Java 8 features against lack of foresight.
 *
 */
public interface ItemExtension {

	public static final UUID itemModifierUUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
	
	public static final int USE_DURATON = 72000;
	
	@SuppressWarnings("rawtypes")
	default Multimap getAttributeModifiers(ItemStack stack) {
		
		Multimap modifiers =  HashMultimap.create();
		
		modifiers.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(),
				new AttributeModifier(itemModifierUUID, "Weapon Damage", getHitDamage(stack), 0));
		return modifiers;
	}
	
	default void registerRecipes() {}

	default float getHitDamage(ItemStack stack) {
		
		float enchantDamage = 0f;
		
		for(Object o :  EnchantmentHelper.getEnchantments(stack).entrySet()) {
			
			Entry <Integer, Integer> entry = (Entry<Integer, Integer>) o;
			
			Enchantment e = Enchantment.getEnchantmentByID(entry.getKey());
			
			if(e instanceof EnchantmentDamage) {
				
				EnchantmentDamage ed = (EnchantmentDamage) e;
				
				if(ed.damageType == 0) {
					
					enchantDamage += ed.calcDamageByCreature(entry.getValue(), null);
				}
			}
		}		
		return getHitDamage() + enchantDamage;
	}
	
	float getHitDamage();

	
	default float getEnchantmentBonus(ItemStack stack, EntityPlayer player, Entity entity) {
		
		float extraDamage = 0f;
		
		if(entity instanceof EntityLivingBase) {
			
			EntityLivingBase living = (EntityLivingBase) entity;
			
			for(Object o :  EnchantmentHelper.getEnchantments(stack).entrySet()) {
				
				Entry <Integer, Integer> entry = (Entry<Integer, Integer>) o;
				
				Enchantment e = Enchantment.getEnchantmentByID(entry.getKey());
				
				if(e instanceof EnchantmentDamage) {
					
					EnchantmentDamage ed = (EnchantmentDamage) e;
					
					if(ed.damageType != 0) {
						
						extraDamage += e.calcDamageByCreature(EnchantmentHelper.getEnchantmentLevel(
								e, stack), living.getCreatureAttribute());
					}
				}
			}
		}
		return extraDamage;
	}
}
