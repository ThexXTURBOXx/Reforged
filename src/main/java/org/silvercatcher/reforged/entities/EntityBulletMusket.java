package org.silvercatcher.reforged.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.silvercatcher.reforged.api.AReforgedThrowable;
import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.proxy.CommonProxy;

public class EntityBulletMusket extends AReforgedThrowable {

	public static final String NAME = "musket";

	public EntityBulletMusket(World worldIn) {
		super(ReforgedAdditions.ENTITY_MUSKET, worldIn, NAME);
		setNoGravity(true);
	}

	public EntityBulletMusket(World worldIn, EntityLivingBase throwerIn, ItemStack stack) {
		super(ReforgedAdditions.ENTITY_MUSKET, worldIn, throwerIn, stack, NAME);
		setNoGravity(true);
		motionX *= 5;
		motionY *= 5;
		motionZ *= 5;
		setInited();
	}

	@Override
	protected float getImpactDamage(Entity target) {
		return CommonProxy.damage_musket;
	}

	@Override
	protected boolean onEntityHit(Entity entity) {
		entity.attackEntityFrom(causeImpactDamage(entity, getThrower()), getImpactDamage(entity));
		return true;
	}

	@Override
	public void deserializeNBT(INBTBase nbt) {
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
	}

}