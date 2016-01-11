package org.silvercatcher.reforged.entities;

import javax.tools.Tool;

import org.silvercatcher.reforged.ReforgedItems;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.items.CompoundTags;
import org.silvercatcher.reforged.items.ReforgedItem;
import org.silvercatcher.reforged.items.weapons.ItemBoomerang;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class EntityBoomerang extends EntityThrowable {
	
	private ToolMaterial material;
	private int itemDamage;

	public EntityBoomerang(World worldIn) {
		super(worldIn);
	}
	
	public EntityBoomerang(World worldIn, EntityLivingBase throwerIn, ItemStack stack) {
		
		super(worldIn, throwerIn);
		
		if(stack.getItem() instanceof ItemBoomerang) {
			this.material = ((ItemBoomerang) stack.getItem()).getMaterial();
			this.itemDamage = stack.getItemDamage();
		} else {
			throw new IllegalArgumentException("Cannot created boomerang entity from Item: " + stack.getItem());
		}
	}
	
	public ToolMaterial getMaterial() {
		
		return material;
	}
	
	public int getItemDamage() {
		
		return itemDamage;
	}

	@Override
	protected void onImpact(MovingObjectPosition target) {
	
		if(target.entityHit == null) {
			// let's see
		} else {
			
			target.entityHit.attackEntityFrom(DamageSource.causeThornsDamage(getThrower()), getImpactDamage());
		}
		
		if(!worldObj.isRemote) {
			
			playSound("random.pop", 0.5F, 0.4F);
			Item item = null;
			switch(material) {
			case EMERALD: item = ReforgedItems.DIAMOND_BOOMERANG;
				break;
			case GOLD: item = ReforgedItems.GOLDEN_BOOMERANG;
				break;
			case IRON: item = ReforgedItems.IRON_BOOMERANG;
				break;
			case STONE: item = ReforgedItems.STONE_BOOMERANG;
				break;
			case WOOD: item = ReforgedItems.WOODEN_BOOMERANG;
				break;
			default:
				break;
			
			}
			ItemStack dropStack = new ItemStack(item);
			dropStack.setItemDamage(itemDamage);
			entityDropItem(dropStack, 0.5f);
			setDead();
		}
	}
	
	private float getImpactDamage() {
		return 4;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		
		super.writeEntityToNBT(tagCompound);
		
		tagCompound.setString(CompoundTags.ITEM_MATERIAL, material.name());
		tagCompound.setInteger(CompoundTags.ITEM_DAMAGE, itemDamage);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		
		material = ToolMaterial.valueOf(tagCompund.getString(CompoundTags.ITEM_MATERIAL));
		itemDamage = tagCompund.getInteger(CompoundTags.ITEM_DAMAGE);
	}
}
