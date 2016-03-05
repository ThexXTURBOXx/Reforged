package org.silvercatcher.reforged.items.weapons;

import java.util.Arrays;
import java.util.UUID;

import org.apache.logging.log4j.core.helpers.UUIDUtil;
import org.silvercatcher.reforged.entities.ai.EntityAIDefendNecromancer;
import org.silvercatcher.reforged.entities.ai.EntityAIFollowNecromancer;
import org.silvercatcher.reforged.items.CompoundTags;
import org.silvercatcher.reforged.items.ExtendedItem;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIControlledByPlayer;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class ItemNecromancersStaff extends ExtendedItem {

	// very specific compound tag for keeping track of slaves, just keep it here
	private static final String SLAVE_TAG = "slaves";
	private static final MinecraftServer server = MinecraftServer.getServer();
	
	public ItemNecromancersStaff() {
		
		System.out.println(server);
		setUnlocalizedName("necromancers_staff");
	}
	
	//just for testing something...
	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		
		System.out.println(book);
		
		return true;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
				
		if(entity instanceof EntityCreature) {
			
			EntityCreature creature = (EntityCreature) entity;
			
			NBTTagCompound compound = CompoundTags.giveCompound(stack);
			
			NBTTagList slaveNames = new NBTTagList();
			
			if(compound.hasKey(SLAVE_TAG)) {
				slaveNames = compound.getTagList(SLAVE_TAG, 9);
			} else {
				slaveNames = new NBTTagList();
				compound.setTag(SLAVE_TAG, slaveNames);
			}
			
			if(isSlave(slaveNames, creature)) {
				// some reaction
			} else {
				enslave(slaveNames, creature);
			}
			
			// safe changes
			compound.setTag(SLAVE_TAG, slaveNames);
			
			String s = "";
			for(int i = 0; i < slaveNames.tagCount(); i++) {
				s += slaveNames.getStringTagAt(i);
			}
			System.out.println(s);
		}
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		
		playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));

		NBTTagCompound compound = CompoundTags.giveCompound(itemStackIn);
		
		NBTTagList slaveNames = compound.getTagList(SLAVE_TAG, 9);
		
		System.out.println(slaveNames.tagCount());
		
		for(int i = 0; i < slaveNames.tagCount(); i++) {
			
			// only EntityCreatures are added, so cast should be safe
			EntityCreature slave = (EntityCreature) server.getEntityFromUuid(
					UUID.fromString(slaveNames.getStringTagAt(i)));
			
			System.out.println(slave);
			
			// throw invalid entities out
			if(slave == null || !slave.isEntityAlive()) {
				
				slaveNames.removeTag(i);
				System.out.println("Removed invalid Entity with ID: " + slave);
				break;
			}
			
			//System.out.println("Calling slave: " + slave);
			// call the slave to the holder of this staff
			PathNavigate navigate = slave.getNavigator();
			navigate.tryMoveToEntityLiving(playerIn, 1);
		}
		// save changes
		compound.setTag(SLAVE_TAG, slaveNames);
		return itemStackIn;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 10;
	}
	
	private boolean isSlave(NBTTagList slaveNames, EntityCreature creature) {
		
		String creatureID = creature.getPersistentID().toString();
		for(int i = 0; i < slaveNames.tagCount(); i++) {
			if(slaveNames.getStringTagAt(i).equals(creatureID)) return true;
		}
		return false;
	}
	
	private void enslave(NBTTagList slaveNames, EntityCreature creature) {

		System.out.println("appending");
		slaveNames.appendTag(new NBTTagString(
				creature.getPersistentID().toString()));
	}
}
