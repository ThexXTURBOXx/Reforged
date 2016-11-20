package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.api.AReforgedThrowable;
import org.silvercatcher.reforged.items.others.ItemDart;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityDart extends AReforgedThrowable {
	
	public EntityDart(World world) {
		
		super(world, "dart");
	}
	
	public EntityDart(World world, EntityLivingBase thrower, ItemStack stack) {
		
		super(world, thrower, stack, "dart");
		setItemStack(stack);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		
		// id 5 = ItemStack of Dart, type 5 = ItemStack
		dataWatcher.addObjectByDataType(5, 5);
	}

	public ItemStack getItemStack() {
		
		return dataWatcher.getWatchableObjectItemStack(5);
	}
	
	public void setItemStack(ItemStack stack) {
		
		if(stack == null || !(stack.getItem().getUnlocalizedName().contains("dart"))) {
			throw new IllegalArgumentException("Invalid Itemstack!");
		}
		dataWatcher.updateObject(5, stack);
	}
	
	public String getEffect() {
		return ((ItemDart) getItemStack().getItem()).getUnlocalizedName().substring(10);
	}
	
	@Override
	protected boolean onBlockHit(BlockPos blockPos) {
		if(!worldObj.isRemote && rand.nextInt(4) == 0 && !creativeUse()) {
			entityDropItem(new ItemStack(Items.feather), 1);	
		}
		return true;
	}
	
	@Override
	protected boolean onEntityHit(Entity entity) {
		entity.attackEntityFrom(causeImpactDamage(entity, getThrower()), getImpactDamage(entity));
		if(!entity.isDead) {
			// Still alive after first damage
			if(entity instanceof EntityLivingBase) {
				
				EntityLivingBase p = (EntityLivingBase) entity;
				
				switch(getEffect()) {
				
				case "normal": break;
				
				case "hunger": p.addPotionEffect(new PotionEffect(Potion.hunger.getId(), 300, 1)); break;
				
				case "poison": p.addPotionEffect(new PotionEffect(Potion.poison.getId(), 200, 1)); break;
				
				case "poison_strong": p.addPotionEffect(new PotionEffect(Potion.poison.getId(), 300, 2)); break;
				
				case "slowness": p.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 300, 1));
				p.addPotionEffect(new PotionEffect(Potion.digSlowdown.getId(), 300, 1)); break;
				
				case "wither": p.addPotionEffect(new PotionEffect(Potion.wither.getId(), 300, 1)); break;
				
				default: throw new IllegalArgumentException("No effect called " + getEffect().substring(5) + " found!");
				
				}
			}
		}
		worldObj.playSoundAtEntity(this, "reforged:boomerang_break", 1.0F, 1.0F);
		return true;
	}
	
	@Override
	protected float getGravityVelocity() {
		return 0.03F;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		
		super.writeEntityToNBT(tagCompound);
		
		if(getItemStack() != null) {
			tagCompound.setTag("item", getItemStack().writeToNBT(new NBTTagCompound()));
		}
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		
		super.readEntityFromNBT(tagCompund);
		
		setItemStack(ItemStack.loadItemStackFromNBT(tagCompund.getCompoundTag("item")));
	}

	@Override
	protected float getImpactDamage(Entity target) {

		return 5f;
	}
}
