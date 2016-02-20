package org.silvercatcher.reforged.entities.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAIFollowNecromancer extends EntityAIBase {

	protected final EntityPlayer master;
	protected final EntityLiving slave;
	
	protected final double followMasterSpeed;
	
	protected final int updateInterval;
	protected final int maxDistance;
	
	protected int updateCountdown;
	
	
	public EntityAIFollowNecromancer(EntityPlayer master, EntityLiving slave) {
		
		this(master, slave, 0.9, 20, 4);
	}
	
	
	public EntityAIFollowNecromancer(EntityPlayer master, EntityLiving slave,
			double followMasterSpeed, int updateInterval, int maxDistance) {
		
		/*
		 *  bits: 	0011
		 *  &		0001
		 * 			0001
		 * 
		 * should cancel out other AI, expect swimming
		*/
		setMutexBits(3);
		
		this.master = master;
		this.slave = slave;
		this.followMasterSpeed = followMasterSpeed;
		
		this.updateInterval = updateInterval;
		this.maxDistance = Math.max(0, maxDistance);

	}

	
	@Override
	public boolean shouldExecute() {
		
		if(master.isEntityAlive() && slave.isEntityAlive()) {
			
			double distance = slave.getDistanceToEntity(master);
						System.out.println(distance);
			if(distance > maxDistance) {
								
				return true;
			}
		}
		
		return false;
	}
	
	
	@Override
	public void updateTask() {
	
		if(--updateCountdown <= 0) {
			
			slave.getNavigator().tryMoveToEntityLiving(master, followMasterSpeed);
			updateCountdown = updateInterval; 
		}
		
	}
	
	@Override
	public void resetTask() {
		
		slave.getNavigator().clearPathEntity();
		updateCountdown = 0;
	}
}
