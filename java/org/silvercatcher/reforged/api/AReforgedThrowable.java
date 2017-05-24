package org.silvercatcher.reforged.api;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

public abstract class AReforgedThrowable extends EntityThrowable {
	
	private final String damageName;
	
	public AReforgedThrowable(World world, String damageName) {
		
		super(world);
		this.damageName = damageName;
	}
	
	public AReforgedThrowable(World world, EntityLivingBase throwerIn, ItemStack stack, String damageName) {
		
		super(world, throwerIn);
		this.damageName = damageName;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
	}

	@Override
	protected void onImpact(MovingObjectPosition target) {
		boolean broken;
		if(target.entityHit == null) {
			IBlockState state = worldObj.getBlockState(target.getBlockPos());
			broken = state.getBlock().getCollisionBoundingBox(worldObj, target.getBlockPos(), state) != null ? onBlockHit(target.getBlockPos()) : false;
		} else {
			if(target.entityHit instanceof EntityLivingBase && target.entityHit.equals(getThrower()) && ticksExisted < 5) {
				return;
			}
			broken = onEntityHit(target.entityHit instanceof EntityLivingBase
					? (EntityLivingBase) target.entityHit : target.entityHit); 
		}
		if(broken) setDead();
	}
	
	/** Called when the entity hits a block
	 * @param blockPos The position where the entity landed
	 * @return should the entity get setDead() ? */
	protected boolean onBlockHit(BlockPos blockPos) {
		return true;
	}
	
	/** Called when the entity hits an other entity
	 * @param entity The entity which got hit
	 * @return should the entity get setDead() ? */
	protected boolean onEntityHit(Entity entity) {
		return true;
	}

	/** Called when the entity hits a living entity
	 * @param living The mob which got hit
	 * @return should the entity get setDead() ? */
	protected boolean onEntityHit(EntityLivingBase living) {
		return onEntityHit((Entity) living);
	}

	/** Causes the damage, which is set in the constructor
	 * @param target the entity which got hit
	 * @param shooter the mob which shot
	 * @return the specified DamageSource*/
	protected DamageSource causeImpactDamage(Entity target, EntityLivingBase shooter) {
		return new EntityDamageSourceIndirect(damageName, target, shooter).setProjectile();
	}
	
	/** Sets the damage which should be caused. It
	 * is set in half-lives.
	 * @param target The mob which gets hit
	 * @return the amount of damage*/
	protected abstract float getImpactDamage(Entity target);
	
	@Override
	protected float getGravityVelocity() {
		return 0.005f;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		
		super.writeEntityToNBT(tagCompound);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		
		super.readEntityFromNBT(tagCompund);
	}
	
	/**@return True, if the thrower is a player in Creative Mode.
	 * False, if the player is in Survival Mode or the thrower is an Entity*/
	public boolean creativeUse() {
		return creativeUse(getThrower());
	}
	
	/**@return True, if the given Entity is a player in Creative Mode.
	 * False, if the player is in Survival Mode or the entity is a normal Entity*/
	public boolean creativeUse(Entity e) {
		return (e instanceof EntityPlayer && ((EntityPlayer) e).capabilities.isCreativeMode)
			   || !(e instanceof EntityPlayer);
	}
	
}
