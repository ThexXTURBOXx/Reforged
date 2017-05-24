package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.api.AReforgedThrowable;
import org.silvercatcher.reforged.api.CompoundTags;
import org.silvercatcher.reforged.items.weapons.ItemBoomerang;
import org.silvercatcher.reforged.material.MaterialDefinition;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityBoomerang extends AReforgedThrowable {

	public EntityBoomerang(World world) {

		super(world, "boomerang");
	}

	public EntityBoomerang(World world, EntityLivingBase thrower, ItemStack stack) {

		super(world, thrower, stack, "boomerang");
		setItemStack(stack);
		setCoords(thrower.posX, thrower.posY + thrower.getEyeHeight(), thrower.posZ);
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

		if (stack == null || !(stack.getItem() instanceof ItemBoomerang)) {
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

	public MaterialDefinition getMaterialDefinition() {

		return ((ItemBoomerang) getItemStack().getItem()).getMaterialDefinition();
	}

	@Override
	public void onUpdate() {

		super.onUpdate();

		if (getThrower() != null && CompoundTags.giveCompound(getItemStack()).getBoolean(CompoundTags.ENCHANTED)) {
			setCoords(getThrower().posX, getThrower().posY + getThrower().getEyeHeight(), getThrower().posZ);
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

		// After 103 ticks, the Boomerang drops exactly where the thrower stood
		if (isInWater()) {
			if (!worldObj.isRemote) {
				if (getItemStack().getMaxDamage() - getItemStack().getItemDamage() > 0 && !creativeUse()) {
					entityDropItem(getItemStack(), 0.5f);
				} else {
					worldObj.playSoundAtEntity(this, "reforged:boomerang_break", 1.0F, 1.0F);
				}
				setDead();
			}
		}
		if (ticksExisted >= 103) {
			if (CompoundTags.giveCompound(getItemStack()).getBoolean(CompoundTags.ENCHANTED)) {
				if (onEntityHit(getThrower()))
					setDead();
			} else {
				if (!worldObj.isRemote) {
					if (getItemStack().getMaxDamage() - getItemStack().getItemDamage() > 0 && !creativeUse()) {
						entityDropItem(getItemStack(), 0.5f);
					} else {
						worldObj.playSoundAtEntity(this, "reforged:boomerang_break", 1.0F, 1.0F);
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

		if (!worldObj.isRemote) {
			if (getItemStack().getMaxDamage() - getItemStack().getItemDamage() > 0 && !creativeUse()) {
				entityDropItem(getItemStack(), 0.5f);
			}
		}
		return true;
	}

	@Override
	protected boolean onEntityHit(Entity hitEntity) {
		if (hitEntity == getThrower()) {
			// It's the thrower himself
			ItemStack stack = getItemStack();
			EntityPlayer p = (EntityPlayer) hitEntity;
			if (stack.getMaxDamage() - stack.getItemDamage() > 0 && !creativeUse()) {
				p.inventory.addItemStackToInventory(stack);
				worldObj.playSoundAtEntity(this, "random.pop", 0.5F, 0.7F);
			} else {
				worldObj.playSoundAtEntity(this, "reforged:boomerang_break", 1.0F, 1.0F);
			}
			return true;
		} else {
			// It's an hit entity
			hitEntity.attackEntityFrom(causeImpactDamage(hitEntity, getThrower()), getImpactDamage(hitEntity));
			ItemStack stack = getItemStack();
			if (stack.getItem().isDamageable() && stack.attemptDamageItem(1, rand)) {
				worldObj.playSoundAtEntity(this, "reforged:boomerang_break", 1.0F, 1.0F);
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

		if (getItemStack() != null) {
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
		return getMaterialDefinition().getDamageVsEntity() + 5;
	}
}
