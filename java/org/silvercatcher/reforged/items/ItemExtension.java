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
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;

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
		
		modifiers.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(itemModifierUUID, "Weapon Damage", getHitDamage(stack), 0));
		return modifiers;
	}
	
	default void registerRecipes() {}

	default float getHitDamage(ItemStack stack) {
		
		float enchantDamage = 0f;
		
		for(Object o :  EnchantmentHelper.getEnchantments(stack).entrySet()) {
			
			Entry <Integer, Integer> entry = (Entry<Integer, Integer>) o;
			
			Enchantment e = Enchantment.getEnchantmentById(entry.getKey());
			
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
				
				Enchantment e = Enchantment.getEnchantmentById(entry.getKey());
				
				if(e instanceof EnchantmentDamage) {
					
					EnchantmentDamage ed = (EnchantmentDamage) e;
					
					if(ed.damageType != 0) {
						
						extraDamage += e.calcDamageByCreature(EnchantmentHelper.getEnchantmentLevel(
								e.effectId, stack), living.getCreatureAttribute());
					}
				}
			}
		}
		return extraDamage;
	}

/*
	default void applyFireAspect(ItemStack stack, EntityPlayer player, Entity entity) {
		
		int fireAspect = EnchantmentHelper.getFireAspectModifier(player);
		if(fireAspect > 0) {
			entity.setFire(4 * fireAspect);
		}
	}
	
	/**
	 * @return the amount of damage added by the smite enchantment,
	 * 0 if enchantment does not exist
	 *
	default float applySmite(ItemStack stack, EntityLivingBase living) {
		
		int smite = EnchantmentHelper.getEnchantmentLevel(Enchantment.smite.effectId, stack);

		if(smite > 0 && living.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
			
			return smite * 2.5f;
		}
		return 0f;
	}
	
	default float applyBaneOfArthropods(ItemStack stack,EntityLivingBase living) {
		
		int baneOfArthropods = EnchantmentHelper.getEnchantmentLevel(
				Enchantment.baneOfArthropods.effectId, stack);
		
		if(baneOfArthropods > 0 && living.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) {
			
			living.addPotionEffect(new PotionEffect(PotionEffec;
			return baneOfArthropods * 2.5f;
		}
		return 0f;
	}*/
}
