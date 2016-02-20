package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.entities.ai.EntityAIDefendNecromancer;
import org.silvercatcher.reforged.entities.ai.EntityAIFollowNecromancer;
import org.silvercatcher.reforged.items.ExtendedItem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIControlledByPlayer;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemNecromancersStaff extends ExtendedItem {

	public ItemNecromancersStaff() {
		
		setUnlocalizedName("necromancers_staff");
	}
	
	//just for testing something...
	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		
		System.out.println(book);
		
		return true;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		
		if(entity instanceof EntityCreature) {
			
			EntityCreature living = (EntityCreature) entity;
			
			living.targetTasks.addTask(0, new EntityAIDefendNecromancer(player, living));
			living.targetTasks.addTask(1, new EntityAIFollowNecromancer(player, living));
		}
		return false;
	}
}
