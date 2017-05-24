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
