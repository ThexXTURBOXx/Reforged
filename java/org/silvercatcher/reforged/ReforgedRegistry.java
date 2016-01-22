package org.silvercatcher.reforged;

import java.util.ArrayList;
import java.util.List;

import org.silvercatcher.reforged.items.others.ItemArrowBundle;
import org.silvercatcher.reforged.items.others.ItemBulletMusket;
import org.silvercatcher.reforged.items.weapons.ItemBattleAxe;
import org.silvercatcher.reforged.items.weapons.ItemMusketWithBayonet;
import org.silvercatcher.reforged.items.weapons.ItemBoomerang;
import org.silvercatcher.reforged.items.weapons.ItemHolyCross;
import org.silvercatcher.reforged.items.weapons.ItemFireRod;
import org.silvercatcher.reforged.items.weapons.ItemJavelin;
import org.silvercatcher.reforged.items.weapons.ItemMusket;
import org.silvercatcher.reforged.items.weapons.ItemNestOfBees;
import org.silvercatcher.reforged.items.weapons.ItemSaber;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ReforgedRegistry {
	
	//public static ToolMaterial COPPER = EnumHelper.addToolMaterial("COPPER", 2, 200, 5.0F, 1.5F, 10);

	public static Item ARROW_BUNDLE;
	
	public static Item GUN_STOCK;
	public static Item BLUNDERBUSS_BARREL;
	public static Item MUSKET_BARREL;
	
	public static Item BLUNDERBUSS;
	public static Item MUSKET;
	public static Item WOODEN_BAYONET_MUSKET;
	public static Item STONE_BAYONET_MUSKET;
	public static Item IRON_BAYONET_MUSKET;
	public static Item GOLDEN_BAYONET_MUSKET;
	public static Item DIAMOND_BAYONET_MUSKET;
	
	public static Item MUSKET_BULLET;
	public static Item BLUNDERBUSS_SHOT;
	
	public static Item NEST_OF_BEES;

	public static Item HOLY_CROSS;
	
	public static Item FIREROD;
	
	public static Item WOODEN_BATTLE_AXE;
	public static Item STONE_BATTLE_AXE;
	public static Item IRON_BATTLE_AXE;
	public static Item GOLDEN_BATTLE_AXE;
	public static Item DIAMOND_BATTLE_AXE;
	//public static Item COPPER_BATTLE_AXE = new ItemBattleAxe(COPPER);
	
	public static Item WOODEN_BOOMERANG;
	public static Item STONE_BOOMERANG;
	public static Item IRON_BOOMERANG;
	public static Item GOLDEN_BOOMERANG;
	public static Item DIAMOND_BOOMERANG;
	//public static Item COPPER_BOOMERANG = new ItemBoomerang(COPPER);
	
	public static Item WOODEN_SABER;
	public static Item STONE_SABER;
	public static Item IRON_SABER;
	public static Item GOLDEN_SABER;
	public static Item DIAMOND_SABER;
	
	public static Item JAVELIN;	

	
	public static List<Item> registratonList = new ArrayList<Item>();
	
	public static void createItems() {
		
		registratonList.add(ARROW_BUNDLE = new ItemArrowBundle());
		
		registratonList.add(NEST_OF_BEES = new ItemNestOfBees());

		registratonList.add(HOLY_CROSS = new ItemHolyCross());
		
		registratonList.add(FIREROD = new ItemFireRod());
		
		
		registratonList.add(MUSKET = new ItemMusket());
		registratonList.add(WOODEN_BAYONET_MUSKET = new ItemMusketWithBayonet(ToolMaterial.WOOD));
		registratonList.add(STONE_BAYONET_MUSKET = new ItemMusketWithBayonet(ToolMaterial.STONE));
		registratonList.add(IRON_BAYONET_MUSKET = new ItemMusketWithBayonet(ToolMaterial.IRON));
		registratonList.add(GOLDEN_BAYONET_MUSKET = new ItemMusketWithBayonet(ToolMaterial.GOLD));
		registratonList.add(DIAMOND_BAYONET_MUSKET = new ItemMusketWithBayonet(ToolMaterial.EMERALD));
		
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
		
		registratonList.add(WOODEN_SABER = new ItemSaber(ToolMaterial.WOOD));
		registratonList.add(STONE_SABER = new ItemSaber(ToolMaterial.STONE));
		registratonList.add(GOLDEN_SABER = new ItemSaber(ToolMaterial.GOLD));
		registratonList.add(IRON_SABER = new ItemSaber(ToolMaterial.IRON));
		registratonList.add(DIAMOND_SABER = new ItemSaber(ToolMaterial.EMERALD));
		
		registratonList.add(JAVELIN = new ItemJavelin());
		
		registratonList.add(MUSKET_BULLET = new ItemBulletMusket());
		
	}
	
	public static void registerItems() {
		
		for(Item item : registratonList) {
			GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
		}
	}
	
	public static void registerRecipes() {
		
		/*
		for(Item item : registratonList) {
			item.registerRecipes();
		}*/
	}
	
	public static void registerEntity(Class c, String name, int counter) {
		EntityRegistry.registerModEntity(c, name, counter, ReforgedMod.instance, 120, 3, true);		
	}	
	
	public static void registerEntityRenderer(Class entityclass, Render renderclass) {
		RenderingRegistry.registerEntityRenderingHandler(entityclass, renderclass);
	}
	
	public static void registerEventHandler(ReforgedEvents eventclass) {
		FMLCommonHandler.instance().bus().register(eventclass);
	    MinecraftForge.EVENT_BUS.register(eventclass);
	}
	
}
