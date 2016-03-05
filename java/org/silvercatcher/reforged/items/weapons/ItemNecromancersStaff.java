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
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;

public class ItemNecromancersStaff extends ExtendedItem {

	// very specific compound tag for keeping track of slaves, just keep it here
	private static final String SLAVE_TAG = "slaves";
	
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
			
			if(compound.hasKey(SLAVE_TAG)) {
				slaveIDs = compound.getIntArray(SLAVE_TAG);
			} else {
				slaveIDs = new int [4];
				compound.setIntArray(SLAVE_TAG, slaveIDs);
			}
			
			if(isSlave(slaveIDs, creature)) {
				// some reaction
			} else {
				enslave(slaveIDs, creature);
			}
			
			// safe changes
			compound.setIntArray(SLAVE_TAG, slaveIDs);
		}
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		
		playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
		
		NBTTagCompound compound = CompoundTags.giveCompound(itemStackIn);
		
		int [] slaveIDs = compound.getIntArray(SLAVE_TAG);
		
		System.out.println(Arrays.toString(slaveIDs));
		
		for(int i = 0; i < slaveIDs.length; i++) {
			
			int id = slaveIDs[i];
			if(id == 0) break;
			
			
			// only EntityCreatures are added, so cast should be safe
			EntityCreature slave = (EntityCreature) worldIn.getEntityByID(id);
			
			// throw invalid entities out
			if(slave == null || !slave.isEntityAlive()) {
				slaveIDs[i] = 0;
				System.out.println("Removed invalid Entity with ID " + id);
				break;
			}
			
			//System.out.println("Calling slave: " + slave);
			// call the slave to the holder of this staff
			PathNavigate navigate = slave.getNavigator();
			navigate.tryMoveToEntityLiving(playerIn, 1);
		}
		// save changes
		compound.setIntArray(SLAVE_TAG, slaveIDs);
		return itemStackIn;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		
		return 10;
	}
	
	private boolean isSlave(int [] slaveIDs, EntityCreature creature) {
		
		int creatureID = creature.getEntityId();
		return 0 < Arrays.binarySearch(slaveIDs, creatureID);
	}
	
	
	private void enslave(int [] slaveIDs, EntityCreature creature) {

		slaveIDs[0] = creature.getEntityId();
		Arrays.sort(slaveIDs);
	}
}
