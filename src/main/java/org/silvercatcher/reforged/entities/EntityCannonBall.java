package org.silvercatcher.reforged.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.silvercatcher.reforged.api.AReforgedThrowable;

public class EntityCannonBall extends AReforgedThrowable {

	public static final String NAME = "cannon";
	public static final EntityType<EntityCannonBall> TYPE =
			EntityType.Builder.create(EntityCannonBall.class, EntityCannonBall::new).build(NAME);

	public EntityCannonBall(World worldIn) {
		super(TYPE, worldIn, NAME);
	}

	public EntityCannonBall(World worldIn, EntityLivingBase throwerIn, ItemStack stack) {
		super(TYPE, worldIn, throwerIn, stack, NAME);
		setInited();
	}

	@Override
	protected float getImpactDamage(Entity target) {
		return 0;
	}

}