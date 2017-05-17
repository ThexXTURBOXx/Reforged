package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.api.AReforgedThrowable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityBulletMusket extends AReforgedThrowable {
	
	public EntityBulletMusket(World worldIn) {
		super(worldIn, "musket");
		setNoGravity(true);
	}
	
	public EntityBulletMusket(World worldIn, EntityLivingBase throwerIn, ItemStack stack) {
		super(worldIn, throwerIn, stack, "musket");
		setNoGravity(true);
		motionX *= 5;
		motionY *= 5;
		motionZ *= 5;
		setInited();
	}
	
	@Override
	protected boolean onEntityHit(Entity entity) {
		entity.attackEntityFrom(causeImpactDamage(entity, getThrower()), getImpactDamage(entity));
		return true;
	}
	
	@Override
	protected float getImpactDamage(Entity target) {
		return 10f;
	}
	
}