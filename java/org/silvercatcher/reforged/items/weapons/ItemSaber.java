package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.ItemExtension;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;

import com.google.common.collect.Multimap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemSaber extends ItemSword implements ItemExtension {

	protected final MaterialDefinition materialDefinition;
	protected final boolean unbreakable;
	
	public ItemSaber(ToolMaterial material) {
		this(material, false);
	}
	
	public ItemSaber(ToolMaterial material, boolean unbreakable) {
		super(material);
		
		this.unbreakable = unbreakable;
		materialDefinition = MaterialManager.getMaterialDefinition(material);
		
		setUnlocalizedName(materialDefinition.getPrefixedName("saber"));
		
		setCreativeTab(ReforgedMod.tabReforged);
		setMaxStackSize(1);
		setMaxDamage(materialDefinition.getMaxUses());
	}
	
	@Override
	public boolean isDamageable() {
		if(unbreakable) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
			
		super.onLeftClickEntity(stack, player, entity);
		
		float damage = getHitDamage(stack) + getEnchantmentBonus(stack, player, entity);
				
		if(player.isRiding()) {
		
			damage += getHitDamage() / 2;
		}
				
		entity.attackEntityFrom(DamageSource.causePlayerDamage(player), damage);

		return true;
	}
	
	@Override
	public void registerRecipes() {
		
		GameRegistry.addRecipe(new ItemStack(this),
				" b ",
				"b  ",
				"s  ",
				'b', materialDefinition.getRepairMaterial(),
				's', Items.stick);
	}

	@Override
	public float getHitDamage() {
		
		return materialDefinition.getDamageVsEntity() + 3.5f;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		return ItemExtension.super.getAttributeModifiers(stack);
	}
	
	@Override
	public int getItemEnchantability(ItemStack stack) {
		return materialDefinition.getEnchantability();
	}
}
