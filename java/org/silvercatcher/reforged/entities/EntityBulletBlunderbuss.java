package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.ReforgedRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBulletBlunderbuss extends EntityThrowable {


	public EntityBulletBlunderbuss(World worldIn) {
		super(worldIn);
	}
	
	public EntityBulletBlunderbuss(World worldIn, EntityLivingBase throwerIn, ItemStack stack) {
		
		super(worldIn, throwerIn);
		this.setPositionAndRotation(throwerIn.posX, throwerIn.posY + throwerIn.getEyeHeight(), throwerIn.posZ, throwerIn.rotationYaw, throwerIn.rotationPitch);
		float randomNumX = rand.nextInt(21);
		float randomNumY = rand.nextInt(21);
		float randomNumZ = rand.nextInt(21);
		if(rand.nextBoolean()) {
			randomNumX = 0 - randomNumX;
		}
		if(rand.nextBoolean()) {
			randomNumY = 0 - randomNumY;
		}
		if(rand.nextBoolean()) {
			randomNumZ = 0 - randomNumZ;
		}
		this.motionX += randomNumX / 100;
		this.motionY += randomNumY / 100;
		this.motionZ += randomNumZ / 100;
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
			target.entityHit.attackEntityFrom(ReforgedRegistry.blunderbussDamage, 10);
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
