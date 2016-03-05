package org.silvercatcher.reforged.items.weapons;

import java.util.Arrays;

import org.silvercatcher.reforged.entities.ai.EntityAIDefendNecromancer;
import org.silvercatcher.reforged.entities.ai.EntityAIFollowNecromancer;
import org.silvercatcher.reforged.items.CompoundTags;
import org.silvercatcher.reforged.items.ExtendedItem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIControlledByPlayer;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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
			
			EntityCreature creature = (EntityCreature) entity;
			
			NBTTagCompound compound = CompoundTags.giveCompound(stack);
			
			
			int [] slaveIDs;
			
			if(compound.hasKey("slaves")) {
				slaveIDs = compound.getIntArray("slaves");
			} else {
				slaveIDs = new int [4];
				compound.setIntArray("slaves", slaveIDs);
			}
			
			if(isSlave(slaveIDs, creature)) {
				
				// some reaction
			} else {
				
				enslave(slaveIDs, creature);
			}
		}
		return true;
	}

	private boolean isSlave(int [] slaveIDs, EntityCreature creature) {
		
		
		int creatureID = creature.getEntityId();
		return 0 < Arrays.binarySearch(slaveIDs, creatureID);
	}
	
	
	private void enslave(int [] slaveIDs, EntityCreature creature) {

		slaveIDs[0] = creature.getEntityId();
		Arrays.sort(slaveIDs);
		System.out.println(Arrays.toString(slaveIDs));
	}
}
