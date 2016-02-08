package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.ReforgedReferences.GlobalValues;
import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.items.weapons.ItemBoomerang;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.LanguageRegistry;

public class EntityBoomerang extends ReforgedThrowable {
	
	public EntityBoomerang(World worldIn) {
		
		super(worldIn);
	}
	
	public EntityBoomerang(World worldIn, EntityLivingBase getThrowerIn, ItemStack stack) {
		
		super(worldIn, getThrowerIn, stack);
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
	
	public double getCoord(String coordId) {
		switch(coordId) {
		case "X": return dataWatcher.getWatchableObjectFloat(7);
		case "Y": return dataWatcher.getWatchableObjectFloat(8);
		case "Z": return dataWatcher.getWatchableObjectFloat(9);
		default: throw new IllegalArgumentException("Invalid coordId!");
		}
	}
	
	public ToolMaterial getMaterial() {

		return ((ItemBoomerang) getItemStack().getItem()).getMaterial();
	}

	private float getImpactDamage() {
		
		return getMaterial().getDamageVsEntity()  + 3;
	}
	
	@Override
	public void onUpdate() {
		
			super.onUpdate();
			double dx = this.posX - getCoord("X");
			double dy = this.posY - getCoord("Y");
			double dz = this.posZ - getCoord("Z");
			double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
			dx /= d;
			dy /= d;
			dz /= d;
			
			motionX -= 0.05D * dx;
			motionY -= 0.05D * dy;
			motionZ -= 0.05D * dz;
			
			int distance = GlobalValues.DISTANCE_BOOMERANG;
			double px = getCoord("X");
			double py = getCoord("Y");
			double pz = getCoord("Z");
			if(getThrowerASave() != null) {
				px = getThrowerASave().posX;
				py = getThrowerASave().posY;
				pz = getThrowerASave().posZ;				
			}
			if(this.ticksExisted >= 100 || isInWater()) {
				if(!worldObj.isRemote) {
					if(Math.abs(posX - px) <= distance && Math.abs(posY - py) <= distance && Math.abs(posZ - pz) <= distance) {
						if(getItemStack().getMaxDamage() - getItemStack().getItemDamage() > 0) {
							EntityPlayer p = (EntityPlayer) getThrowerASave();
							p.inventory.addItemStackToInventory(getItemStack());
						} else {
							//Custom sound later... [BREAK SOUND]
						}
						this.setDead();
					} else {
						if(getItemStack().getMaxDamage() - getItemStack().getItemDamage() > 0) {
							this.entityDropItem(getItemStack(), 0);
						} else {
							//Custom sound later... [BREAK SOUND]
						}
						this.setDead();			
					}
				}
			}
	}
	
	@Override
	protected float getGravityVelocity() {
		return 0.0F;
	}

	@Override
	protected void onImpact(MovingObjectPosition target) {
		super.onImpact(target);
		
		//Target is entity or block?
		if(target.entityHit == null) {
			//It's a block
			//Distance specifies the range the boomerang should get auto-collected
			int distance = GlobalValues.DISTANCE_BOOMERANG;
			this.setDead();
			double px = getCoord("X");
			double py = getCoord("Y");
			double pz = getCoord("Z");
			if(getThrowerASave() != null) {
				px = getThrowerASave().posX;
				py = getThrowerASave().posY;
				pz = getThrowerASave().posZ;				
			}
			if(!worldObj.isRemote && Math.abs(px - posX) <= distance && Math.abs(py - posY) <= distance && Math.abs(pz - posZ) <= distance) {
				if(getItemStack().getMaxDamage() - getItemStack().getItemDamage() > 0) {
					EntityPlayer p = (EntityPlayer) getThrowerASave();
					p.inventory.addItemStackToInventory(getItemStack());
				} else {
					EntityPlayer p = (EntityPlayer) getThrowerASave();
					if(p.getHealth() <= 2.0F) {
						p.attackEntityFrom(ReforgedRegistry.boomerangBreakDamage, 20);
					} else {
						p.attackEntityFrom(ReforgedRegistry.boomerangBreakDamage, 2);
						p.addChatMessage(new ChatComponentText(LanguageRegistry.instance().getStringLocalization("item.boomerang.langBreak").replace("%1$s",getItemStack().getDisplayName())));
					}
				}
			} else if(!worldObj.isRemote) {
				if(getItemStack().getMaxDamage() - getItemStack().getItemDamage() > 0) {
					entityDropItem(getItemStack(), 0.5f);
				} else {
					//Custom sound later... [BREAK SOUND]
				}
			}
		} else {
			//It's an entity
			if(target.entityHit != getThrowerASave()) {
				//It's an hit entity
				target.entityHit.attackEntityFrom(ReforgedRegistry.boomerangHitDamage, getImpactDamage());
				ItemStack stack = getItemStack();
				if(stack.attemptDamageItem(1, rand)) {
					this.setDead();
				} else {
					setItemStack(stack);
				}
			} else {
				//It's the thrower himself
				this.setDead();
				ItemStack stack = getItemStack();
				EntityPlayer p = (EntityPlayer) target.entityHit;
				if(stack.getMaxDamage() - stack.getItemDamage() > 0) {
					p.inventory.addItemStackToInventory(stack);
				} else {
					//Custom sound later... [BREAK SOUND]
				}
			}
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		
		super.writeEntityToNBT(tagCompound);
		
		tagCompound.setDouble("playerX", getCoord("X"));
		tagCompound.setDouble("playerY", getCoord("Y"));
		tagCompound.setDouble("playerZ", getCoord("Z"));
		
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
}
