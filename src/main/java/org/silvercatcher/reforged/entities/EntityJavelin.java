package org.silvercatcher.reforged.entities;

import com.google.common.base.Optional;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.silvercatcher.reforged.api.AReforgedThrowable;
import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.items.weapons.ItemJavelin;
import org.silvercatcher.reforged.util.Helpers;

public class EntityJavelin extends AReforgedThrowable {

    public static final DataParameter<Optional<ItemStack>> STACK = EntityDataManager.createKey(EntityJavelin.class,
            DataSerializers.OPTIONAL_ITEM_STACK);
    public static final DataParameter<Integer> DURATION = EntityDataManager.createKey(EntityJavelin.class,
            DataSerializers.VARINT);

    public EntityJavelin(World worldIn) {

        super(worldIn, "javelin");
    }

    public EntityJavelin(World worldIn, EntityLivingBase throwerIn, ItemStack stack, int durationLoaded) {

        super(worldIn, throwerIn, "javelin");

        setItemStack(stack);
        setDurLoaded(durationLoaded);

        if (durationLoaded < 20) {
            durationLoaded = 20;
        } else if (durationLoaded > 40) {
            durationLoaded = 40;
        }
        this.motionX *= (durationLoaded / 20d);
        this.motionY *= (durationLoaded / 20d);
        this.motionZ *= (durationLoaded / 20d);
        setInited();
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(STACK, Optional.of(new ItemStack(ReforgedAdditions.JAVELIN)));
        dataManager.register(DURATION, 1);
    }

    public int getDurLoaded() {
        return dataManager.get(DURATION);
    }

    @Override
    protected float getGravityVelocity() {
        return 0.03F;
    }

    @Override
    protected float getImpactDamage(Entity target) {

        return 5 + getDurLoaded() / 10f;
    }

    public ItemStack getItemStack() {
        return dataManager.get(STACK).or(new ItemStack(ReforgedAdditions.JAVELIN));
    }

    @Override
    protected boolean onBlockHit(BlockPos blockPos) {
        ItemStack stack = getItemStack();
        if (!stack.attemptDamageItem(1, rand)) {
            setItemStack(stack);
        }
        if (getItemStack().getMaxDamage() - getItemStack().getItemDamage() > 0) {
            if (!creativeUse()) {
                entityDropItem(getItemStack(), 0.5f);
            }
        } else {
            Helpers.playSound(worldObj, this, "boomerang_break", 1.0F, 1.0F);
        }
        return true;
    }

    @Override
    protected boolean onEntityHit(Entity entity) {
        entity.attackEntityFrom(causeImpactDamage(entity, getThrower()), getImpactDamage(entity));
        ItemStack stack = getItemStack();
        if (!stack.attemptDamageItem(1, rand)) {
            setItemStack(stack);
        }
        if (getItemStack().getMaxDamage() - getItemStack().getItemDamage() > 0) {
            if (!creativeUse()) {
                entityDropItem(getItemStack(), 0.5f);
            }
        } else {
            Helpers.playSound(worldObj, this, "boomerang_break", 1.0F, 1.0F);
        }
        return true;
    }

    @Override
    public void onUpdate() {
        if (!isDead)
            super.onUpdate();
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund) {

        super.readEntityFromNBT(tagCompund);
        setDurLoaded(tagCompund.getInteger("durloaded"));
        setItemStack(ItemStack.loadItemStackFromNBT(tagCompund.getCompoundTag("item")));
    }

    public void setDurLoaded(int durloaded) {

        dataManager.set(DURATION, durloaded);
    }

    public void setItemStack(ItemStack stack) {

        if (stack == null || !(stack.getItem() instanceof ItemJavelin)) {
            throw new IllegalArgumentException("Invalid Itemstack!");
        }
        dataManager.set(STACK, Optional.of(stack));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {

        super.writeEntityToNBT(tagCompound);

        tagCompound.setInteger("durloaded", getDurLoaded());

        if (getItemStack() != null) {
            tagCompound.setTag("item", getItemStack().writeToNBT(new NBTTagCompound()));
        }
    }
}
