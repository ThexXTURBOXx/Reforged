package org.silvercatcher.reforged.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.api.AReforgedThrowable;

public class EntityBulletBlunderbuss extends AReforgedThrowable {

	public static final String NAME = "blunderbuss";
	public static final EntityType<EntityBulletBlunderbuss> TYPE =
			ReforgedRegistry.registerEntity(EntityType.Builder.create(EntityBulletBlunderbuss.class, EntityBulletBlunderbuss::new).build(NAME));

	public EntityBulletBlunderbuss(World worldIn) {
		super(TYPE, worldIn, NAME);
	}

	public EntityBulletBlunderbuss(World worldIn, EntityLivingBase throwerIn, ItemStack stack) {
		super(TYPE, worldIn, throwerIn, stack, NAME);
		float randomNumX = rand.nextInt(21);
		float randomNumY = rand.nextInt(21);
		float randomNumZ = rand.nextInt(21);
		if (rand.nextBoolean()) {
			randomNumX = 0 - randomNumX;
		}
		if (rand.nextBoolean()) {
			randomNumY = 0 - randomNumY;
		}
		if (rand.nextBoolean()) {
			randomNumZ = 0 - randomNumZ;
		}
		this.motionX += randomNumX / 100;
		this.motionY += randomNumY / 100;
		this.motionZ += randomNumZ / 100;
		motionX *= 5;
		motionY *= 5;
		motionZ *= 5;
		setInited();
	}

	@Override
	protected float getImpactDamage(Entity target) {
		return (((30 - ticksExisted) / 4f) + 4f);
	}

	@Override
	protected boolean onEntityHit(Entity entity) {
		entity.attackEntityFrom(causeImpactDamage(entity, getThrower()), getImpactDamage(entity));
		return true;
	}

	@Override
	public void onUpdated() {
		if (ticksExisted >= 30) {
			remove();
		}
	}

	@Override
	public void deserializeNBT(INBTBase nbt) {
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
	}

}
