package org.silvercatcher.reforged.items;

import org.silvercatcher.reforged.ReforgedMod;

import com.google.common.collect.Multimap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class ReforgedItem extends Item {

	protected final String name;
	
	public ReforgedItem(String name) {

		super();
		this.name = name;
		setCreativeTab(ReforgedMod.tabReforged);
		setUnlocalizedName(name);
		mapEnchantments();
	}

		
	public final String getName() { return name; }
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		
		playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
		return itemStackIn;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		
		stack.attemptDamageItem(1, itemRand);
		return false;
	}
	
	protected void mapEnchantments() {}

	public abstract void registerRecipes();
	
	public abstract float getHitDamage();
	
	/**
	 * make sure we get no NPEs by using this method!
	 * if anyone knows a less stupid way, tell me!
	 * 
	 * @param stack
	 * @return
	 */
	public NBTTagCompound giveCompound(ItemStack stack) {
		
		NBTTagCompound compound = stack.getTagCompound();
		if(compound == null) {
			compound = new NBTTagCompound();
			stack.setTagCompound(compound);
		}
		return compound;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		
		Multimap modifiers = super.getAttributeModifiers(stack);

		modifiers.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(itemModifierUUID, "Weapon Modifier", getHitDamage(), 0));
		return modifiers;
	}
}
