package org.silvercatcher.reforged.entities;

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
	protected boolean onEntityHit(Entity entity) {
		System.out.println("Hit...");
		entity.attackEntityFrom(causeImpactDamage(entity, getThrower()), 4);
		return true;
	}

	@Override
	protected float getImpactDamage(Entity target) {
		return 5f;
	}
}
