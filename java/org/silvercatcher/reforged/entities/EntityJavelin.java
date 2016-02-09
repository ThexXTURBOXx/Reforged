package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.items.weapons.ItemJavelin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityJavelin extends AReforgedThrowable {


	public EntityJavelin(World worldIn, EntityLivingBase throwerIn, ItemStack stack, int durationLoaded) {
		
		super(worldIn, throwerIn, stack);
		
		setItemStack(stack);
		setDurLoaded(durationLoaded);
		
		if(durationLoaded < 20) {
			durationLoaded = 20;
		} else if(durationLoaded > 40) {
			durationLoaded = 40;
		}
		this.motionX *= (durationLoaded / 20);
		this.motionY *= (durationLoaded / 20);
		this.motionZ *= (durationLoaded / 20);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		
		// id 6 = ItemStack of Javelin, type 5 = ItemStack
		dataWatcher.addObjectByDataType(6, 5);
		
		// id 7 = Loaded Duration, type 2 = Integer
		dataWatcher.addObjectByDataType(7, 2);
	}
	
	public ItemStack getItemStack() {
		return dataWatcher.getWatchableObjectItemStack(6);
	}
	
	public void setItemStack(ItemStack stack) {
		
		if(stack == null || !(stack.getItem() instanceof ItemJavelin)) {
			throw new IllegalArgumentException("Invalid Itemstack!");
		}
		dataWatcher.updateObject(6, stack);
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
			target.entityHit.attackEntityFrom(ReforgedRegistry.javelinDamage, getImpactDamage(target.entityHit));
			ItemStack stack = getItemStack();
			if(stack.attemptDamageItem(1, rand)) {
				
			} else {
				setItemStack(stack);
			}
		}
		setDead();
		if(getItemStack().getMaxDamage() - getItemStack().getItemDamage() > 0) {
			if(!worldObj.isRemote) {
				entityDropItem(getItemStack(), 0.5f);
			}
		} else {
			//Custom sound later... [BREAK SOUND]
		}
	}
	
	public int getDurLoaded() {
		return dataWatcher.getWatchableObjectInt(7);
	}
	
	public void setDurLoaded(int durloaded) {
		
		dataWatcher.updateObject(7, durloaded);
	}
	
	@Override
	protected float getGravityVelocity() {
		return 0.03F;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		
		super.writeEntityToNBT(tagCompound);
		
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
	}

	@Override
	protected float getImpactDamage(Entity target) {

		return 5 + getDurLoaded() / 10;
	}
}
