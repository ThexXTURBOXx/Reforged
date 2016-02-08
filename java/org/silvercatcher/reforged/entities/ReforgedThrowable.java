package org.silvercatcher.reforged.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ReforgedThrowable extends EntityThrowable {
	
	public ReforgedThrowable(World worldIn) {
		super(worldIn);
	}
	
	public ReforgedThrowable(World worldIn, EntityLivingBase throwerIn, ItemStack stack) {
		
		super(worldIn, throwerIn);
		this.setPositionAndRotation(throwerIn.posX, throwerIn.posY + throwerIn.getEyeHeight(), throwerIn.posZ, throwerIn.rotationYaw, throwerIn.rotationPitch);
		setThrowerName(throwerIn.getName());
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		// id 5 = Name of Thrower, type 4 = String
		dataWatcher.addObjectByDataType(5, 4);
	}
	
	public EntityLivingBase getThrowerASave() {
		return getEntityWorld().getPlayerEntityByName(dataWatcher.getWatchableObjectString(5));
	}
	
	public void setThrowerName(String name) {
		
		dataWatcher.updateObject(5, name);
	}

	@Override
	protected void onImpact(MovingObjectPosition target) {
		if(target.entityHit != null && target.entityHit instanceof EntityLivingBase) {
			EntityLivingBase entityHit = (EntityLivingBase) target.entityHit;
			if(entityHit instanceof EntityPigZombie) {
				EntityPigZombie en = (EntityPigZombie) entityHit;
				en.setRevengeTarget(getThrowerASave());
			}
		}
	}
	
	@Override
	protected float getGravityVelocity() {
		return 0.005f;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		
		super.writeEntityToNBT(tagCompound);
		tagCompound.setString("thrower", getThrowerASave().getName());
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		
		super.readEntityFromNBT(tagCompund);
		setThrowerName(tagCompund.getString("thrower"));
	}
}
