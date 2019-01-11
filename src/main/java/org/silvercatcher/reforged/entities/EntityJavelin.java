package org.silvercatcher.reforged.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.api.AReforgedThrowable;
import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.items.weapons.ItemJavelin;
import org.silvercatcher.reforged.util.Helpers;

public class EntityJavelin extends AReforgedThrowable {

	public static final String NAME = "javelin";
	public static final EntityType<EntityJavelin> TYPE =
			ReforgedRegistry.registerEntity(EntityType.Builder.create(EntityJavelin.class, EntityJavelin::new).build(NAME));

	public static final DataParameter<ItemStack> STACK = EntityDataManager.createKey(EntityJavelin.class,
			DataSerializers.ITEM_STACK);
	public static final DataParameter<Integer> DURATION = EntityDataManager.createKey(EntityJavelin.class,
			DataSerializers.VARINT);

	public EntityJavelin(World worldIn) {
		super(TYPE, worldIn, NAME);
	}

	public EntityJavelin(World worldIn, EntityLivingBase throwerIn, ItemStack stack, int durationLoaded) {
		super(TYPE, worldIn, throwerIn, stack, NAME);

		setItemStack(stack);
		setDurLoaded(durationLoaded);

		if (durationLoaded < 20) {
			durationLoaded = 20;
		} else if (durationLoaded > 40) {
			durationLoaded = 40;
		}
		this.motionX *= (durationLoaded / 20f);
		this.motionY *= (durationLoaded / 20f);
		this.motionZ *= (durationLoaded / 20f);
		setInited();
	}

	@Override
	protected void registerData() {
		super.registerData();
		dataManager.register(STACK, new ItemStack(ReforgedAdditions.JAVELIN));
		dataManager.register(DURATION, 1);
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
	protected float getImpactDamage(Entity target) {

		return 5 + getDurLoaded() / 10;
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
		if (!stack.attemptDamageItem(1, rand, null)) {
			setItemStack(stack);
		}
		if (getItemStack().getMaxDamage() - getItemStack().getDamage() > 0) {
			if (!creativeUse()) {
				entityDropItem(getItemStack(), 0.5f);
			}
		} else {
			Helpers.playSound(world, this, "boomerang_break", 1.0F, 1.0F);
		}
		return true;
	}

	@Override
	protected boolean onEntityHit(Entity entity) {
		entity.attackEntityFrom(causeImpactDamage(entity, getThrower()), getImpactDamage(entity));
		ItemStack stack = getItemStack();
		if (!stack.attemptDamageItem(1, rand, null)) {
			setItemStack(stack);
		}
		if (getItemStack().getMaxDamage() - getItemStack().getDamage() > 0) {
			if (!creativeUse()) {
				entityDropItem(getItemStack(), 0.5f);
			}
		} else {
			Helpers.playSound(world, this, "boomerang_break", 1.0F, 1.0F);
		}
		return true;
	}

	@Override
	public void tick() {
		if (!removed)
			super.tick();
	}

	@Override
	public void readAdditional(NBTTagCompound compound) {
		super.readAdditional(compound);
		setDurLoaded(compound.getInt("durloaded"));
		setItemStack(ItemStack.read(compound.getCompound("item")));
	}

	@Override
	public void writeAdditional(NBTTagCompound compound) {
		super.writeAdditional(compound);
		compound.setInt("durloaded", getDurLoaded());

		if (getItemStack() != null && !getItemStack().isEmpty()) {
			compound.setTag("item", getItemStack().write(new NBTTagCompound()));
		}
	}

	@Override
	public void deserializeNBT(INBTBase nbt) {
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
	}

}
