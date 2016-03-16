package org.silvercatcher.reforged.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityDynamite extends AReforgedThrowable {
	
	public EntityDynamite(World worldIn) {
		
		super(worldIn, "dynamite");
	}
	
	public EntityDynamite(World worldIn, EntityLivingBase throwerIn, ItemStack stack) {
		
		super(worldIn, throwerIn, stack, "dynamite");
	}
	
	@Override
	protected boolean onBlockHit(BlockPos blockPos) {
		Explosion e = worldObj.createExplosion(this, posX, posY, posZ, 5, true);
		return true;
	}
	
	@Override
	protected boolean onEntityHit(Entity entity) {
		motionX = 0;
		motionY = -0.2;
		motionZ = 0;
		return false;
	}
	
	@Override
	protected float getImpactDamage(Entity target) {

		return 0f;
	}
	
	@Override
	protected float getGravityVelocity() {
		return 0.01F;
	}
}
