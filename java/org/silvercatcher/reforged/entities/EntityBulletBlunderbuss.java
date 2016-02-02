package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.ReforgedRegistry;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBulletBlunderbuss extends EntityThrowable {


	public EntityBulletBlunderbuss(World worldIn) {
		super(worldIn);
	}
	
	public EntityBulletBlunderbuss(World worldIn, EntityLivingBase throwerIn, ItemStack stack) {
		
		super(worldIn, throwerIn);
		this.setPositionAndRotation(throwerIn.posX, throwerIn.posY + throwerIn.getEyeHeight(), throwerIn.posZ, throwerIn.rotationYaw, throwerIn.rotationPitch);
	}

	@Override
	protected void onImpact(MovingObjectPosition target) {
		//Target is entity or block?
		if(target.entityHit == null) {
			//It's a block
		} else {
			//It's an entity
			target.entityHit.attackEntityFrom(ReforgedRegistry.musketDamage, 4);
		}
		setDead();
	}
	
	@Override
	protected float getGravityVelocity() {
		return 0.005f;
	}
}
