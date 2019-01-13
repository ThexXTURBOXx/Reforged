package org.silvercatcher.reforged.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.silvercatcher.reforged.api.AReforgedThrowable;
import org.silvercatcher.reforged.api.ReforgedAdditions;

public class EntityCannonBall extends AReforgedThrowable {

	public static final String NAME = "cannon";

	public EntityCannonBall(World worldIn) {
		super(ReforgedAdditions.ENTITY_CANNON_BALL, worldIn, NAME);
	}

	public EntityCannonBall(World worldIn, EntityLivingBase throwerIn, ItemStack stack) {
		super(ReforgedAdditions.ENTITY_CANNON_BALL, worldIn, throwerIn, stack, NAME);
		setInited();
	}

	@Override
	protected float getImpactDamage(Entity target) {
		return 0;
	}

	@Override
	public void deserializeNBT(INBTBase nbt) {
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
	}

}