package org.silvercatcher.reforged.entities;

import com.google.common.base.Optional;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.silvercatcher.reforged.api.AReforgedThrowable;
import org.silvercatcher.reforged.api.ReforgedAdditions;

public class EntityDart extends AReforgedThrowable {

    public static final DataParameter<Optional<ItemStack>> STACK = EntityDataManager.createKey(EntityDart.class,
            DataSerializers.OPTIONAL_ITEM_STACK);

    public EntityDart(World worldIn) {

        super(worldIn, "dart");
    }

    public EntityDart(World worldIn, EntityLivingBase getThrowerIn, ItemStack stack) {

        super(worldIn, getThrowerIn, "dart");
        setItemStack(stack);
        setInited();
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(STACK, Optional.of(new ItemStack(ReforgedAdditions.DART_NORMAL)));
    }

    public String getEffect() {
        return getItemStack().getItem().getUnlocalizedName().substring(10);
    }

    @Override
    protected float getGravityVelocity() {
        return 0.03F;
    }

    @Override
    protected float getImpactDamage(Entity target) {

        return 5f;
    }

    public ItemStack getItemStack() {

        return dataManager.get(STACK).or(new ItemStack(ReforgedAdditions.DART_NORMAL));
    }

    private Potion getPotion(String name) {
        return Potion.getPotionFromResourceLocation(name);
    }

    @Override
    protected boolean onBlockHit(BlockPos blockPos) {
        if (!world.isRemote && rand.nextInt(4) == 0 && !creativeUse()) {
            entityDropItem(new ItemStack(Items.FEATHER), 1);
        }
        return true;
    }

    @Override
    protected boolean onEntityHit(Entity entity) {
        entity.attackEntityFrom(causeImpactDamage(entity, getThrower()), getImpactDamage(entity));
        if (!entity.isDead) {
            // Still alive after first damage
            if (entity instanceof EntityLivingBase) {

                EntityLivingBase p = (EntityLivingBase) entity;

                switch (getEffect()) {

                case "normal":
                    break;

                case "hunger":
                    p.addPotionEffect(new PotionEffect(getPotion("hunger"), 300, 1));
                    break;

                case "poison":
                    p.addPotionEffect(new PotionEffect(getPotion("poison"), 200, 1));
                    break;

                case "poison_strong":
                    p.addPotionEffect(new PotionEffect(getPotion("poison"), 300, 2));
                    break;

                case "slowness":
                    p.addPotionEffect(new PotionEffect(getPotion("slowness"), 300, 1));
                    p.addPotionEffect(new PotionEffect(getPotion("mining_fatigue"), 300, 1));
                    break;

                case "wither":
                    p.addPotionEffect(new PotionEffect(getPotion("wither"), 300, 1));
                    break;

                default:
                    throw new IllegalArgumentException("No effect called " + getEffect().substring(5) + " found!");

                }
            }
        }
        return true;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund) {

        super.readEntityFromNBT(tagCompund);

        setItemStack(ItemStack.loadItemStackFromNBT(tagCompund.getCompoundTag("item")));
    }

    public void setItemStack(ItemStack stack) {

        if (stack == null || !(stack.getItem().getUnlocalizedName().contains("dart"))) {
            throw new IllegalArgumentException("Invalid Itemstack!");
        }
        dataManager.set(STACK, Optional.of(stack));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {

        super.writeEntityToNBT(tagCompound);

        if (getItemStack() != null) {
            tagCompound.setTag("item", getItemStack().writeToNBT(new NBTTagCompound()));
        }
    }
}
