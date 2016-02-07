package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.items.others.ItemDart;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityDart extends EntityThrowable {
	
	public EntityDart(World worldIn) {
		
		super(worldIn);
	}
	
	public EntityDart(World worldIn, EntityLivingBase getThrowerIn, ItemStack stack) {
		
		super(worldIn, getThrowerIn);
		setItemStack(stack);
		this.setPositionAndRotation(getThrowerIn.posX, getThrowerIn.posY + getThrowerIn.getEyeHeight(), getThrowerIn.posZ, getThrowerIn.rotationYaw, getThrowerIn.rotationPitch);
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
	protected void onImpact(MovingObjectPosition target) {

		//Target is entity or block?
		if(target.entityHit == null) {
			//It's a block
			if(!worldObj.isRemote && rand.nextInt(4) == 0) {
				entityDropItem(new ItemStack(Items.feather), 1);	
			}
		} else {
			//It's an entity
			target.entityHit.attackEntityFrom(ReforgedRegistry.dartDamage, 5);
			if(!target.entityHit.isDead) {
				// Still alive after first damage
				if(target.entityHit instanceof EntityLivingBase) {

					EntityLivingBase p = (EntityLivingBase) target.entityHit;
				
					switch(getEffect()) {
					
					case "normal": break;
					
					case "hunger": p.addPotionEffect(new PotionEffect(Potion.hunger.getId(), 400)); break;
					
					case "poison": p.addPotionEffect(new PotionEffect(Potion.poison.getId(), 300)); break;
					
					case "poison_strong": p.addPotionEffect(new PotionEffect(Potion.poison.getId(), 400, 1)); break;
					
					case "slowness": p.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 400));
								 	 p.addPotionEffect(new PotionEffect(Potion.digSlowdown.getId(), 400)); break;
								 	 
					case "wither": p.addPotionEffect(new PotionEffect(Potion.wither.getId(), 300)); break;
					
					default: throw new IllegalArgumentException("No effect called " + getEffect().substring(5) + " found!");
					
					}
				}
			}
		}
		//Custom sound later... [BREAK SOUND]
		this.setDead();
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
}
