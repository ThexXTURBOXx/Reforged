package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.api.AReforgedThrowable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityBulletMusket extends AReforgedThrowable {

	public EntityBulletMusket(World world) {

		super(world, "musket");
	}

	public EntityBulletMusket(World world, EntityLivingBase thrower, ItemStack stack) {

		super(world, thrower, stack, "musket");
		motionX *= 5;
		motionY *= 5;
		motionZ *= 5;
	}

	@Override
	protected float getGravityVelocity() {
		return 0f;
	}

	@Override
	protected float getImpactDamage(Entity target) {

		return 10f;
	}

	@Override
	protected boolean onEntityHit(Entity entity) {
		entity.attackEntityFrom(causeImpactDamage(entity, getThrower()), getImpactDamage(entity));
		return true;
	}
}
