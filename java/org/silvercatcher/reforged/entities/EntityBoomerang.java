package org.silvercatcher.reforged.entities;

import java.util.List;

import org.omg.IOP.TaggedComponent;
import org.silvercatcher.reforged.items.weapons.ItemBoomerang;

import net.minecraft.entity.DataWatcher.WatchableObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityBoomerang extends EntityThrowable {
	
	public EntityBoomerang(World worldIn) {
		
		super(worldIn);
	}
	
	
	public EntityBoomerang(World worldIn, EntityLivingBase getThrowerAfterSaveIn, ItemStack stack) {
		
		super(worldIn, getThrowerAfterSaveIn);
		setItemStack(stack);
	}
	
	@Override
	protected void entityInit() {
	
		
		super.entityInit();
		
		// id 5 = ItemStack of Boomerang, type 5 = ItemStack
		dataWatcher.addObjectByDataType(5, 5);

		// id 6 = Name of Thrower, type 4 = String
		dataWatcher.addObjectByDataType(6, 4);;
	}

	public ItemStack getItemStack() {
		
		return dataWatcher.getWatchableObjectItemStack(5);
	}
	
	public void setItemStack(ItemStack stack) {
		
		if(stack == null || !(stack.getItem() instanceof ItemBoomerang)) {
			throw new IllegalArgumentException("Invalid Itemstack!");
		}
		dataWatcher.updateObject(5, stack);
	}
	
	public String getThrowerName() {
		
		return dataWatcher.getWatchableObjectString(6);
	}
	
	public void setThrowerName(String name) {
		
		dataWatcher.updateObject(6, name);
	}
	
	private void printDatawatcher() {
		
		System.out.println("##########");
		for(Object o : dataWatcher.getAllWatched()) {
			WatchableObject wo = (WatchableObject) o;
			System.out.println(wo.getDataValueId() + ": (" + wo.getObjectType() + ") " + wo.getObject());
		}
		
		System.out.println("++++++++++++++++");
	}
	
	public ToolMaterial getMaterial() {

		return ((ItemBoomerang) getItemStack().getItem()).getMaterial();
	}

	private float getImpactDamage() {
		
		return getMaterial().getDamageVsEntity()  + 3;
	}
	
	private static final double returnStrength = 0.05D;
	
	@Override
	public void onUpdate() {
		if(!getEntityWorld().isRemote) {
			super.onUpdate();
			double dx = this.posX - this.getThrower().posX;
			double dy = this.posY - this.getThrower().posY - this.getThrower().getEyeHeight();
			double dz = this.posZ - this.getThrower().posZ;
			
			double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
			dx /= d;
			dy /= d;
			dz /= d;
			
			motionX -= returnStrength * dx;
			motionY -= returnStrength * dy;
			motionZ -= returnStrength * dz;
		}
	}
	
	@Override
	protected float getGravityVelocity() {
		return 0.0F;
	}

	@Override
	protected void onImpact(MovingObjectPosition target) {
			
		//Target is entity or block?
		if(target.entityHit == null) {
			//It's a block
			if(!worldObj.isRemote) {
				entityDropItem(getItemStack(), 0.5f);
			}
			setDead();
		} else {
			//It's an entity
			if(target.entityHit != this.getThrower()) {
				//It's an hit entity
				target.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(
						target.entityHit, this.getThrower()), getImpactDamage());
				ItemStack stack = getItemStack();
				if(stack.attemptDamageItem(1, rand)) {
					this.setDead();
				} else {
					setItemStack(stack);
				}
			} else {
				//It's the thrower himself
				this.setDead();
				ItemStack stack = getItemStack();
				entityDropItem(stack, 0.0F);
			}
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		
		super.writeEntityToNBT(tagCompound);
		
		tagCompound.setTag("thrower", getThrower().getEntityData());
		
		if(getItemStack() != null) {
			tagCompound.setTag("item", getItemStack().writeToNBT(new NBTTagCompound()));
		}
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		
		super.readEntityFromNBT(tagCompund);
		
		setItemStack(ItemStack.loadItemStackFromNBT(tagCompund.getCompoundTag("item")));
	}
}
