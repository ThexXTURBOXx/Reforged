package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.api.AReforgedThrowable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityBulletBlunderbuss extends AReforgedThrowable {
	
	public EntityBulletBlunderbuss(World worldIn) {
		
		super(worldIn, "blunderbuss");
	}
	
	public EntityBulletBlunderbuss(World worldIn, EntityLivingBase throwerIn, ItemStack stack) {
		
		super(worldIn, throwerIn, stack, "blunderbuss");
		float randomNumX = rand.nextInt(21);
		float randomNumY = rand.nextInt(21);
		float randomNumZ = rand.nextInt(21);
		if(rand.nextBoolean()) {
			randomNumX = 0 - randomNumX;
		}
		if(rand.nextBoolean()) {
			randomNumY = 0 - randomNumY;
		}
		if(rand.nextBoolean()) {
			randomNumZ = 0 - randomNumZ;
		}
		this.motionX += randomNumX / 100;
		this.motionY += randomNumY / 100;
		this.motionZ += randomNumZ / 100;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(ticksExisted >= 30) {
			setDead();
		}
	}

	@Override
	protected boolean onEntityHit(Entity entity) {
		entity.attackEntityFrom(causeImpactDamage(entity, getThrower()), getImpactDamage(entity));
		return true;
	}

	@Override
	protected float getImpactDamage(Entity target) {
		return (((30 - ticksExisted) / 4) + 4f);
	}
}
