package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.api.AReforgedThrowable;
import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.items.weapons.ItemJavelin;
import org.silvercatcher.reforged.util.Helpers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityJavelin extends AReforgedThrowable {

	public static final DataParameter<ItemStack> STACK = EntityDataManager.<ItemStack>createKey(EntityJavelin.class,
			DataSerializers.OPTIONAL_ITEM_STACK);
	public static final DataParameter<Integer> DURATION = EntityDataManager.<Integer>createKey(EntityJavelin.class,
			DataSerializers.VARINT);

	public EntityJavelin(World worldIn) {

		super(worldIn, "javelin");
	}

	public EntityJavelin(World worldIn, EntityLivingBase throwerIn, ItemStack stack, int durationLoaded) {

		super(worldIn, throwerIn, stack, "javelin");

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
		setInited();
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(STACK, new ItemStack(ReforgedAdditions.JAVELIN));
		dataManager.register(DURATION, 1);
	}

	public ItemStack getItemStack() {
		return dataManager.get(STACK);
	}

	public void setItemStack(ItemStack stack) {

		if (stack == null || stack.isEmpty() || !(stack.getItem() instanceof ItemJavelin)) {
			throw new IllegalArgumentException("Invalid Itemstack!");
		}
		dataManager.set(STACK, stack);
	}

	@Override
	protected boolean onBlockHit(BlockPos blockPos) {
		ItemStack stack = getItemStack();
		if (!stack.attemptDamageItem(1, rand)) {
			setItemStack(stack);
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
		return true;
	}

	@Override
	protected void onImpact(RayTraceResult target) {
		super.onImpact(target);
		if (getItemStack().getMaxDamage() - getItemStack().getItemDamage() > 0) {
			if (!world.isRemote && !creativeUse()) {
				entityDropItem(getItemStack(), 0.5f);
			}
		} else {
			Helpers.playSound(world, this, "boomerang_break", 1.0F, 1.0F);
		}
	}

	public int getDurLoaded() {
		return dataManager.get(DURATION);
	}

	public void setDurLoaded(int durloaded) {

		dataManager.set(DURATION, durloaded);
	}

	@Override
	protected float getGravityVelocity() {
		return 0.03F;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {

		super.writeEntityToNBT(tagCompound);

		tagCompound.setInteger("durloaded", getDurLoaded());

		if (getItemStack() != null && !getItemStack().isEmpty()) {
			tagCompound.setTag("item", getItemStack().writeToNBT(new NBTTagCompound()));
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {

		super.readEntityFromNBT(tagCompund);
		setDurLoaded(tagCompund.getInteger("durloaded"));
		setItemStack(new ItemStack(tagCompund.getCompoundTag("item")));
	}

	@Override
	protected float getImpactDamage(Entity target) {

		return 5 + getDurLoaded() / 10;
	}
}
