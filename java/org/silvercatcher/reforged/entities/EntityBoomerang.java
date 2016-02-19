package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.items.CompoundTags;
import org.silvercatcher.reforged.items.weapons.ItemBoomerang;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityBoomerang extends AReforgedThrowable {
	
	public EntityBoomerang(World worldIn) {
		
		super(worldIn, "boomerang");
	}
	
	public EntityBoomerang(World worldIn, EntityLivingBase getThrowerIn, ItemStack stack) {
		
		super(worldIn, getThrowerIn, stack, "boomerang");
		setItemStack(stack);
		setCoords(getThrowerIn.posX, getThrowerIn.posY + getThrowerIn.getEyeHeight(), getThrowerIn.posZ);
	}
	
	@Override
	protected void entityInit() {
		
		super.entityInit();
		
		// id 5 = ItemStack of Boomerang, type 5 = ItemStack
		dataWatcher.addObjectByDataType(5, 5);
		
		// id 6 = posX, type 3 = float
		dataWatcher.addObjectByDataType(6, 3);
		
		// id 7 = posY, type 3 = float
		dataWatcher.addObjectByDataType(7, 3);
		
		// id 8 = posZ, type 3 = float
		dataWatcher.addObjectByDataType(8, 3);
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
	
	public void setCoords(double playerX, double playerY, double playerZ) {
		
		dataWatcher.updateObject(6, (float) playerX);
		dataWatcher.updateObject(7, (float) playerY);
		dataWatcher.updateObject(8, (float) playerZ);
	}
	
	public double getPosX() {
		return dataWatcher.getWatchableObjectFloat(6);
	}
	
	public double getPosY() {
		return dataWatcher.getWatchableObjectFloat(7);
	}
	
	public double getPosZ() {
		return dataWatcher.getWatchableObjectFloat(8);
	}
	
	public ToolMaterial getMaterial() {

		return ((ItemBoomerang) getItemStack().getItem()).getMaterial();
	}
	
	@Override
	public void onUpdate() {
		
		super.onUpdate();
		
		if(getThrower() != null) {
			if(CompoundTags.giveCompound(getItemStack()).getInteger(CompoundTags.ENCHANTED) == 1) {
				setCoords(getThrower().posX, getThrower().posY + getThrower().getEyeHeight(), getThrower().posZ);
			}
		}
		
		double dx = this.posX - getPosX();
		double dy = this.posY - getPosY();
		double dz = this.posZ - getPosZ();
		double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
		
		dx /= d;
		dy /= d;
		dz /= d;
		
		motionX -= 0.05D * dx;
		motionY -= 0.05D * dy;
		motionZ -= 0.05D * dz;
		
		//After 103 ticks, the Boomerang drops exactly where the thrower stood
		if((CompoundTags.giveCompound(getItemStack()).getInteger(CompoundTags.ENCHANTED) != 1 && ticksExisted >= 103) || isInWater()) {
			if(!worldObj.isRemote) {
				if(getItemStack().getMaxDamage() - getItemStack().getItemDamage() > 0) {
					entityDropItem(getItemStack(), 0.5f);
				} else {
					//Custom sound later... [BREAK SOUND]
				}
				setDead();
			}
		}
	}
	
	@Override
	protected float getGravityVelocity() {
		return 0f;
	}

	@Override
	protected boolean onBlockHit(BlockPos blockPos) {
		
		if(!worldObj.isRemote) {
			if(getItemStack().getMaxDamage() - getItemStack().getItemDamage() > 0) {
				entityDropItem(getItemStack(), 0.5f);
			}
		}
		return true;
	}
	
	@Override
	protected boolean onEntityHit(Entity hitEntity) {
		if(hitEntity == getThrower()) {
			//It's the thrower himself
			ItemStack stack = getItemStack();
			EntityPlayer p = (EntityPlayer) hitEntity;
			if(stack.getMaxDamage() - stack.getItemDamage() > 0) {
				p.inventory.addItemStackToInventory(stack);
			} else {
				//Custom sound later... [BREAK SOUND]
			}
			return true;
		} else {
			//It's an hit entity
			hitEntity.attackEntityFrom(causeImpactDamage(hitEntity, getThrower()), getImpactDamage(hitEntity));
			ItemStack stack = getItemStack();
			if(stack.attemptDamageItem(1, rand)) {
				//Custom sound later... [BREAK SOUND]
				return true;
			} else {
				setItemStack(stack);
			}
		}
		return false;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		
		super.writeEntityToNBT(tagCompound);
		
		tagCompound.setDouble("playerX", getPosX());
		tagCompound.setDouble("playerY", getPosY());
		tagCompound.setDouble("playerZ", getPosZ());
		
		if(getItemStack() != null) {
			tagCompound.setTag("item", getItemStack().writeToNBT(new NBTTagCompound()));
		}
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		
		super.readEntityFromNBT(tagCompund);
		setItemStack(ItemStack.loadItemStackFromNBT(tagCompund.getCompoundTag("item")));
		setCoords(tagCompund.getDouble("playerX"), tagCompund.getDouble("playerY"), tagCompund.getDouble("playerZ"));
	}

	@Override
	protected float getImpactDamage(Entity target) {
		return getMaterial().getDamageVsEntity() + 5;
	}
}
