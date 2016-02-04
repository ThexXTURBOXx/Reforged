package org.silvercatcher.reforged;

import java.util.Random;
import java.util.UUID;

public class ReforgedMonsterArmourer {

	private static final UUID itemModifierUUID =  UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
	private Random random = new Random();
	/*
	// simple way for now
	private static final ItemReforged [] zombieWeapons = {
			
			ReforgedRegistry.WOODEN_BATTLE_AXE,
			ReforgedRegistry.WOODEN_BATTLE_AXE,
			ReforgedRegistry.WOODEN_BATTLE_AXE,
			ReforgedRegistry.WOODEN_BATTLE_AXE,
			ReforgedRegistry.STONE_BATTLE_AXE,
			ReforgedRegistry.STONE_BATTLE_AXE,
			ReforgedRegistry.STONE_BATTLE_AXE,
			ReforgedRegistry.IRON_BATTLE_AXE,
			ReforgedRegistry.IRON_BATTLE_AXE,
			ReforgedRegistry.GOLDEN_BATTLE_AXE,
	};
	
	
	@SubscribeEvent
	public void onSpawn(EntityJoinWorldEvent event) {
		
		if(event.isCanceled() || event.entity == null || event.world.isRemote) return;
		
		if(event.entity instanceof EntityZombie) {
			equipZombie((EntityZombie) event.entity);
		}
	}

	private Item randomFrom(Item [] selection) {
		
		return selection[random.nextInt(selection.length)];
	}
	
	private void equipZombie(EntityZombie zombie) {
		
		if(zombie.getCurrentArmor(0) == null && random.nextInt(10) == 0) {
			
			Item item = randomFrom(zombieWeapons);
			
			zombie.setCurrentItemOrArmor(0, new ItemStack(item));
			
			zombie.getEntityAttribute(SharedMonsterAttributes.attackDamage).applyModifier(
					new AttributeModifier(itemModifierUUID, "Weapon Damage", 99f, 0));
			
		}
	}*/
}
