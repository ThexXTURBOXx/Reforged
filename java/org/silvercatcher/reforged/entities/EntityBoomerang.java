package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.api.AReforgedThrowable;
import org.silvercatcher.reforged.api.CompoundTags;
import org.silvercatcher.reforged.items.weapons.ItemBoomerang;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.util.Helpers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityBoomerang extends AReforgedThrowable {

	public static final DataParameter<Double> THROWER_X = EntityDataManager.<Double>createKey(EntityBoomerang.class, DOUBLE);
	public static final DataParameter<Double> THROWER_Y = EntityDataManager.<Double>createKey(EntityBoomerang.class, DOUBLE);
	public static final DataParameter<Double> THROWER_Z = EntityDataManager.<Double>createKey(EntityBoomerang.class, DOUBLE);
	public static final DataParameter<ItemStack>  STACK = EntityDataManager.<ItemStack>createKey(EntityBoomerang.class, ITEMSTACK);
	
	public EntityBoomerang(World worldIn) {
		super(worldIn, "boomerang");
	}
	
	public EntityBoomerang(World worldIn, EntityLivingBase thrower, ItemStack stack) {
		super(worldIn, thrower, stack, "boomerang");
		setItemStack(stack);
		setCoords(thrower.posX, thrower.posY + thrower.getEyeHeight(), thrower.posZ);
		setInited();
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(STACK, ItemStack.EMPTY);
		dataManager.register(THROWER_X, 0D);
		dataManager.register(THROWER_Y, 0D);
		dataManager.register(THROWER_Z, 0D);
	}
	
	public ItemStack getItemStack() {
		return dataManager.get(STACK);
	}
	
	public void setItemStack(ItemStack stack) {
		if(stack == null || !(stack.getItem() instanceof ItemBoomerang)) {
			throw new IllegalArgumentException("Invalid Itemstack!");
		}
		dataManager.set(STACK, stack);
	}
	
	public void setCoords(double playerX, double playerY, double playerZ) {
		dataManager.set(THROWER_X, playerX);
		dataManager.set(THROWER_Y, playerY);
		dataManager.set(THROWER_Z, playerZ);
	}
	
	public double getPosX() {
		return dataManager.get(THROWER_X);
	}
	
	public double getPosY() {
		return dataManager.get(THROWER_Y);
	}
	
	public double getPosZ() {
		return dataManager.get(THROWER_Z);
	}
	
	public MaterialDefinition getMaterialDefinition() {
		MaterialDefinition md;
		try {
			md = ((ItemBoomerang) getItemStack().
					getItem()).
					getMaterialDefinition();
		} catch(NullPointerException e) {
			md = null;
		}
		return md;
	}
	
	@Override
	public void onUpdated() {
		if(getThrower() != null && CompoundTags.giveCompound(getItemStack()).getBoolean(CompoundTags.ENCHANTED)) {
			setCoords(getThrower().posX, getThrower().posY + getThrower().getEyeHeight(), getThrower().posZ);
		}
		
		double dx = this.posX - getPosX();
		double dy = this.posY - getPosY();
		double dz = this.posZ - getPosZ();
		
		double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
		
		dx /= d;
		dy /= d;
		dz /= d;
		
		if(world.isRemote) {
			setVelocity(motionX - (0.05D * dx), motionY - (0.05D * dy), motionZ - (0.05D * dz));
		} else {
			motionX -= (0.05D * dx);
			motionY -= (0.05D * dy);
			motionZ -= (0.05D * dz);
		}
		
		/*motionX -= (0.05D * dx);
		motionY -= (0.05D * dy);
		motionZ -= (0.05D * dz);*/
		
		if(isInWater()) {
			if(!world.isRemote) {
				if(getItemStack().getMaxDamage() - getItemStack().getItemDamage() > 0 && !creativeUse()) {
						entityDropItem(getItemStack(), 0.5f);
				} else {
					Helpers.playSound(world, this, "reforged:boomerang_break", 1.0F, 1.0F);
				}
				setDead();
			}
		}
		
		//After 103 ticks, the Boomerang drops exactly where the thrower stood
		if(ticksExisted >= 103) {
			if(CompoundTags.giveCompound(getItemStack()).getBoolean(CompoundTags.ENCHANTED)) {
				if(onEntityHit(getThrower())) setDead();
			} else {
				if(!world.isRemote) {
					if(getItemStack().getMaxDamage() - getItemStack().getItemDamage() > 0 && !creativeUse()) {
							entityDropItem(getItemStack(), 0.5f);
					} else {
						Helpers.playSound(world, this, "reforged:boomerang_break", 1.0F, 1.0F);
					}
					setDead();
				}
			}
		}
	}
	
	@Override
	protected float getGravityVelocity() {
		return 0f;
	}
	
	@Override
	protected boolean onBlockHit(BlockPos blockPos) {
		if(!world.isRemote) {
			if(getItemStack().getMaxDamage() - getItemStack().getItemDamage() > 0 && !creativeUse()) {
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
			if(stack.getMaxDamage() - stack.getItemDamage() > 0 && !creativeUse()) {
				p.inventory.addItemStackToInventory(stack);
				Helpers.playSound(world, this, "random.pop", 0.5F, 0.7F);
			} else {
				Helpers.playSound(world, this, "reforged:boomerang_break", 1.0F, 1.0F);
			}
			return true;
		} else {
			//It's an hit entity
			hitEntity.attackEntityFrom(causeImpactDamage(hitEntity, getThrower()), getImpactDamage(hitEntity));
			ItemStack stack = getItemStack();
			if(stack.getItem().isDamageable() && stack.attemptDamageItem(1, rand)) {
				Helpers.playSound(world, this, "reforged:boomerang_break", 1.0F, 1.0F);
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
		setItemStack(new ItemStack(tagCompund.getCompoundTag("item")));
		setCoords(tagCompund.getDouble("playerX"), tagCompund.getDouble("playerY"), tagCompund.getDouble("playerZ"));
	}

	@Override
	protected float getImpactDamage(Entity target) {
		return getMaterialDefinition().
				getDamageVsEntity() + 5;
	}
	
}