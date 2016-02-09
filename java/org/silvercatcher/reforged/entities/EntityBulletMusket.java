package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.ReforgedRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBulletMusket extends AReforgedThrowable {


	public EntityBulletMusket(World worldIn) {
		super(worldIn, "bullet_musket");
	}
	
	public EntityBulletMusket(World worldIn, EntityLivingBase throwerIn, ItemStack stack) {
		
		super(worldIn, throwerIn, stack);
	}

	@Override
	protected void onImpact(MovingObjectPosition target) {
		super.onImpact(target);
		//Target is entity or block?
		if(target.entityHit == null) {
			//It's a block
		} else {
			//It's an entity
			target.entityHit.attackEntityFrom(ReforgedRegistry.musketDamage, 10);
		}
		setDead();
	}

	@Override
	protected DamageSource causeImpactDamage(Entity target, EntityLivingBase shooter) {
		
		return new EntityDamageSourceIndirect("bullet_musket", target, shooter);
	}

	@Override
	protected float getImpactDamage(Entity target) {

		return 4f;
	}
}
