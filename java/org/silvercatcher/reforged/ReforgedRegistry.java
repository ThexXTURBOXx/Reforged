package org.silvercatcher.reforged;

import java.util.ArrayList;
import java.util.List;

import org.silvercatcher.reforged.items.ItemReforged;
import org.silvercatcher.reforged.items.others.ItemArrowBundle;
import org.silvercatcher.reforged.items.others.ItemBulletMusket;
import org.silvercatcher.reforged.items.weapons.ItemBattleAxe;
import org.silvercatcher.reforged.items.weapons.ItemBoomerang;
import org.silvercatcher.reforged.items.weapons.ItemHolyCross;
import org.silvercatcher.reforged.items.weapons.ItemFireRod;
import org.silvercatcher.reforged.items.weapons.ItemJavelin;
import org.silvercatcher.reforged.items.weapons.ItemMusket;
import org.silvercatcher.reforged.items.weapons.ItemNestOfBees;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ReforgedRegistry {

	public static ItemReforged ARROW_BUNDLE;
	
	public static ItemReforged NEST_OF_BEES;

	public static ItemReforged HOLY_CROSS;
	
	public static ItemReforged FIREROD;
	
	public static ItemReforged MUSKET;
	
	public static ItemReforged WOODEN_BATTLE_AXE;
	public static ItemReforged STONE_BATTLE_AXE;
	public static ItemReforged IRON_BATTLE_AXE;
	public static ItemReforged GOLDEN_BATTLE_AXE;
	public static ItemReforged DIAMOND_BATTLE_AXE;
	
	public static ItemReforged WOODEN_BOOMERANG;
	public static ItemReforged STONE_BOOMERANG;
	public static ItemReforged IRON_BOOMERANG;
	public static ItemReforged GOLDEN_BOOMERANG;
	public static ItemReforged DIAMOND_BOOMERANG;
	
	public static ItemReforged JAVELIN;
	
	public static ItemReforged TEMPORARY;
	
	
	public static List<ItemReforged> registratonList = new ArrayList<ItemReforged>();
	
	public static void createItems() {
		
		registratonList.add(ARROW_BUNDLE = new ItemArrowBundle());
		
		registratonList.add(NEST_OF_BEES = new ItemNestOfBees());

		registratonList.add(HOLY_CROSS = new ItemHolyCross());
		
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
		
		registratonList.add(JAVELIN = new ItemJavelin());
		
		registratonList.add(TEMPORARY = new ItemBulletMusket());
		
	}
	
	public static void registerItems() {
		
		for(ItemReforged item : registratonList) {
			GameRegistry.registerItem(item, item.getName());
		}
	}
	
	public static void registerRecipes() {
		
		for(ItemReforged item : registratonList) {
			item.registerRecipes();
		}
	}
	
	public static void registerEntity(Class c, String name, int counter) {
		EntityRegistry.registerModEntity(c, name, counter, ReforgedMod.instance, 120, 3, true);		
	}	
	
	public static void registerEntityRenderers(Class entityclass, Render renderclass) {
		RenderingRegistry.registerEntityRenderingHandler(entityclass, renderclass);
	}
	
}
