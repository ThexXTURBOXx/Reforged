package org.silvercatcher.reforged.entities;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBulletMusket extends EntityThrowable {


	public EntityBulletMusket(World worldIn) {
		super(worldIn);
	}
	
	public EntityBulletMusket(World worldIn, EntityLivingBase throwerIn, ItemStack stack) {
		
		super(worldIn, throwerIn);
	}

	@Override
	protected void onImpact(MovingObjectPosition target) {
		//Target is entity or block?
		if(target.entityHit == null) {
			//It's a block
		} else {
			//It's an entity
			target.entityHit.attackEntityFrom(DamageSource.causeThornsDamage(getThrower()), 4);
		}
		setDead();
	}
	
	@Override
	protected float getGravityVelocity() {
		return 0.005f;
	}
}
