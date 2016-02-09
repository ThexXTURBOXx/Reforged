package org.silvercatcher.reforged.entities;

import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ReforgedThrowable extends EntityThrowable {
	
	public ReforgedThrowable(World worldIn) {
		super(worldIn);
	}
	
	public ReforgedThrowable(World worldIn, EntityLivingBase throwerIn, ItemStack stack) {
		
		super(worldIn, throwerIn);
		setThrowerUUID(throwerIn.getUniqueID());
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		// id 5 = UUID of Thrower, type 4 = String
		dataWatcher.addObjectByDataType(5, 4);
	}
	
	public EntityLivingBase getThrowerASave() {
		if(worldObj != null) {
			return worldObj.getPlayerEntityByUUID(UUID.fromString(dataWatcher.getWatchableObjectString(5)));
		}
		return getThrower();
	}
	
	public void setThrowerUUID(UUID uuid) {
		
		dataWatcher.updateObject(5, uuid.toString());
	}

	@Override
	protected void onImpact(MovingObjectPosition target) {
		if(target.entityHit != null && target.entityHit instanceof EntityLivingBase) {
			EntityLivingBase entityHit = (EntityLivingBase) target.entityHit;
			if(entityHit instanceof EntityPigZombie) {
				EntityPigZombie en = (EntityPigZombie) entityHit;
				en.setRevengeTarget(getThrowerASave());
			}
			if(entityHit instanceof EntityLivingBase) {
				EntityLivingBase en = (EntityLivingBase) entityHit;
				if(getThrowerASave() instanceof EntityPlayer) {
					en.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) getThrowerASave()), 0F);
				} else {
					en.attackEntityFrom(DamageSource.causeMobDamage(getThrowerASave()), 0F);
				}
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
		tagCompound.setString("throwerUUID", getThrowerASave().getUniqueID().toString());
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		
		super.readEntityFromNBT(tagCompund);
		setThrowerUUID(UUID.fromString(tagCompund.getString("throwerUUID")));
	}
}
