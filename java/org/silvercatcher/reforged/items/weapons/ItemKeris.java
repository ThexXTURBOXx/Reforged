package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.ItemExtension;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;

import com.google.common.collect.Multimap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemKeris extends ItemSword implements ItemExtension {

	protected final MaterialDefinition materialDefinition;
	
	public ItemKeris() {
		
		super(ToolMaterial.GOLD);
		
		materialDefinition = MaterialManager.getMaterialDefinition(ToolMaterial.GOLD);
		
		setUnlocalizedName("keris");
		setMaxDamage(materialDefinition.getMaxUses());
		setMaxStackSize(1);
		setCreativeTab(ReforgedMod.tabReforged);
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if(entity instanceof EntityLivingBase) {
			EntityLivingBase e = (EntityLivingBase) entity;
			if(e.getHealth() <= getHitDamage() && !(e instanceof EntityPlayer)) {
				World w = e.getEntityWorld();
		        if(!w.isRemote) {
		        	int amount = player.experienceLevel / 2;
		            while (amount > 0) {
		                int j = EntityXPOrb.getXPSplit(amount);
		                amount -= j;
		                w.spawnEntity(new EntityXPOrb(w, e.posX + 0.5D, e.posY + 0.5D, e.posZ + 0.5D, j));
		            }
		        }
			}
		}
		return false;
	}
	
	@Override
	public void registerRecipes() {

		GameRegistry.addRecipe(new ItemStack(this),
				" m ",
				" m ",
				" s ",
				'm', Items.IRON_INGOT,
				's', Items.GOLD_INGOT);
	}
	
	@Override
	public float getHitDamage() {
		return materialDefinition.getDamageVsEntity() + 2f;
	}
	
	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		return ItemExtension.super.getAttributeModifiers(stack);
	}
	
	@Override
	public int getItemEnchantability(ItemStack stack) {
		//Sunconure11 wanted high enchantability ^^
		return materialDefinition.getEnchantability() + 8;
	}
}