package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.ReforgedReferences.GlobalValues;
import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.items.weapons.ItemBoomerang;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.LanguageRegistry;

public class EntityBoomerang extends AReforgedThrowable {
	
	public EntityBoomerang(World worldIn) {
		
		super(worldIn, "boomerang");
	}
	
	public EntityBoomerang(World worldIn, EntityLivingBase getThrowerIn, ItemStack stack) {
		
		super(worldIn, getThrowerIn, stack, "boomerang");
		setItemStack(stack);
		setCoords(getThrowerIn.posX, getThrowerIn.posY + getThrowerIn.getEyeHeight(), getThrowerIn.posZ);
	}
	
	@Override
	protected void entityInit() {
		
		super.entityInit();
		
		// id 6 = ItemStack of Boomerang, type 5 = ItemStack
		dataWatcher.addObjectByDataType(6, 5);
		
		// id 7 = posX, type 3 = float
		dataWatcher.addObjectByDataType(7, 3);
		
		// id 8 = posY, type 3 = float
		dataWatcher.addObjectByDataType(8, 3);
		
		// id 9 = posZ, type 3 = float
		dataWatcher.addObjectByDataType(9, 3);
	}

	public ItemStack getItemStack() {
		
		return dataWatcher.getWatchableObjectItemStack(6);
	}
	
	public void setItemStack(ItemStack stack) {
		
		if(stack == null || !(stack.getItem() instanceof ItemBoomerang)) {
			throw new IllegalArgumentException("Invalid Itemstack!");
		}
		dataWatcher.updateObject(6, stack);
	}
	
	public void setCoords(double playerX, double playerY, double playerZ) {
		
		dataWatcher.updateObject(7, (float) playerX);
		dataWatcher.updateObject(8, (float) playerY);
		dataWatcher.updateObject(9, (float) playerZ);
	}
	
	public double getPosX() {
		return dataWatcher.getWatchableObjectFloat(7);
	}
	
	public double getPosY() {
		return dataWatcher.getWatchableObjectFloat(8);
	}
	
	public double getPosZ() {
		return dataWatcher.getWatchableObjectFloat(9);
	}
	
	public ToolMaterial getMaterial() {

		return ((ItemBoomerang) getItemStack().getItem()).getMaterial();
	}
	
	@Override
	public void onUpdate() {
		
		super.onUpdate();
		
		double dx = this.posX - getPosX();
		double dy = this.posY - getPosY();
		double dz = this.posZ - getPosZ();
		double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
		
		dx /= d;
		dy /= d;
		dz /= d;
		
		motionX -= 0.05D * dx;
		motionY -= 0.05D * dy;
		motionZ -= 0.05D * dz;
		
		//After 103 ticks, the Boomerang drops exactly where the thrower stood
		if(ticksExisted >= 103 || isInWater()) {
			if(!worldObj.isRemote) {
				if(getItemStack().getMaxDamage() - getItemStack().getItemDamage() > 0) {
					entityDropItem(getItemStack(), 0.5f);
				} else {
					//Custom sound later... [BREAK SOUND]
				}
				setDead();
			}
		}
	}
	
	@Override
	protected float getGravityVelocity() {
		return 0f;
	}

	@Override
	protected boolean onBlockHit(BlockPos blockPos) {
		
		double px = getPosX();
		double py = getPosY();
		double pz = getPosZ();
		
		if(!worldObj.isRemote) {
			if(getItemStack().getMaxDamage() - getItemStack().getItemDamage() > 0) {
				entityDropItem(getItemStack(), 0.5f);
			}
		}
		return true;
	}
	
	@Override
	protected boolean onEntityHit(Entity hitEntity) {
		if(hitEntity == getThrower()) {
			
			//It's the thrower himself
			ItemStack stack = getItemStack();
			EntityPlayer p = (EntityPlayer) hitEntity;
			if(stack.getMaxDamage() > stack.getItemDamage()) {
				p.inventory.addItemStackToInventory(stack);
			} else {
				//Custom sound later... [BREAK SOUND]
			}
			return true;
		} else {
			//It's an hit entity
			hitEntity.attackEntityFrom(causeImpactDamage(hitEntity, getThrower()), getImpactDamage(hitEntity));
			ItemStack stack = getItemStack();
			
			if(stack.attemptDamageItem(1, rand)) {
				return true;
			} else {
				setItemStack(stack);
			}
		}
		return false;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		
		super.writeEntityToNBT(tagCompound);
		
		tagCompound.setDouble("playerX", getPosX());
		tagCompound.setDouble("playerY", getPosY());
		tagCompound.setDouble("playerZ", getPosZ());
		
		if(getItemStack() != null) {
			tagCompound.setTag("item", getItemStack().writeToNBT(new NBTTagCompound()));
		}
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		
		super.readEntityFromNBT(tagCompund);
		setItemStack(ItemStack.loadItemStackFromNBT(tagCompund.getCompoundTag("item")));
		setCoords(tagCompund.getDouble("playerX"), tagCompund.getDouble("playerY"), tagCompund.getDouble("playerZ"));
	}

	@Override
	protected float getImpactDamage(Entity target) {
		return getMaterial().getDamageVsEntity() + 5;
	}
}
