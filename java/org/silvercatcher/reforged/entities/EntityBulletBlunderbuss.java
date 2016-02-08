package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.ReforgedRegistry;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBulletBlunderbuss extends ReforgedThrowable {
	
	public EntityBulletBlunderbuss(World worldIn) {
		super(worldIn);
	}
	
	public EntityBulletBlunderbuss(World worldIn, EntityLivingBase throwerIn, ItemStack stack) {
		
		super(worldIn, throwerIn, stack);
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
	protected void onImpact(MovingObjectPosition target) {
		super.onImpact(target);
		//Target is entity or block?
		if(target.entityHit == null) {
			//It's a block
		} else {
			//It's an entity
			target.entityHit.attackEntityFrom(ReforgedRegistry.blunderbussDamage, 10);
		}
		setDead();
	}
}
