package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.api.AReforgedThrowable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityDynamite extends AReforgedThrowable {
	
	//In the lang-files we don't need the "dynamite-damage"-String,
	//because the dynamite can't kill anyone as it does 0 damage...
	
	public EntityDynamite(World world) {
		
		super(world, "dynamite");
	}
	
	public EntityDynamite(World world, EntityLivingBase thrower, ItemStack stack) {
		
		super(world, thrower, stack, "dynamite");
	}
	
	@Override
	protected boolean onBlockHit(BlockPos blockPos) {
		worldObj.createExplosion(this, posX, posY, posZ, 2, true);
		return true;
	}
	
	@Override
	protected boolean onEntityHit(Entity entity) {
		worldObj.createExplosion(this, posX, posY, posZ, 2, true);
		return true;
	}
	
	@Override
	protected float getImpactDamage(Entity target) {

		return 0f;
	}
	
	@Override
	protected float getGravityVelocity() {
		return 0.05F;
	}
}
