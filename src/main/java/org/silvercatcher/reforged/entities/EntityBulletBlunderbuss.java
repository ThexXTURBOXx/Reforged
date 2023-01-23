package org.silvercatcher.reforged.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import org.silvercatcher.reforged.api.AReforgedThrowable;
import org.silvercatcher.reforged.proxy.CommonProxy;

public class EntityBulletBlunderbuss extends AReforgedThrowable {

    public EntityBulletBlunderbuss(World worldIn) {

        super(worldIn, "blunderbuss");
    }

    public EntityBulletBlunderbuss(World worldIn, EntityLivingBase throwerIn) {

        super(worldIn, throwerIn, "blunderbuss");
        float randomNumX = rand.nextInt(21);
        float randomNumY = rand.nextInt(21);
        float randomNumZ = rand.nextInt(21);
        if (rand.nextBoolean()) {
            randomNumX = 0 - randomNumX;
        }
        if (rand.nextBoolean()) {
            randomNumY = 0 - randomNumY;
        }
        if (rand.nextBoolean()) {
            randomNumZ = 0 - randomNumZ;
        }
        this.motionX += randomNumX / 100;
        this.motionY += randomNumY / 100;
        this.motionZ += randomNumZ / 100;
        motionX *= 5;
        motionY *= 5;
        motionZ *= 5;
        setInited();
    }

    @Override
    protected float getImpactDamage(Entity target) {
        return CommonProxy.damageBlunderbuss * (((30 - ticksExisted) / 4f) + 4f);
    }

    @Override
    protected boolean onEntityHit(Entity entity) {
        entity.attackEntityFrom(causeImpactDamage(entity, getThrower()), getImpactDamage(entity));
        return true;
    }

    @Override
    public void onUpdated() {
        if (ticksExisted >= 30) {
            setDead();
        }
    }
}
