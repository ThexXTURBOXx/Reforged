package org.silvercatcher.reforged.entities;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.silvercatcher.reforged.api.ReforgedAdditions;

public class EntityDummy extends Entity {

    private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.createKey(EntityDummy.class,
            DataSerializers.VARINT);
    private static final DataParameter<Byte> ROCK_DIRECTION = EntityDataManager.createKey(EntityDummy.class,
            DataSerializers.BYTE);
    private static final DataParameter<Integer> CURRENT_DAMAGE = EntityDataManager.createKey(EntityDummy.class,
            DataSerializers.VARINT);
    private int durability;

    public EntityDummy(final World world) {
        super(world);
        this.preventEntitySpawning = true;
        this.rotationPitch = -20.0f;
        this.setRotation(this.rotationYaw, this.rotationPitch);
        this.setSize(0.5f, 1.9f);
        this.durability = 50;
    }

    public EntityDummy(final World world, final double d, final double d1, final double d2) {
        this(world);
        this.setPosition(d, d1, d2);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = d;
        this.prevPosY = d1;
        this.prevPosZ = d2;
    }

    @Override
    protected void entityInit() {
        this.dataManager.register(EntityDummy.TIME_SINCE_HIT, 0);
        this.dataManager.register(EntityDummy.ROCK_DIRECTION, (byte) 1);
        this.dataManager.register(EntityDummy.CURRENT_DAMAGE, 0);
    }

    @Override
    public AxisAlignedBB getCollisionBox(final Entity entity) {
        return entity.getEntityBoundingBox();
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox() {
        return this.getEntityBoundingBox();
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public boolean attackEntityFrom(final DamageSource source, final float damage) {
        if (this.world.isRemote || this.isDead || damage <= 0.0f) {
            return false;
        }
        this.setRockDirection(-this.getRockDirection());
        this.setTimeSinceHit(10);
        int i = this.getCurrentDamage();
        i += (int) (damage * 5.0f);
        if (i > 50) {
            i = 50;
        }
        this.setCurrentDamage(i);
        this.setBeenAttacked();
        if (!(source instanceof EntityDamageSource)) {
            this.durability -= (int) damage;
        } else {
            this.playRandomHitSound();
        }
        if (this.durability <= 0 && this.world.getGameRules().getBoolean("doEntityDrops")) {
            this.dropAsItem(true, true);
        }
        this.setBeenAttacked();
        return false;
    }

    public void playRandomHitSound() {
        final int i = this.rand.nextInt(2);
        if (i == 0) {
            this.playSound(SoundEvents.BLOCK_CLOTH_STEP, 0.7f, 1.0f / (this.rand.nextFloat() * 0.2f + 0.4f));
        } else {
            this.playSound(SoundEvents.BLOCK_WOOD_STEP, 0.7f, 1.0f / (this.rand.nextFloat() * 0.2f + 0.2f));
        }
    }

    @Override
    public void performHurtAnimation() {
        this.setRockDirection(-this.getRockDirection());
        this.setTimeSinceHit(10);
        this.setCurrentDamage(this.getCurrentDamage() + 10);
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        int i = this.getTimeSinceHit();
        if (i > 0) {
            this.setTimeSinceHit(i - 1);
        }
        i = this.getCurrentDamage();
        if (i > 0) {
            this.setCurrentDamage(i - this.rand.nextInt(2));
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.onGround) {
            this.motionX = 0.0;
            this.motionY = 0.0;
            this.motionZ = 0.0;
        } else {
            this.motionX *= 0.99;
            this.motionZ *= 0.99;
            this.motionY -= 0.05;
            this.fallDistance += (float) (-this.motionY);
        }
        this.setRotation(this.rotationYaw, this.rotationPitch);
        this.move(MoverType.SELF, 0.0, this.motionY, 0.0);
        final List<Entity> list = this.world.getEntitiesInAABBexcluding(this,
                this.getEntityBoundingBox().grow(0.2, 0.0, 0.2),
                EntitySelectors.getTeamCollisionPredicate(this));
        if (!list.isEmpty()) {
            for (final Entity entity : list) {
                if (!entity.isPassenger(this)) {
                    this.applyEntityCollision(entity);
                }
            }
        }
    }

    @Override
    public void fall(final float f, final float f1) {
        super.fall(f, f1);
        if (!this.onGround) {
            return;
        }
        final int i = MathHelper.floor(f);
        this.attackEntityFrom(DamageSource.FALL, (float) i);
    }

    public void dropAsItem(final boolean destroyed, final boolean noCreative) {
        if (this.world.isRemote) {
            return;
        }
        if (destroyed) {
            for (int i = 0; i < this.rand.nextInt(8); ++i) {
                this.dropItem(Items.LEATHER, 1);
            }
        } else if (noCreative) {
            this.dropItem(ReforgedAdditions.DUMMY, 1);
        }
        this.setDead();
    }

    @Override
    public boolean processInitialInteract(final EntityPlayer entityplayer, final EnumHand hand) {
        final ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if (!itemstack.isEmpty()) {
            if (itemstack.getItem() instanceof ItemSword
                    || itemstack.getItem() instanceof ItemBow || itemstack.getItem() instanceof ItemShield) {
                return false;
            }
        }
        if (entityplayer.capabilities.isCreativeMode) {
            this.dropAsItem(false, false);
            return true;
        }
        this.dropAsItem(false, true);
        return true;
    }

    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbt) {
    }

    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbt) {
        this.setPosition(this.posX, this.posY, this.posZ);
        this.setRotation(this.rotationYaw, this.rotationPitch);
    }

    public void setTimeSinceHit(final int i) {
        this.dataManager.set(EntityDummy.TIME_SINCE_HIT, i);
    }

    public void setRockDirection(final int i) {
        this.dataManager.set(EntityDummy.ROCK_DIRECTION, (byte) i);
    }

    public void setCurrentDamage(final int i) {
        this.dataManager.set(EntityDummy.CURRENT_DAMAGE, i);
    }

    public int getTimeSinceHit() {
        return this.dataManager.get(EntityDummy.TIME_SINCE_HIT);
    }

    public int getRockDirection() {
        return this.dataManager.get(EntityDummy.ROCK_DIRECTION);
    }

    public int getCurrentDamage() {
        return this.dataManager.get(EntityDummy.CURRENT_DAMAGE);
    }

}
