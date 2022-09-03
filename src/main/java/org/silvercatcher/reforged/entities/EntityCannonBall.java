package org.silvercatcher.reforged.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import org.silvercatcher.reforged.api.AReforgedThrowable;

public class EntityCannonBall extends AReforgedThrowable {

    public EntityCannonBall(World worldIn) {
        super(worldIn, "cannon");
    }

    public EntityCannonBall(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn, "cannon");
        setInited();
    }

    @Override
    protected float getImpactDamage(Entity target) {
        return 0;
    }

}
