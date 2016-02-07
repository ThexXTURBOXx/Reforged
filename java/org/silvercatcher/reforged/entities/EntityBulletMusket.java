package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.ReforgedRegistry;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBulletMusket extends EntityThrowable {


	public EntityBulletMusket(World worldIn) {
		super(worldIn);
	}
	
	public EntityBulletMusket(World worldIn, EntityLivingBase throwerIn, ItemStack stack) {
		
		super(worldIn, throwerIn);
		setPositionAndRotation(throwerIn.posX, throwerIn.posY + throwerIn.getEyeHeight(), throwerIn.posZ, throwerIn.rotationYaw, throwerIn.rotationPitch);
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
		//Target is entity or block?
		if(target.entityHit == null) {
			//It's a block
		} else {
			//It's an entity
			target.entityHit.attackEntityFrom(ReforgedRegistry.musketDamage, 10);
		}
		if(target.entityHit instanceof EntityLivingBase) {
			EntityLivingBase entityHit = (EntityLivingBase) target.entityHit;
			if(entityHit instanceof EntityPigZombie) {
				EntityPigZombie en = (EntityPigZombie) entityHit;
				en.setRevengeTarget(getThrowerASave());
			}
		}
		setDead();
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
