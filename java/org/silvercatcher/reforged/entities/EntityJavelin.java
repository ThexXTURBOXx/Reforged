package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.ReforgedReferences.GlobalValues;
import org.silvercatcher.reforged.items.weapons.ItemJavelin;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityJavelin extends EntityThrowable {

	public EntityJavelin(World worldIn) {
		super(worldIn);
	}
	
	public EntityJavelin(World worldIn, EntityLivingBase throwerIn, ItemStack stack, int durationLoaded) {
		super(worldIn, throwerIn);
		setItemStack(stack);
		setThrowerName(throwerIn.getName());
		setDurLoaded(durationLoaded);
		if(durationLoaded < 20) {
			durationLoaded = 20;
		} else if(durationLoaded > 40) {
			durationLoaded = 40;
		}
		this.motionX *= (durationLoaded / 20);
		this.motionY *= (durationLoaded / 20);
		this.motionZ *= (durationLoaded / 20);
		this.setPositionAndRotation(throwerIn.posX, throwerIn.posY + throwerIn.getEyeHeight(),
				throwerIn.posZ, throwerIn.rotationYaw, throwerIn.rotationPitch);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		
		// id 5 = ItemStack of Javelin, type 5 = ItemStack
		dataWatcher.addObjectByDataType(5, 5);
		
		// id 6 = Name of Thrower, type 4 = String
		dataWatcher.addObjectByDataType(6, 4);
		
		// id 7 = Loaded Duration, type 2 = Integer
		dataWatcher.addObjectByDataType(7, 2);
	}
	
	public ItemStack getItemStack() {
		return dataWatcher.getWatchableObjectItemStack(5);
	}
	
	public void setItemStack(ItemStack stack) {
		
		if(stack == null || !(stack.getItem() instanceof ItemJavelin)) {
			throw new IllegalArgumentException("Invalid Itemstack!");
		}
		dataWatcher.updateObject(5, stack);
	}

	@Override
	protected void onImpact(MovingObjectPosition target) {

		//Target is entity or block?
		if(target.entityHit == null) {
			//It's a block
			ItemStack stack = getItemStack();
			if(stack.attemptDamageItem(1, rand)) {
			} else {
				setItemStack(stack);
			}
		} else {
			//It's an entity
			target.entityHit.attackEntityFrom(ReforgedRegistry.javelinDamage, 5 + getDurLoaded() / 10);
			ItemStack stack = getItemStack();
			if(stack.attemptDamageItem(1, rand)) {
				
			} else {
				setItemStack(stack);
			}
		}
		this.setDead();
		if(getItemStack().getMaxDamage() - getItemStack().getItemDamage() > 0) {
			if(!worldObj.isRemote) {
				entityDropItem(getItemStack(), 0.5f);
			}
		} else {
			//Custom sound later... [BREAK SOUND]
		}
	}
	
	public EntityLivingBase getThrowerASave() {
		return getEntityWorld().getPlayerEntityByName(getThrowerName());
	}
	
	public String getThrowerName() {
		return dataWatcher.getWatchableObjectString(6);
	}
	
	public void setThrowerName(String name) {
		
		dataWatcher.updateObject(6, name);
	}
	
	public int getDurLoaded() {
		return dataWatcher.getWatchableObjectInt(7);
	}
	
	public void setDurLoaded(int durloaded) {
		
		dataWatcher.updateObject(7, durloaded);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		
		super.writeEntityToNBT(tagCompound);
		
		tagCompound.setString("thrower", getThrower().getName());
		tagCompound.setInteger("durloaded", getDurLoaded());
		
		if(getItemStack() != null) {
			tagCompound.setTag("item", getItemStack().writeToNBT(new NBTTagCompound()));
		}
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		
		super.readEntityFromNBT(tagCompund);
		setDurLoaded(tagCompund.getInteger("durloaded"));
		setItemStack(ItemStack.loadItemStackFromNBT(tagCompund.getCompoundTag("item")));
		setThrowerName(tagCompund.getString("thrower"));
	}
}
