package org.silvercatcher.reforged.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import org.silvercatcher.reforged.api.AReforgedThrowable;
import org.silvercatcher.reforged.proxy.CommonProxy;

public class EntityBulletMusket extends AReforgedThrowable {

    public EntityBulletMusket(World worldIn) {
        super(worldIn, "musket");
        setNoGravity(true);
    }

    public EntityBulletMusket(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn, "musket");
        setNoGravity(true);
        motionX *= 5;
        motionY *= 5;
        motionZ *= 5;
        setInited();
    }

    @Override
    protected float getImpactDamage(Entity target) {
        return CommonProxy.damageMusket;
    }

    @Override
    protected boolean onEntityHit(Entity entity) {
        entity.attackEntityFrom(causeImpactDamage(entity, getThrower()), getImpactDamage(entity));
        return true;
    }

}
