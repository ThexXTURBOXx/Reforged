package org.silvercatcher.reforged.entities.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAINecromancerSlave extends EntityAIBase {

	protected final EntityPlayer master;
	protected final EntityCreature slave;
	protected final int updateInterval;
	
	public EntityAINecromancerSlave(EntityPlayer master, EntityCreature slave, int updateInterval) {

		setMutexBits(1);
		
		this.master = master;
		this.slave = slave;
		this.updateInterval = updateInterval;
	}

	protected int updatecountdown;
	protected EntityLivingBase attackTarget;
	
	@Override
	public boolean shouldExecute() {

		if(updatecountdown-- == 0) {
		
			updatecountdown = updateInterval;
			return true;
		}
		return false;
	}
	
	public void setAttackTarget(EntityLivingBase target) {
		
		this.attackTarget = target;
	}
}