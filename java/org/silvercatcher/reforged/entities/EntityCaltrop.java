package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.util.Helpers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class EntityCaltrop extends AReforgedThrowable {
	
	private boolean falling = true;
	
	public EntityCaltrop(World worldIn) {
		super(worldIn, "caltrop");
	}
	
	public EntityCaltrop(World worldIn, EntityLivingBase throwerIn, ItemStack stack, BlockPos pos, EnumFacing side) {
		super(worldIn, throwerIn, stack, "caltrop");
		motionX = 0;
		motionY = -0.2;
		motionZ = 0;
		BlockPos newpos = pos.offset(side);
		setPositionAndUpdate(newpos.getX() + 0.5, newpos.getY() + 0.5, newpos.getZ() + 0.5);
	}
	
	public ItemStack getItemStack() {
		
		return new ItemStack(ReforgedAdditions.CALTROP);
	}
	
	@Override
	public void onUpdate() {
		if(falling) {
			super.onUpdate();
		} else {
			BlockPos p = new BlockPos(getPosition().getX(), getPosition().getY() - 1, getPosition().getZ());
			if(worldObj.getBlockState(p).getBlock() == Blocks.air) {
				falling = true;
			}
		}
		BlockPos newpos1 = new BlockPos(getPosition().getX() - 1, getPosition().getY() - 1, getPosition().getZ() - 1);
		BlockPos newpos2 = new BlockPos(getPosition().getX() + 1, getPosition().getY() + 1, getPosition().getZ() + 1);
		Entity nearest = worldObj.findNearestEntityWithinAABB(EntityLivingBase.class, new AxisAlignedBB(newpos1, newpos2), this);
		if(nearest != null && Helpers.blockPosEqual(getPosition(), nearest.getPosition())) {
			onEntityHit(nearest);
			setDead();
		}
	}
	
	@Override
	protected boolean onBlockHit(BlockPos blockPos) {
		falling = false;
		return false;
	}
	
	@Override
	protected boolean onEntityHit(Entity entity) {
		if(entity != getThrower()) {
			entity.attackEntityFrom(causeImpactDamage(entity, getThrower()), getImpactDamage(entity));
		} else {
			if(!creativeUse()) {
				worldObj.playSoundAtEntity(this, "random.pop", 0.5F, 0.7F);
				((EntityPlayer) getThrower()).inventory.addItemStackToInventory(getItemStack());
			}
		}
		return true;
	}
	
	@Override
	protected float getImpactDamage(Entity target) {
		return (ToolMaterial.IRON.getDamageVsEntity() * 4);
	}
	
}