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
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		// id 5 = UUID of Thrower, type 4 = String
		//dataWatcher.addObjectByDataType(5, 4);
	}

	@Override
	protected void onImpact(MovingObjectPosition target) {
		if(target.entityHit != null && target.entityHit instanceof EntityLivingBase) {
			EntityLivingBase entityHit = (EntityLivingBase) target.entityHit;
			if(entityHit instanceof EntityPigZombie) {
				EntityPigZombie en = (EntityPigZombie) entityHit;
				en.setRevengeTarget(getThrower());
			}
			if(entityHit instanceof EntityLivingBase) {
				EntityLivingBase en = (EntityLivingBase) entityHit;
				if(getThrower() instanceof EntityPlayer) {
					en.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) getThrower()), 0F);
				} else {
					en.attackEntityFrom(DamageSource.causeMobDamage(getThrower()), 0F);
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
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		
		super.readEntityFromNBT(tagCompund);
	}
}
