package org.silvercatcher.reforged.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityDynamite extends AReforgedThrowable {
	
	//In the lang-files we don't need the "dynamite-damage"-String,
	//because the dynamite can't kill anyone as it does 0 damage...
	
	public EntityDynamite(World worldIn) {
		
		super(worldIn, "dynamite");
	}
	
	public EntityDynamite(World worldIn, EntityLivingBase throwerIn, ItemStack stack) {
		
		super(worldIn, throwerIn, stack, "dynamite");
	}
	
	@Override
	protected boolean onBlockHit(BlockPos blockPos) {
		Explosion e = worldObj.createExplosion(this, posX, posY, posZ, 5, true);
		return true;
	}
	
	@Override
	protected boolean onEntityHit(Entity entity) {
		Explosion e = worldObj.createExplosion(this, posX, posY, posZ, 5, true);
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
