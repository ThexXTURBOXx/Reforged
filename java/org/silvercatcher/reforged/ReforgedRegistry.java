package org.silvercatcher.reforged;

import java.util.ArrayList;
import java.util.List;

import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.entities.EntityBulletMusket;
import org.silvercatcher.reforged.entities.EntityJavelin;
import org.silvercatcher.reforged.items.ReforgedItem;
import org.silvercatcher.reforged.items.others.ItemArrowBundle;
import org.silvercatcher.reforged.items.others.ItemBulletMusket;
import org.silvercatcher.reforged.items.weapons.ItemBattleAxe;
import org.silvercatcher.reforged.items.weapons.ItemBoomerang;
import org.silvercatcher.reforged.items.weapons.ItemFireRod;
import org.silvercatcher.reforged.items.weapons.ItemJavelin;
import org.silvercatcher.reforged.items.weapons.ItemMusket;
import org.silvercatcher.reforged.items.weapons.ItemNestOfBees;
import org.silvercatcher.reforged.render.RenderBoomerang;
import org.silvercatcher.reforged.render.RenderBulletMusket;
import org.silvercatcher.reforged.render.RenderJavelin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ReforgedRegistry {

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
	
	public static ReforgedItem JAVELIN;
	
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
		
		registratonList.add(JAVELIN = new ItemJavelin());
		
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
	
	public static void registerEntity(Class c, String name, int counter) {
		EntityRegistry.registerModEntity(c, name, counter, ReforgedMod.instance, 120, 3, true);		
	}	
	
	public static void registerEntityRenderers(Class entityclass, Render renderclass) {
		RenderingRegistry.registerEntityRenderingHandler(entityclass, renderclass);
	}
	
}
