package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.ReforgedRegistry;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBulletMusket extends ReforgedThrowable {


	public EntityBulletMusket(World worldIn) {
		super(worldIn);
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
}
