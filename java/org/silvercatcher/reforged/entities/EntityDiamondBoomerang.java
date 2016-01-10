package org.silvercatcher.reforged.entities;

import java.util.Random;

import org.silvercatcher.reforged.ReforgedItems;
import org.silvercatcher.reforged.ReforgedMod;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityDiamondBoomerang extends EntityThrowable
{
	
    public EntityDiamondBoomerang(World par1World)
    {
        super(par1World);
    }
    public EntityDiamondBoomerang(World par1World, EntityLivingBase par2EntityLivingBase)
    {
        super(par1World, par2EntityLivingBase);
    }
    public EntityDiamondBoomerang(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
        
    }
    
    @Override
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {
        if (par1MovingObjectPosition.entityHit != null)
        {
            byte damageBoomerang = 5;
            par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), damageBoomerang);
        }
        if (!this.worldObj.isRemote)
        {
        	Random r = new Random();
        	if(r.nextInt(1001) == 1) {
        		this.playSound("mob.blaze.hit", 0.5F, 0.4F);
            	this.entityDropItem(new ItemStack(ReforgedItems.DIAMOND_BOOMERANG), 0.0F);        		
        	} else {
            	this.playSound("liquid.lavapop", 0.5F, 0.4F);
        	}
        	this.setDead();
        }
    }
    
    @Override
    protected float getGravityVelocity() 
    {
		return 0.01F;
    }
    
}