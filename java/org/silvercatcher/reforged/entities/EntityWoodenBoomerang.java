package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.ReforgedMod;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityWoodenBoomerang extends EntityThrowable
{
	
    public EntityWoodenBoomerang(World par1World)
    {
        super(par1World);
    }
    public EntityWoodenBoomerang(World par1World, EntityLivingBase par2EntityLivingBase)
    {
        super(par1World, par2EntityLivingBase);
    }
    public EntityWoodenBoomerang(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
        
    }
    
    @Override
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {
        if (par1MovingObjectPosition.entityHit != null)
        {
            byte damageBoomerang = 2;
            par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), damageBoomerang);
        }
        if (!this.worldObj.isRemote)
        {
            this.setDead();
        }
    }
    
    @Override
    protected float getGravityVelocity() 
    {
		return 0.01F;
    }
    
}