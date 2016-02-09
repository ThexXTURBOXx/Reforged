package org.silvercatcher.reforged.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public abstract class AReforgedThrowable extends EntityThrowable {
	
	private final String damageName;
	
	public AReforgedThrowable(World worldIn, String damageName) {
		
		super(worldIn);
		this.damageName = damageName;
	}
	
	public AReforgedThrowable(World worldIn, EntityLivingBase throwerIn, ItemStack stack, String damageName) {
		
		super(worldIn, throwerIn);
		this.damageName = damageName;
	}


	@Override
	protected void entityInit() {
		super.entityInit();
	}

	@Override
	protected void onImpact(MovingObjectPosition target) {
		
		boolean broken;
		
		if(target.entityHit == null) {
			
			broken = onBlockHit(target.getBlockPos());
			
		} else {
			
			broken = onEntityHit(target.entityHit instanceof EntityLivingBase
					? (EntityLivingBase) target.entityHit : target.entityHit); 
		}
		
		if(broken) {
			setDead();
		}
	}
	
	protected boolean onBlockHit(BlockPos blockPos) {
		return true;
	}

	protected boolean onEntityHit(Entity entity) {
		return true;
	}
	
	protected boolean onEntityHit(EntityLivingBase living) {
		return onEntityHit((Entity) living);
	}
		
	protected DamageSource causeImpactDamage(Entity target, EntityLivingBase shooter) {
		return new EntityDamageSourceIndirect(damageName, target, shooter).setProjectile();
	}
	
	protected abstract float getImpactDamage(Entity target);
	
	@Override
	protected float getGravityVelocity() {
		return 0.005f;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		
		super.writeEntityToNBT(tagCompound);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		
		super.readEntityFromNBT(tagCompund);
	}
}
