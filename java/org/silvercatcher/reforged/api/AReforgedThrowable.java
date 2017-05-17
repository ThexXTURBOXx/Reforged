package org.silvercatcher.reforged.api;

import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.*;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public abstract class AReforgedThrowable extends EntityThrowable {
	
	private final String damageName;
	
	public static final DataSerializer<Double> DOUBLE = new DataSerializer<Double>()
    {
        @Override
		public void write(PacketBuffer buf, Double value)
        {
            buf.writeDouble(value);
        }
        @Override
		public Double read(PacketBuffer buf)
        {
            return buf.readDouble();
        }
        @Override
		public DataParameter<Double> createKey(int id)
        {
            return new DataParameter(id, this);
        }
    };
    
    public static final DataSerializer<ItemStack> ITEMSTACK = new DataSerializer<ItemStack>()
    {
        @Override
		public void write(PacketBuffer buf, ItemStack value)
        {
            buf.writeItemStack(value);
        }
        @Override
		public ItemStack read(PacketBuffer buf)
        {
            try {
				return buf.readItemStack();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
        }
        @Override
		public DataParameter<ItemStack> createKey(int id)
        {
            return new DataParameter(id, this);
        }
    };
    
	public static final DataParameter<Boolean> INITIATED = EntityDataManager.<Boolean>createKey(AReforgedThrowable.class, DataSerializers.BOOLEAN);
	
	static {
		DataSerializers.registerSerializer(DOUBLE);
		DataSerializers.registerSerializer(ITEMSTACK);
	}
	
	public AReforgedThrowable(World worldIn, String damageName) {
		super(worldIn);
		this.damageName = damageName;
	}
	
	public AReforgedThrowable(World worldIn, EntityLivingBase thrower, ItemStack stack, String damageName) {
		super(worldIn, thrower);
		this.damageName = damageName;
        setLocationAndAngles(thrower.posX, thrower.posY + (double)thrower.getEyeHeight(), thrower.posZ, thrower.rotationYaw, thrower.rotationPitch);
        posX -= (double)(MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        posY -= 0.10000000149011612D;
        posZ -= (double)(MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        setPosition(posX, posY, posZ);
        float f = 0.4F;
        motionX = (double)(-MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI) * f);
        motionZ = (double)(MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI) * f);
        motionY = (double)(-MathHelper.sin(rotationPitch / 180.0F * (float)Math.PI) * f);
        setThrowableHeading(motionX, motionY, motionZ, 1.5F, 1.0F);
	}
	
	public void setInited() {
		dataManager.set(INITIATED, true);
	}
	
	@Override
	public void onUpdate() {
		if(isInited()) {
			super.onUpdate();
			onUpdated();
		}
	}
	
	public void onUpdated() {}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(INITIATED, false);
	}
	
	@Override
	protected void onImpact(RayTraceResult target) {
		
		boolean broken;
		
		if(target.entityHit == null) {
			
			broken = onBlockHit(target.getBlockPos());
			
		} else {
			
			broken = onEntityHit(target.entityHit instanceof EntityLivingBase
					? (EntityLivingBase) target.entityHit : target.entityHit); 
		}
		
		if(broken && !world.isRemote) setDead();
	}
	
	protected boolean isInited() {
		return dataManager.get(INITIATED);
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
		tagCompound.setBoolean("initiated", dataManager.get(INITIATED));
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		
		super.readEntityFromNBT(tagCompund);
		dataManager.set(INITIATED, tagCompund.getBoolean("initiated"));
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
