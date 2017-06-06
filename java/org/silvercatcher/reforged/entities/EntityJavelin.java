package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.api.AReforgedThrowable;
import org.silvercatcher.reforged.items.weapons.ItemJavelin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityJavelin extends AReforgedThrowable {

	public EntityJavelin(World world) {

		super(world, "javelin");
	}

	public EntityJavelin(World world, EntityLivingBase thrower, ItemStack stack, int durationLoaded) {

		super(world, thrower, stack, "javelin");

		setItemStack(stack);
		setDurLoaded(durationLoaded);

		if (durationLoaded < 20) {
			durationLoaded = 20;
		} else if (durationLoaded > 40) {
			durationLoaded = 40;
		}
		this.motionX *= (durationLoaded / 20);
		this.motionY *= (durationLoaded / 20);
		this.motionZ *= (durationLoaded / 20);
	}

	@Override
	protected void entityInit() {
		super.entityInit();

		// id 5 = ItemStack of Javelin, type 5 = ItemStack
		dataWatcher.addObjectByDataType(5, 5);

		// id 6 = Loaded Duration, type 2 = Integer
		dataWatcher.addObjectByDataType(6, 2);
	}

	public int getDurLoaded() {
		return dataWatcher.getWatchableObjectInt(6);
	}

	@Override
	protected float getGravityVelocity() {
		return 0.03F;
	}

	@Override
	protected float getImpactDamage(Entity target) {

		return 5 + getDurLoaded() / 10;
	}

	public ItemStack getItemStack() {
		return dataWatcher.getWatchableObjectItemStack(5);
	}

	@Override
	protected boolean onBlockHit(BlockPos blockPos) {
		ItemStack stack = getItemStack();
		if (!stack.attemptDamageItem(1, rand)) {
			setItemStack(stack);
		}
		if (getItemStack().getMaxDamage() - getItemStack().getItemDamage() > 0) {
			if (!creativeUse()) {
				entityDropItem(getItemStack(), 0.5f);
			}
		} else {
			worldObj.playSoundAtEntity(this, "reforged:boomerang_break", 1.0F, 1.0F);
		}
		return true;
	}

	@Override
	protected boolean onEntityHit(Entity entity) {
		entity.attackEntityFrom(causeImpactDamage(entity, getThrower()), getImpactDamage(entity));
		ItemStack stack = getItemStack();
		if (!stack.attemptDamageItem(1, rand)) {
			setItemStack(stack);
		}
		if (getItemStack().getMaxDamage() - getItemStack().getItemDamage() > 0) {
			if (!creativeUse()) {
				entityDropItem(getItemStack(), 0.5f);
			}
		} else {
			worldObj.playSoundAtEntity(this, "reforged:boomerang_break", 1.0F, 1.0F);
		}
		return true;
	}

	@Override
	public void onUpdate() {
		if (!isDead)
			super.onUpdate();
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {

		super.readEntityFromNBT(tagCompund);
		setDurLoaded(tagCompund.getInteger("durloaded"));
		setItemStack(ItemStack.loadItemStackFromNBT(tagCompund.getCompoundTag("item")));
	}

	public void setDurLoaded(int durloaded) {

		dataWatcher.updateObject(6, durloaded);
	}

	public void setItemStack(ItemStack stack) {

		if (stack == null || !(stack.getItem() instanceof ItemJavelin)) {
			throw new IllegalArgumentException("Invalid Itemstack!");
		}
		dataWatcher.updateObject(5, stack);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {

		super.writeEntityToNBT(tagCompound);

		tagCompound.setInteger("durloaded", getDurLoaded());

		if (getItemStack() != null) {
			tagCompound.setTag("item", getItemStack().writeToNBT(new NBTTagCompound()));
		}
	}
}
