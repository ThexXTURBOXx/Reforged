package org.silvercatcher.reforged.items.weapons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.helpers.UUIDUtil;
import org.silvercatcher.reforged.entities.ai.EntityAINecromancerSlave;
import org.silvercatcher.reforged.entities.ai.EntityAINecromancerSlaveZombie;
import org.silvercatcher.reforged.items.CompoundTags;
import org.silvercatcher.reforged.items.ExtendedItem;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIControlledByPlayer;
import net.minecraft.entity.ai.EntityAIFindEntityNearest;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class ItemNecromancersStaff extends ExtendedItem {
	
	public static final HashMap<Class<? extends EntityCreature>,
		BiConsumer<EntityPlayer, EntityCreature>> SLAVE_TRANSFORMATIONS = new HashMap<>();
	
	static {
		
		SLAVE_TRANSFORMATIONS.put(EntityZombie.class, (player, zombie) -> {
		
			zombie.tasks.taskEntries.clear();
			zombie.targetTasks.taskEntries.clear();
			
			zombie.tasks.addTask(0, new EntityAISwimming(zombie));
			zombie.tasks.addTask(2, new EntityAIAttackOnCollide(zombie, EntityLivingBase.class, 1.0, false));
			zombie.tasks.addTask(6, new EntityAIBreakDoor(zombie));
			zombie.tasks.addTask(8, new EntityAIWatchClosest(zombie, EntityPlayer.class, 8f));
			zombie.tasks.addTask(8, new EntityAILookIdle(zombie));
			
			
			zombie.targetTasks.addTask(0, new EntityAINecromancerSlaveZombie(player, zombie));
			}
		);
	}
	
	private static final ChatStyle notEnslavableStyle = 
			new ChatStyle().setColor(EnumChatFormatting.GRAY);
	private static final ChatStyle minionsDiedStyle =
			new ChatStyle().setColor(EnumChatFormatting.DARK_RED);
	private static final ChatStyle enslavedSuccessStyle =
			new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE).setBold(true);
	private static final ChatStyle alreadySlaveStyle = 
			new ChatStyle().setColor(EnumChatFormatting.LIGHT_PURPLE);

	
	// very specific compound tag for keeping track of slaves, just keep it here
	private static final String SLAVE_TAG = "slaves";
	
	public ItemNecromancersStaff() {
		
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
		
		if(!player.worldObj.isRemote) {
			
			if(entity instanceof EntityCreature) {
				
				EntityCreature creature = (EntityCreature) entity;

				NBTTagCompound compound = CompoundTags.giveCompound(stack);	
				NBTTagList slaveNames = new NBTTagList();
								
				if(compound.hasKey(SLAVE_TAG)) {
					slaveNames = compound.getTagList(SLAVE_TAG, 8);
				} else {
					slaveNames = new NBTTagList();
					compound.setTag(SLAVE_TAG, slaveNames);
				}
				
				if(isSlave(slaveNames, creature)) {
					player.addChatComponentMessage(new ChatComponentText(
							"This " + creature.getName() + " follows you already.")
							.setChatStyle(alreadySlaveStyle));
				} else {
					enslave(slaveNames, player, creature);
				}
			}
		}
		return true;
	}

	
	private boolean isSlave(NBTTagList slaveNames, EntityCreature creature) {
		
		String creatureID = creature.getPersistentID().toString();
		for(int i = 0; i < slaveNames.tagCount(); i++) {
			if(slaveNames.getStringTagAt(i).equals(creatureID)) return true;
		}
		return false;
	}
	
	private void enslave(NBTTagList slaveNames, EntityPlayer player, EntityCreature creature) {
		
		
		BiConsumer<EntityPlayer, EntityCreature> transformation =
				SLAVE_TRANSFORMATIONS.get(creature.getClass());
		
		if(transformation == null) {
			player.addChatMessage(new ChatComponentText(
					"A " + creature.getName() + " cannot be enslaved.")
					.setChatStyle(notEnslavableStyle));
		} else {
			player.addChatComponentMessage(new ChatComponentText(
					"This puny " + creature.getName() + " will now follow your command.")
					.setChatStyle(enslavedSuccessStyle));
			transformation.accept(player, creature);
			slaveNames.appendTag(new NBTTagString(
					creature.getPersistentID().toString()));
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		
		playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));

		if(!worldIn.isRemote) {
	
			NBTTagCompound compound = CompoundTags.giveCompound(itemStackIn);
			
			NBTTagList slaveNames = compound.getTagList(SLAVE_TAG, 8);

			List<EntityCreature> slaves = getSlaves(slaveNames, playerIn);

			slaves.forEach(slave -> slave.getNavigator().tryMoveToEntityLiving(playerIn, 1));
			
			// save changes
			compound.setTag(SLAVE_TAG, slaveNames);
		}
		return itemStackIn;
	}
	
	protected List<EntityCreature> getSlaves(NBTTagList slaveNames, EntityPlayer player) {
				
		MinecraftServer server = MinecraftServer.getServer();
	
		List<EntityCreature> slaves = new LinkedList<>();
		
		for(int i = 0; i < slaveNames.tagCount(); i++) {
			
			UUID uuid = UUID.fromString(slaveNames.getStringTagAt(i));
					
			// only EntityCreatures are added, so cast should be safe
			EntityCreature slave = (EntityCreature) server.getEntityFromUuid(uuid);
			
			// throw invalid entities out
			if(slave == null || !slave.isEntityAlive()) {
				
				player.addChatMessage(new ChatComponentText("One of your evil minions has died!")
						.setChatStyle(minionsDiedStyle));
				slaveNames.removeTag(i);
				break;
			}
			
			slaves.add(slave);
		}
		return slaves;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 10;
	}
}
