package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.ReforgedRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBulletMusket extends AReforgedThrowable {
	
	public EntityBulletMusket(World worldIn) {
		
		super(worldIn, "musket");
	}
	
	public EntityBulletMusket(World worldIn, EntityLivingBase throwerIn, ItemStack stack) {
		
		super(worldIn, throwerIn, stack, "musket");
	}
	
	@Override
	protected boolean onEntityHit(Entity entity) {
		entity.attackEntityFrom(causeImpactDamage(entity, getThrower()), 10);
		return true;
	}
	
	@Override
	protected DamageSource causeImpactDamage(Entity target, EntityLivingBase shooter) {
		
		return new EntityDamageSourceIndirect("bullet_musket", target, shooter);
	}

	@Override
	protected float getImpactDamage(Entity target) {

		return 4f;
	}
}
