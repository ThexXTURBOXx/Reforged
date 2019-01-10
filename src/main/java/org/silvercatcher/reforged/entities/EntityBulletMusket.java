package org.silvercatcher.reforged.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.silvercatcher.reforged.api.AReforgedThrowable;
import org.silvercatcher.reforged.proxy.CommonProxy;

public class EntityBulletMusket extends AReforgedThrowable {

	public static final String NAME = "musket";
	public static final EntityType<EntityBulletMusket> TYPE =
			EntityType.Builder.create(EntityBulletMusket.class, EntityBulletMusket::new).build(NAME);

	public EntityBulletMusket(World worldIn) {
		super(TYPE, worldIn, NAME);
		setNoGravity(true);
	}

	public EntityBulletMusket(World worldIn, EntityLivingBase throwerIn, ItemStack stack) {
		super(TYPE, worldIn, throwerIn, stack, NAME);
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

}