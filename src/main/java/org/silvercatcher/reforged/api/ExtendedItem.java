package org.silvercatcher.reforged.api;

import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.silvercatcher.reforged.ReforgedMod;

/**
 * *ggrrrrr*
 * <p>
 * this class is just for less @override trouble...
 */
public abstract class ExtendedItem extends Item implements ItemExtension {

    public ExtendedItem() {
        setCreativeTab(ReforgedMod.tabReforged);
    }

    @Override
    public double getAttackSpeed(ItemStack stack) {
        return -3;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack) {
        return ItemExtension.super.getAttributeModifiers(stack);
    }

    @Override
    public float getHitDamage() {
        return 0f;
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND && isWeapon()) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(),
                    new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", getHitDamage(), 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(),
                    new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", getAttackSpeed(null), 0));
        }

        return multimap;
    }

    public boolean isWeapon() {
        return true;
    }

}
