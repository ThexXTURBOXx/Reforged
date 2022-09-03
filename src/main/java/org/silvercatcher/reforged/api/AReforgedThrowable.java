package org.silvercatcher.reforged.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public abstract class AReforgedThrowable extends EntityThrowable {

    public static final DataParameter<Boolean> INITIATED = EntityDataManager
            .createKey(AReforgedThrowable.class, DataSerializers.BOOLEAN);

    private final String damageName;

    public AReforgedThrowable(World worldIn, EntityLivingBase thrower, ItemStack stack, String damageName) {
        super(worldIn, thrower);
        this.damageName = damageName;
        setLocationAndAngles(thrower.posX, thrower.posY + thrower.getEyeHeight(), thrower.posZ, thrower.rotationYaw,
                thrower.rotationPitch);
        posX -= MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.25;
        posY -= 0.10000000149011612D;
        posZ -= MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.25;
        setPosition(posX, posY, posZ);
        motionX = -MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI)
                * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI);
        motionZ = MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI)
                * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI);
        motionY = -MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI);
        shoot(motionX, motionY, motionZ, 1.5F, 1.0F);
    }

    public AReforgedThrowable(World worldIn, String damageName) {
        super(worldIn);
        this.damageName = damageName;
    }

    /**
     * Causes the damage, which is set in the constructor
     *
     * @param target  the entity which got hit
     * @param shooter the mob which shot
     * @return the specified DamageSource
     */
    protected DamageSource causeImpactDamage(Entity target, EntityLivingBase shooter) {
        return new EntityDamageSourceIndirect(damageName, target, shooter).setProjectile();
    }

    /**
     * @return True, if the thrower is a player in Creative Mode. False, if the
     *         player is in Survival Mode or the thrower is an Entity
     */
    public boolean creativeUse() {
        return creativeUse(getThrower());
    }

    /**
     * @return True, if the given Entity is a player in Creative Mode. False, if the
     *         player is in Survival Mode or the entity is a normal Entity
     */
    public boolean creativeUse(Entity e) {
        return !(e instanceof EntityPlayer) || ((EntityPlayer) e).capabilities.isCreativeMode;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(INITIATED, false);
    }

    @Override
    protected float getGravityVelocity() {
        return 0.005f;
    }

    /**
     * Sets the damage which should be caused. It is set in half-lives.
     *
     * @param target The mob which gets hit
     * @return the amount of damage
     */
    protected abstract float getImpactDamage(Entity target);

    protected boolean isInited() {
        return dataManager.get(INITIATED);
    }

    /**
     * Called when the entity hits a block
     *
     * @param blockPos The position where the entity landed
     * @return should the entity get setDead() ?
     */
    protected boolean onBlockHit(BlockPos blockPos) {
        return true;
    }

    /**
     * Called when the entity hits an other entity
     *
     * @param entity The entity which got hit
     * @return should the entity get setDead() ?
     */
    protected boolean onEntityHit(Entity entity) {
        return true;
    }

    /**
     * Called when the entity hits a living entity
     *
     * @param living The mob which got hit
     * @return should the entity get setDead() ?
     */
    protected boolean onEntityHit(EntityLivingBase living) {
        return onEntityHit((Entity) living);
    }

    @Override
    protected void onImpact(RayTraceResult target) {
        if (!world.isRemote) {
            boolean broken;
            if (target.entityHit == null) {
                broken = world.getBlockState(target.getBlockPos()).getCollisionBoundingBox(world,
                        target.getBlockPos()) != null && onBlockHit(target.getBlockPos());
            } else {
                if (target.entityHit instanceof EntityLivingBase && target.entityHit.equals(getThrower())
                        && ticksExisted < 5) {
                    return;
                }
                broken = onEntityHit(target.entityHit instanceof EntityLivingBase ? target.entityHit
                        : target.entityHit);
            }
            if (broken)
                setDead();
        }
    }

    @Override
    public void onUpdate() {
        if (isInited()) {
            super.onUpdate();
            onUpdated();
        }
    }

    public void onUpdated() {
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund) {

        super.readEntityFromNBT(tagCompund);
        dataManager.set(INITIATED, tagCompund.getBoolean("initiated"));
    }

    public void setInited() {
        dataManager.set(INITIATED, true);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {

        super.writeEntityToNBT(tagCompound);
        tagCompound.setBoolean("initiated", dataManager.get(INITIATED));
    }

}
