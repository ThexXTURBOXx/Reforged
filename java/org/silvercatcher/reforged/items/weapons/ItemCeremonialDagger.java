package org.silvercatcher.reforged.items.weapons;

import java.util.Iterator;
import java.util.LinkedList;

import org.silvercatcher.reforged.items.ItemReforged;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemCeremonialDagger extends ItemReforged {

	private final static int DELAY = 50;
	
	private LinkedList<EntityLivingBase> toPunish;
	
	public ItemCeremonialDagger() {
		super("ceremonial_dagger");
	
		toPunish = new LinkedList<EntityLivingBase>();
	}

	@Override
	public void registerRecipes() {
		
		GameRegistry.addShapedRecipe(new ItemStack(this),
				" g ",
				" g ",
				"geg",
				'g', Items.gold_ingot,
				'e', Items.emerald);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		
		super.onLeftClickEntity(stack, player, entity);
		
		if(entity instanceof EntityLivingBase) {
			EntityLivingBase living = (EntityLivingBase) entity;
			
			if(living.isEntityUndead()) {
				
				toPunish.addLast(living);
			}
		}
		return false;
	}
	
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		
		if(!isSelected || toPunish.isEmpty() || itemRand.nextInt(DELAY) != 0) return;
		
		EntityLivingBase target = toPunish.removeFirst();

		worldIn.spawnEntityInWorld(new EntityLightningBolt(worldIn,
				target.posX, target.posY, target.posZ));
		
		if(!target.isDead) {
			toPunish.addLast(target);
		}
	}
	
	
	@Override
	public float getHitDamage() {
		return 3f;
	}
}
