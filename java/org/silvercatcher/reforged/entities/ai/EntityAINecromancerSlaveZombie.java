package org.silvercatcher.reforged.entities.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAINecromancerSlaveZombie
	extends EntityAINecromancerSlave {

	public EntityAINecromancerSlaveZombie(EntityPlayer master, EntityCreature slave) {
		super(master, slave, 25);
	}
}
