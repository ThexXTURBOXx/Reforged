package org.silvercatcher.reforged;

import java.util.ArrayList;
import java.util.List;

import org.silvercatcher.reforged.weapons.ItemNestOfBees;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ReforgedItems {

	public static ReforgedItem NEST_OF_BEES;

	public static List<ReforgedItem> registratonList = new ArrayList<ReforgedItem>();
	
	public static void init() {
		
		registratonList.add(NEST_OF_BEES = new ItemNestOfBees());
	}
	
	public static void registerItems() {
		
		for(ReforgedItem item : registratonList) {
			GameRegistry.registerItem(item, item.getName());
		}
	}
}
