package org.silvercatcher.reforged.entities;

import java.util.Random;

import javax.tools.Tool;

import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.items.CompoundTags;
import org.silvercatcher.reforged.items.ReforgedItem;
import org.silvercatcher.reforged.items.weapons.ItemBoomerang;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class EntityBoomerang extends EntityThrowable {
	
	private ToolMaterial material;
	private int itemDamage;
	private Random r;

	public EntityBoomerang(World worldIn) {
		super(worldIn);
	}
	
	public EntityBoomerang(World worldIn, EntityLivingBase throwerIn, ItemStack stack) {
		
		super(worldIn, throwerIn);
		
		if(stack.getItem() instanceof ItemBoomerang) {
			this.material = ((ItemBoomerang) stack.getItem()).getMaterial();
			this.itemDamage = stack.getItemDamage();
		} else {
			throw new IllegalArgumentException("Cannot create boomerang entity from Item: " + stack.getItem());
		}
	}
	
	public ToolMaterial getMaterial() {
		
		return material;
	}
	
	public int getItemDamage(ToolMaterial material) {
		
		switch(material) {
		
		case EMERALD: return 10;
		
		case GOLD: return 6;
		
		case IRON: return 6;
		
		case STONE: return 4;
		
		case WOOD: return 2;
		
		default: return 0;
		
		}
	}
	
	private float getImpactDamage() {
		return 4;
	}

	@Override
	protected void onImpact(MovingObjectPosition target) {
	
		//Target is entity or block?
		if(target.entityHit == null) {
			//It's a block
		} else {
			//It's a entity
			target.entityHit.attackEntityFrom(DamageSource.causeThornsDamage(getThrower()), getImpactDamage());
		}
		
		if(!worldObj.isRemote) {
			Item item;
			ItemStack dropStack;
			switch(material) {
			case EMERALD:
				item = ReforgedRegistry.DIAMOND_BOOMERANG;
				playSound("random.pop", 0.5F, 0.4F);
				dropStack = new ItemStack(item);
				dropStack.setItemDamage(itemDamage);
				entityDropItem(dropStack, 0.5f);
				setDead();
				break;
			case GOLD:
				item = ReforgedRegistry.GOLDEN_BOOMERANG;
				playSound("random.pop", 0.5F, 0.4F);
				dropStack = new ItemStack(item);
				dropStack.setItemDamage(itemDamage);
				entityDropItem(dropStack, 0.5f);
				setDead();
				break;
			case IRON:
				item = ReforgedRegistry.IRON_BOOMERANG;
				playSound("random.pop", 0.5F, 0.4F);
				dropStack = new ItemStack(item);
				dropStack.setItemDamage(itemDamage);
				entityDropItem(dropStack, 0.5f);
				setDead();
				break;
			case STONE:
				item = ReforgedRegistry.STONE_BOOMERANG;
				playSound("random.pop", 0.5F, 0.4F);
				dropStack = new ItemStack(item);
				dropStack.setItemDamage(itemDamage);
				entityDropItem(dropStack, 0.5f);
				setDead();
				break;
			case WOOD:
				item = ReforgedRegistry.WOODEN_BOOMERANG;
				playSound("random.pop", 0.5F, 0.4F);
				dropStack = new ItemStack(item);
				dropStack.setItemDamage(itemDamage);
				entityDropItem(dropStack, 0.5f);
				setDead();
				break;
			default: item = null;
				break;
			
			}
		}
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
