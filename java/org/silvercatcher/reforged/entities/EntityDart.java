package org.silvercatcher.reforged.entities;

import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.items.others.ItemDart;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPigZombie;
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
		setThrowerName(getThrowerIn.getName());
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		
		// id 5 = ItemStack of Dart, type 5 = ItemStack
		dataWatcher.addObjectByDataType(5, 5);
		
		// id 6 = Name of Thrower, type 4 = String
		dataWatcher.addObjectByDataType(6, 4);
	}
	
	public EntityLivingBase getThrowerASave() {
		return getEntityWorld().getPlayerEntityByName(dataWatcher.getWatchableObjectString(6));
	}
	
	public void setThrowerName(String name) {
		
		dataWatcher.updateObject(6, name);
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
					
					case "hunger": p.addPotionEffect(new PotionEffect(Potion.hunger.getId(), 300, 1)); break;
					
					case "poison": p.addPotionEffect(new PotionEffect(Potion.poison.getId(), 200, 1)); break;
					
					case "poison_strong": p.addPotionEffect(new PotionEffect(Potion.poison.getId(), 300, 2)); break;
					
					case "slowness": p.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 300, 1));
								 	 p.addPotionEffect(new PotionEffect(Potion.digSlowdown.getId(), 300, 1)); break;
								 	 
					case "wither": p.addPotionEffect(new PotionEffect(Potion.wither.getId(), 300, 1)); break;
					
					default: throw new IllegalArgumentException("No effect called " + getEffect().substring(5) + " found!");
					
					}
					
					if(p instanceof EntityPigZombie) {
						EntityPigZombie en = (EntityPigZombie) p;
						en.setRevengeTarget(getThrowerASave());
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

		tagCompound.setString("thrower", getThrowerASave().getName());
		if(getItemStack() != null) {
			tagCompound.setTag("item", getItemStack().writeToNBT(new NBTTagCompound()));
		}
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		
		super.readEntityFromNBT(tagCompund);

		setThrowerName(tagCompund.getString("thrower"));
		setItemStack(ItemStack.loadItemStackFromNBT(tagCompund.getCompoundTag("item")));
	}
}
