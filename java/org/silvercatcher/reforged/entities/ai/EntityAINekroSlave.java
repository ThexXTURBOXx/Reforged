package org.silvercatcher.reforged.entities.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAINekroSlave extends EntityAIBase {

	protected final EntityPlayer master;
	protected final EntityLiving slave;
	
	protected final double followMasterSpeed;
	protected final double attackLeapSpeed;
	
	protected final int updateInterval;
	
	protected int updateCountdown;
	protected EntityLivingBase target;
	
	public EntityAINekroSlave(EntityPlayer master, EntityLiving slave) {
		
		this(master, slave, 0.75, 0.8, 20);
	}
	
	
	public EntityAINekroSlave(EntityPlayer master, EntityLiving slave,
			double followMasterSpeed, double attackLeapSpeed, int updateInterval) {
		
		setMutexBits(7);
		
		this.master = master;
		this.slave = slave;
		
		this.followMasterSpeed = followMasterSpeed;
		this.attackLeapSpeed = attackLeapSpeed;
		
		this.updateInterval = updateInterval;
		
		slave.setAttackTarget(null);
		slave.setRevengeTarget(null);
	}

	
	@Override
	public boolean shouldExecute() {
		
		if(master.isEntityAlive() && slave.isEntityAlive()) {
			
			double distanceSq = master.getDistanceSqToEntity(slave);
						
			if(distanceSq < 80) {
								
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void startExecuting() {
		
		//if no fighting goes on, just follow master
		if(master.getLastAttacker() == null || master.getLastAttacker() == slave) {
			
			target = master;
			
		} else {
			
			target = master.getLastAttacker();
		}
	}
	
	
	@Override
	public void updateTask() {
	
		if(updateCountdown == 0) {
			
			slave.getNavigator().setPath(slave.getNavigator().getPathToEntityLiving(target),
					target == master ? followMasterSpeed : attackLeapSpeed);
			
			updateCountdown = updateInterval; 
		
		} else {
			
			updateCountdown--;
		}
		
	}
	
	@Override
	public void resetTask() {
		
		System.out.println("reset");
		slave.getNavigator().clearPathEntity();
		target = null;
	}
}
