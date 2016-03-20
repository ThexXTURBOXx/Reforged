package org.silvercatcher.reforged.entities;

import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public abstract class AReforgedThrowable extends EntityThrowable {
	
    public static final DataSerializer<ItemStack> ITEM_STACK = new DataSerializer<ItemStack>()
    {
        @Override
		public void write(PacketBuffer buf, ItemStack value)
        {
            buf.writeItemStackToBuffer(value);
        }
        @Override
		public ItemStack read(PacketBuffer buf)
        {
            try {
				return buf.readItemStackFromBuffer();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
        }
        @Override
		public DataParameter<ItemStack> createKey(int id)
        {
            return new DataParameter(id, this);
        }
    };
    
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
    
	private final String damageName;
	
	public AReforgedThrowable(World worldIn, String damageName) {
		
		super(worldIn);
		this.damageName = damageName;
	}
	
	public AReforgedThrowable(World worldIn, EntityLivingBase throwerIn, ItemStack stack, String damageName) {
		
		super(worldIn, throwerIn);
		this.damageName = damageName;
	}
	
	public static void registerSerializers() {
		DataSerializers.registerSerializer(DOUBLE);
		DataSerializers.registerSerializer(ITEM_STACK);
	}


	@Override
	protected void entityInit() {
		super.entityInit();
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
}
