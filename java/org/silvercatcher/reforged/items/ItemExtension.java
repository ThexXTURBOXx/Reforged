package org.silvercatcher.reforged.items;

import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
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
		
		modifiers.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(itemModifierUUID, "Weapon Damage", getHitDamage(), 0));
		return modifiers;
	}
	
	default void registerRecipes() {}

	default float getHitDamage(ItemStack stack) {
		return 0f;
	}
}
