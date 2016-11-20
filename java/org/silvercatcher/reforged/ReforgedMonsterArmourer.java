package org.silvercatcher.reforged;

import java.util.*;

import org.silvercatcher.reforged.api.IZombieEquippable;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ReforgedMonsterArmourer {
	
	private static final UUID itemModifierUUID =  UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
	private Random random = new Random();
	
	/**DON'T CHANGE IT HERE! NULLPOINTERS WOULD OCCUR!!!*/
	private static Item[] zombieWeapons;
	
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load e) {
		if(e.isCanceled() || e.world.isRemote) return;
		List<Item> list = new ArrayList<>();
		for(Item i : ReforgedRegistry.registrationList) {
			if(i instanceof IZombieEquippable) {
				for(int c = 0; c < ((IZombieEquippable) i).zombieSpawnChance(); c++) list.add(i);
			}
		}
		if(list.isEmpty()) zombieWeapons = null;
		else zombieWeapons = list.toArray(new Item[list.size()]);
	}
	
	@SubscribeEvent
	public void onSpawn(EntityJoinWorldEvent e) {
		
		if(e.isCanceled() || e.entity == null || e.world.isRemote || 
				!(e.entity instanceof EntityZombie) || zombieWeapons == null) return;
		
		equipZombie((EntityZombie) e.entity);
	}
	
	private Item randomFrom(Item[] selection) {
		return selection[random.nextInt(selection.length)];
	}
	
	private void equipZombie(EntityZombie zombie) {
		
		if(zombie.getCurrentArmor(0) == null && random.nextInt(10) == 0) {
			
			Item item = randomFrom(zombieWeapons);
			
			zombie.setCurrentItemOrArmor(0, new ItemStack(item));
			
			zombie.getEntityAttribute(SharedMonsterAttributes.attackDamage).applyModifier(
					new AttributeModifier(itemModifierUUID, "Weapon Damage", 99f, 0));
			
		}
	}
}
