package org.silvercatcher.reforged.props;

import java.util.*;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.network.datasync.*;

public class StunProperty {
	
	public static final DataParameter<Boolean> STUN = EntityDataManager.<Boolean>createKey(EntityZombieVillager.class, DataSerializers.BOOLEAN);
	public static final List<UUID> registered = new ArrayList<UUID>();
	
	public static final void register(EntityLivingBase player) {
		player.getDataManager().register(STUN, false);
		registered.add(player.getUniqueID());
	}
	
	public static boolean isRegistered(EntityLivingBase player) {
		return registered.contains(player.getUniqueID());
	}
	
	public static boolean isStunned(EntityLivingBase player) {
		return player.getDataManager().get(STUN);
	}
	
	public static void setStunned(EntityLivingBase player, boolean whether) {
		player.getDataManager().set(STUN, whether);
	}
	
}