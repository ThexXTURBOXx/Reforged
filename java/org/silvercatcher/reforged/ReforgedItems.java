package org.silvercatcher.reforged;

import java.util.ArrayList;
import java.util.List;

import org.silvercatcher.reforged.items.ReforgedItem;
import org.silvercatcher.reforged.items.others.ItemArrowBundle;
import org.silvercatcher.reforged.items.weapons.ItemBattleAxe;
import org.silvercatcher.reforged.items.weapons.ItemBoomerang;
import org.silvercatcher.reforged.items.weapons.ItemBulletMusket;
import org.silvercatcher.reforged.items.weapons.ItemFireRod;
import org.silvercatcher.reforged.items.weapons.ItemMusket;
import org.silvercatcher.reforged.items.weapons.nob.ItemNestOfBees;
//import org.silvercatcher.reforged.items.weapons.nob.ItemNestOfBeesEmpty;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ReforgedItems {

	public static ReforgedItem ARROW_BUNDLE;
	
	public static ReforgedItem NEST_OF_BEES;
//	public static ReforgedItem NEST_OF_BEES_EMPTY;

	public static ReforgedItem FIREROD;
	
	public static ReforgedItem MUSKET;
	
	public static ReforgedItem WOODEN_BATTLE_AXE;
	public static ReforgedItem STONE_BATTLE_AXE;
	public static ReforgedItem IRON_BATTLE_AXE;
	public static ReforgedItem GOLDEN_BATTLE_AXE;
	public static ReforgedItem DIAMOND_BATTLE_AXE;
	
	public static ReforgedItem WOODEN_BOOMERANG;
	public static ReforgedItem STONE_BOOMERANG;
	public static ReforgedItem IRON_BOOMERANG;
	public static ReforgedItem GOLDEN_BOOMERANG;
	public static ReforgedItem DIAMOND_BOOMERANG;
	
	public static ReforgedItem TEMPORARY;
	
	
	public static List<ReforgedItem> registratonList = new ArrayList<ReforgedItem>();
	
	public static void createItems() {
		
		registratonList.add(ARROW_BUNDLE = new ItemArrowBundle());
		
		registratonList.add(NEST_OF_BEES = new ItemNestOfBees());
//		registratonList.add(NEST_OF_BEES_EMPTY = new ItemNestOfBeesEmpty());
		
		registratonList.add(FIREROD = new ItemFireRod());
		
		registratonList.add(MUSKET = new ItemMusket());
		
		registratonList.add(WOODEN_BATTLE_AXE = new ItemBattleAxe(ToolMaterial.WOOD));
		registratonList.add(STONE_BATTLE_AXE = new ItemBattleAxe(ToolMaterial.STONE));
		registratonList.add(IRON_BATTLE_AXE = new ItemBattleAxe(ToolMaterial.IRON));
		registratonList.add(GOLDEN_BATTLE_AXE = new ItemBattleAxe(ToolMaterial.GOLD));
		registratonList.add(DIAMOND_BATTLE_AXE = new ItemBattleAxe(ToolMaterial.EMERALD));

		registratonList.add(WOODEN_BOOMERANG = new ItemBoomerang(ToolMaterial.WOOD));
		registratonList.add(STONE_BOOMERANG = new ItemBoomerang(ToolMaterial.STONE));
		registratonList.add(GOLDEN_BOOMERANG = new ItemBoomerang(ToolMaterial.GOLD));
		registratonList.add(IRON_BOOMERANG = new ItemBoomerang(ToolMaterial.IRON));
		registratonList.add(DIAMOND_BOOMERANG = new ItemBoomerang(ToolMaterial.EMERALD));
		
		registratonList.add(TEMPORARY = new ItemBulletMusket());
		
	}
	
	public static void registerItems() {
		
		for(ReforgedItem item : registratonList) {
			GameRegistry.registerItem(item, item.getName());
		}
	}
	
	public static void registerRecipes() {
		
		for(ReforgedItem item : registratonList) {
			item.registerRecipes();
		}
	}
}
