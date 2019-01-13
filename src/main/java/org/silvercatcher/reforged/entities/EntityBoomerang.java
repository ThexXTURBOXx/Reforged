package org.silvercatcher.reforged.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.silvercatcher.reforged.api.AReforgedThrowable;
import org.silvercatcher.reforged.api.CompoundTags;
import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.items.weapons.ItemBoomerang;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.util.Helpers;

public class EntityBoomerang extends AReforgedThrowable {

	public static final String NAME = "boomerang";

	public static final DataParameter<Float> THROWER_X = EntityDataManager.createKey(EntityBoomerang.class,
			DataSerializers.FLOAT);
	public static final DataParameter<Float> THROWER_Y = EntityDataManager.createKey(EntityBoomerang.class,
			DataSerializers.FLOAT);
	public static final DataParameter<Float> THROWER_Z = EntityDataManager.createKey(EntityBoomerang.class,
			DataSerializers.FLOAT);
	public static final DataParameter<Float> YAW = EntityDataManager.createKey(EntityBoomerang.class,
			DataSerializers.FLOAT);
	public static final DataParameter<ItemStack> STACK = EntityDataManager.createKey(EntityBoomerang.class,
			DataSerializers.ITEM_STACK);

	public EntityBoomerang(World worldIn) {
		super(ReforgedAdditions.ENTITY_BOOMERANG, worldIn, NAME);
	}

	public EntityBoomerang(World worldIn, EntityLivingBase thrower, ItemStack stack) {
		super(ReforgedAdditions.ENTITY_BOOMERANG, worldIn, thrower, stack, NAME);
		setItemStack(stack);
		setCoords(thrower.posX, thrower.posY + thrower.getEyeHeight(), thrower.posZ);
		setInited();
	}

	@Override
	protected void registerData() {
		super.registerData();
		dataManager.register(STACK, ItemStack.EMPTY);
		dataManager.register(YAW, 0F);
		dataManager.register(THROWER_X, 0F);
		dataManager.register(THROWER_Y, 0F);
		dataManager.register(THROWER_Z, 0F);
		rotationYaw = dataManager.get(YAW);
	}

	@Override
	protected float getGravityVelocity() {
		return 0f;
	}

	@Override
	protected float getImpactDamage(Entity target) {
		return getMaterialDefinition().getDamageVsEntity() + 5;
	}

	public ItemStack getItemStack() {
		return dataManager.get(STACK);
	}

	public void setItemStack(ItemStack stack) {
		if (stack == null || stack.isEmpty() || !(stack.getItem() instanceof ItemBoomerang)) {
			throw new IllegalArgumentException("Invalid Itemstack!");
		}
		dataManager.set(STACK, stack);
	}

	public MaterialDefinition getMaterialDefinition() {
		MaterialDefinition md;
		try {
			md = ((ItemBoomerang) getItemStack().getItem()).getMaterialDefinition();
		} catch (NullPointerException | ClassCastException e) {
			md = null;
		}
		return md;
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

	@Override
	protected boolean onBlockHit(BlockPos blockPos) {
		if (!world.isRemote) {
			if (getItemStack().getMaxDamage() - getItemStack().getDamage() > 0 && !creativeUse()) {
				entityDropItem(getItemStack(), 0.5f);
				Helpers.playSound(world, this, "boomerang_hit", 2.0F, 1.0F);
			} else if (getItemStack().getMaxDamage() - getItemStack().getDamage() <= 0) {
				Helpers.playSound(world, this, "boomerang_break", 2.0F, 1.0F);
			} else if (creativeUse()) {
				Helpers.playSound(world, this, "boomerang_hit", 2.0F, 1.0F);
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
			if (stack.getMaxDamage() - stack.getDamage() > 0 && !creativeUse()) {
				p.inventory.addItemStackToInventory(stack);
				world.playSound(null, posX, posY, posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.MASTER, 0.5F,
						0.7F);
			} else if (getItemStack().getMaxDamage() - getItemStack().getDamage() <= 0) {
				Helpers.playSound(world, this, "boomerang_break", 1.0F, 1.0F);
			}
			return true;
		} else {
			// It's an hit entity
			hitEntity.attackEntityFrom(causeImpactDamage(hitEntity, getThrower()), getImpactDamage(hitEntity));
			ItemStack stack = getItemStack();
			if (stack.getItem().isDamageable() && stack.attemptDamageItem(1, rand, null)) {
				Helpers.playSound(world, this, "boomerang_break", 1.0F, 1.0F);
				return true;
			} else {
				setItemStack(stack);
			}
		}
		return false;
	}

	@Override
	public void onUpdated() {

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

		motionX -= (0.05D * dx);
		motionY -= (0.05D * dy);
		motionZ -= (0.05D * dz);

		if (isInWater()) {
			if (!world.isRemote) {
				if (getItemStack().getMaxDamage() - getItemStack().getDamage() > 0 && !creativeUse()) {
					entityDropItem(getItemStack(), 0.5f);
				} else if (getItemStack().getMaxDamage() - getItemStack().getDamage() <= 0) {
					Helpers.playSound(world, this, "boomerang_break", 1.0F, 1.0F);
				}
				remove();
			}
		}

		// After 103 ticks, the Boomerang drops exactly where the thrower stood
		if (ticksExisted >= 103) {
			if (CompoundTags.giveCompound(getItemStack()).getBoolean(CompoundTags.ENCHANTED)) {
				if (onEntityHit(getThrower()))
					remove();
			} else {
				if (!world.isRemote) {
					if (getItemStack().getMaxDamage() - getItemStack().getDamage() > 0 && !creativeUse()) {
						entityDropItem(getItemStack(), 0.5f);
					} else if (getItemStack().getMaxDamage() - getItemStack().getDamage() <= 0) {
						Helpers.playSound(world, this, "boomerang_break", 1.0F, 1.0F);
					}
					remove();
				}
			}
		}
		rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
		rotationYaw += 20;
		setRotation(rotationYaw, rotationPitch);
	}

	@Override
	public void readAdditional(NBTTagCompound compound) {
		super.readAdditional(compound);
		setItemStack(ItemStack.read(compound.getCompound("item")));
		setCoords(compound.getDouble("playerX"), compound.getDouble("playerY"), compound.getDouble("playerZ"));
		dataManager.set(YAW, compound.getFloat("yawreforged"));
	}

	public void setCoords(double playerX, double playerY, double playerZ) {
		dataManager.set(THROWER_X, (float) playerX);
		dataManager.set(THROWER_Y, (float) playerY);
		dataManager.set(THROWER_Z, (float) playerZ);
	}

	@Override
	public void writeAdditional(NBTTagCompound compound) {
		super.writeAdditional(compound);

		compound.putDouble("playerX", getPosX());
		compound.putDouble("playerY", getPosY());
		compound.putDouble("playerZ", getPosZ());
		compound.putDouble("yawreforged", dataManager.get(YAW));

		if (getItemStack() != null && !getItemStack().isEmpty())
			compound.put("item", getItemStack().write(new NBTTagCompound()));
	}

	@Override
	public void deserializeNBT(INBTBase nbt) {
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
	}

}