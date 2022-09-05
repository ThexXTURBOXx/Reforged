package org.silvercatcher.reforged.entities;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.silvercatcher.reforged.api.ReforgedAdditions;

public class EntityCrossbowBolt extends EntityArrow {
    private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(EntityCrossbowBolt.class,
            DataSerializers.VARINT);

    private PotionType potion = PotionTypes.EMPTY;
    private final Set<PotionEffect> customPotionEffects = Sets.newHashSet();

    public EntityCrossbowBolt(World worldIn) {
        super(worldIn);
    }

    public EntityCrossbowBolt(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public EntityCrossbowBolt(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
    }

    public void addEffect(PotionEffect effect) {
        this.customPotionEffects.add(effect);
        this.getDataManager().set(COLOR, PotionUtils
                .getPotionColorFromEffectList(PotionUtils.mergeEffects(this.potion, this.customPotionEffects)));
    }

    @Override
    protected void arrowHit(EntityLivingBase living) {
        super.arrowHit(living);

        for (PotionEffect potioneffect : this.potion.getEffects()) {
            living.addPotionEffect(new PotionEffect(potioneffect.getPotion(), potioneffect.getDuration() / 8,
                    potioneffect.getAmplifier(), potioneffect.getIsAmbient(), potioneffect.doesShowParticles()));
        }

        if (!this.customPotionEffects.isEmpty()) {
            for (PotionEffect potioneffect : this.customPotionEffects) {
                living.addPotionEffect(potioneffect);
            }
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(COLOR, -1);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ReforgedAdditions.CROSSBOW_BOLT);
    }

    public int getColor() {
        return this.dataManager.get(COLOR);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 0) {
            int i = this.getColor();

            if (i != -1) {
                double d0 = (i >> 16 & 255) / 255.0D;
                double d1 = (i >> 8 & 255) / 255.0D;
                double d2 = (i & 255) / 255.0D;

                for (int j = 0; j < 20; ++j) {
                    this.worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB,
                            this.posX + (this.rand.nextDouble() - 0.5D) * this.width,
                            this.posY + this.rand.nextDouble() * this.height,
                            this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, d0, d1, d2);
                }
            }
        } else {
            super.handleStatusUpdate(id);
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.worldObj.isRemote) {
            if (this.inGround) {
                if (this.timeInGround % 5 == 0) {
                    this.spawnPotionParticles(1);
                }
            } else {
                this.spawnPotionParticles(2);
            }
        } else if (this.inGround && this.timeInGround != 0 && !this.customPotionEffects.isEmpty()
                && this.timeInGround >= 600) {
            this.worldObj.setEntityState(this, (byte) 0);
            this.potion = PotionTypes.EMPTY;
            this.customPotionEffects.clear();
            this.dataManager.set(COLOR, -1);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        if (compound.hasKey("Potion", 8)) {
            this.potion = PotionUtils.getPotionTypeFromNBT(compound);
        }

        for (PotionEffect potioneffect : PotionUtils.getFullEffectsFromTag(compound)) {
            this.addEffect(potioneffect);
        }

        if (this.potion != PotionTypes.EMPTY || !this.customPotionEffects.isEmpty()) {
            this.dataManager.set(COLOR, PotionUtils.getPotionColorFromEffectList(
                    PotionUtils.mergeEffects(this.potion, this.customPotionEffects)));
        }
    }

    public void setPotionEffect(ItemStack stack) {
        if (stack.getItem() == Items.TIPPED_ARROW) {
            this.potion = PotionUtils.getPotionTypeFromNBT(stack.getTagCompound());
            Collection<PotionEffect> collection = PotionUtils.getFullEffectsFromItem(stack);

            if (!collection.isEmpty()) {
                for (PotionEffect potioneffect : collection) {
                    this.customPotionEffects.add(new PotionEffect(potioneffect));
                }
            }

            this.dataManager.set(COLOR, PotionUtils.getPotionColorFromEffectList(
                    PotionUtils.mergeEffects(this.potion, collection)));
        } else if (stack.getItem() == Items.ARROW) {
            this.potion = PotionTypes.EMPTY;
            this.customPotionEffects.clear();
            this.dataManager.set(COLOR, -1);
        }
    }

    private void spawnPotionParticles(int particleCount) {
        int i = this.getColor();

        if (i != -1 && particleCount > 0) {
            double d0 = (i >> 16 & 255) / 255.0D;
            double d1 = (i >> 8 & 255) / 255.0D;
            double d2 = (i & 255) / 255.0D;

            for (int j = 0; j < particleCount; ++j) {
                this.worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB,
                        this.posX + (this.rand.nextDouble() - 0.5D) * this.width,
                        this.posY + this.rand.nextDouble() * this.height,
                        this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, d0, d1, d2);
            }
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        if (this.potion != PotionTypes.EMPTY && this.potion != null) {
            compound.setString("Potion", PotionType.REGISTRY.getNameForObject(this.potion).toString());
        }

        if (!this.customPotionEffects.isEmpty()) {
            NBTTagList nbttaglist = new NBTTagList();

            for (PotionEffect potioneffect : this.customPotionEffects) {
                nbttaglist.appendTag(potioneffect.writeCustomPotionEffectToNBT(new NBTTagCompound()));
            }

            compound.setTag("CustomPotionEffects", nbttaglist);
        }
    }

}
