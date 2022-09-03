package org.silvercatcher.reforged.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.silvercatcher.reforged.api.AReforgedThrowable;

public class EntityCannonBall extends AReforgedThrowable {

    public EntityCannonBall(World worldIn) {
        super(worldIn, "cannon");
    }

    public EntityCannonBall(World worldIn, EntityLivingBase throwerIn, ItemStack stack) {
        super(worldIn, throwerIn, stack, "cannon");
        setInited();
    }

    @Override
    protected float getImpactDamage(Entity target) {
        return 0;
    }

}
