package org.silvercatcher.reforged;

import java.util.ArrayList;
import java.util.List;

import org.silvercatcher.reforged.ReforgedReferences.GlobalValues;
import org.silvercatcher.reforged.items.*;
import org.silvercatcher.reforged.items.others.*;
import org.silvercatcher.reforged.items.weapons.*;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ReforgedRegistry {
	
	//ToolMaterials
	//public static ToolMaterial COPPER = EnumHelper.addToolMaterial("COPPER", 2, 200, 5.0F, 1.5F, 10);

	//Items
	public static Item ARROW_BUNDLE;
	
	public static Item GUN_STOCK;
	public static Item BLUNDERBUSS_BARREL;
	public static Item MUSKET_BARREL;
	
	public static Item BLUNDERBUSS;
	public static Item MUSKET;
	public static Item WOODEN_BAYONET_MUSKET;
	public static Item STONE_BAYONET_MUSKET;
	public static Item GOLDEN_BAYONET_MUSKET;
	public static Item IRON_BAYONET_MUSKET;
	public static Item DIAMOND_BAYONET_MUSKET;
	
	public static Item MUSKET_BULLET;
	public static Item BLUNDERBUSS_SHOT;
	
	public static Item NEST_OF_BEES;

	public static Item HOLY_CROSS;
	
	public static Item FIREROD;
	
	public static Item WOODEN_BATTLE_AXE;
	public static Item STONE_BATTLE_AXE;
	public static Item GOLDEN_BATTLE_AXE;
	public static Item IRON_BATTLE_AXE;
	public static Item DIAMOND_BATTLE_AXE;
	
	public static Item WOODEN_BOOMERANG;
	public static Item STONE_BOOMERANG;
	public static Item GOLDEN_BOOMERANG;
	public static Item IRON_BOOMERANG;
	public static Item DIAMOND_BOOMERANG;
	
	public static Item WOODEN_SABER;
	public static Item STONE_SABER;
	public static Item GOLDEN_SABER;
	public static Item IRON_SABER;
	public static Item DIAMOND_SABER;
	
	public static Item WOODEN_KATANA;
	public static Item STONE_KATANA;
	public static Item GOLDEN_KATANA;
	public static Item IRON_KATANA;
	public static Item DIAMOND_KATANA;
	
	public static Item WOODEN_KNIFE;
	public static Item STONE_KNIFE;
	public static Item GOLDEN_KNIFE;
	public static Item IRON_KNIFE;
	public static Item DIAMOND_KNIFE;
	
	public static Item JAVELIN;	

	public static Item DART_NORMAL;
	public static Item DART_HUNGER;
	public static Item DART_POISON;
	public static Item DART_POISON_2;
	public static Item DART_SLOW;
	public static Item DART_WITHER;
	public static Item BLOWGUN;
	
	//Damage Sources
	public static DamageSource boomerangHitDamage = new DamageSource("boomeranghit").setDamageBypassesArmor();
	public static DamageSource boomerangBreakDamage = new DamageSource("boomerangbreak").setDamageBypassesArmor();
	public static DamageSource javelinDamage = new DamageSource("javelin").setDamageBypassesArmor();
	public static DamageSource musketDamage = new DamageSource("musket").setDamageBypassesArmor();
	public static DamageSource dartDamage = new DamageSource("dart").setDamageBypassesArmor();
	
	//Hashmaps
	public static List<Item> registrationList = new ArrayList<Item>();
	
	//Registry
	public static void createItems() {
		if(GlobalValues.ARROW_BUNDLE) {
			registrationList.add(ARROW_BUNDLE = new ItemArrowBundle());
		}

		if(GlobalValues.NEST_OF_BEES) {
			registrationList.add(NEST_OF_BEES = new ItemNestOfBees());
		}
		
		if(GlobalValues.HOLY_CROSS) {
			registrationList.add(HOLY_CROSS = new ItemHolyCross());
		}
		
		if(GlobalValues.FIREROD) {
			registrationList.add(FIREROD = new ItemFireRod());
		}
		
		if(GlobalValues.MUSKET) {
			registrationList.add(MUSKET_BARREL = 
					new Item().setUnlocalizedName("musket_barrel").setCreativeTab(ReforgedMod.tabReforged));
			registrationList.add(BLUNDERBUSS_BARREL = 
					new Item().setUnlocalizedName("blunderbuss_barrel").setCreativeTab(ReforgedMod.tabReforged));
			registrationList.add(GUN_STOCK = 
					new Item().setUnlocalizedName("gun_stock").setCreativeTab(ReforgedMod.tabReforged));
			registrationList.add(MUSKET = new ItemMusket());
			registrationList.add(WOODEN_BAYONET_MUSKET = new ItemMusketWithBayonet(ToolMaterial.WOOD));
			registrationList.add(STONE_BAYONET_MUSKET = new ItemMusketWithBayonet(ToolMaterial.STONE));
			registrationList.add(GOLDEN_BAYONET_MUSKET = new ItemMusketWithBayonet(ToolMaterial.GOLD));
			registrationList.add(IRON_BAYONET_MUSKET = new ItemMusketWithBayonet(ToolMaterial.IRON));
			registrationList.add(DIAMOND_BAYONET_MUSKET = new ItemMusketWithBayonet(ToolMaterial.EMERALD));
			registrationList.add(MUSKET_BULLET = new ItemBulletMusket());
			registrationList.add(BLUNDERBUSS = new ItemBlunderbuss());
			registrationList.add(BLUNDERBUSS_SHOT = new ItemBulletBlunderbuss());
		}
		
		if(GlobalValues.BATTLEAXE) {
			registrationList.add(WOODEN_BATTLE_AXE = new ItemBattleAxe(ToolMaterial.WOOD));
			registrationList.add(STONE_BATTLE_AXE = new ItemBattleAxe(ToolMaterial.STONE));
			registrationList.add(GOLDEN_BATTLE_AXE = new ItemBattleAxe(ToolMaterial.GOLD));
		}
		//This has to be registered! Else, the Creative Tab would be broken!
		registrationList.add(IRON_BATTLE_AXE = new ItemBattleAxe(ToolMaterial.IRON));
		if(GlobalValues.BATTLEAXE) {
			registrationList.add(DIAMOND_BATTLE_AXE = new ItemBattleAxe(ToolMaterial.EMERALD));
		}
		
		if(GlobalValues.BOOMERANG) {
			registrationList.add(WOODEN_BOOMERANG = new ItemBoomerang(ToolMaterial.WOOD));
			registrationList.add(STONE_BOOMERANG = new ItemBoomerang(ToolMaterial.STONE));
			registrationList.add(GOLDEN_BOOMERANG = new ItemBoomerang(ToolMaterial.GOLD));
			registrationList.add(IRON_BOOMERANG = new ItemBoomerang(ToolMaterial.IRON));
			registrationList.add(DIAMOND_BOOMERANG = new ItemBoomerang(ToolMaterial.EMERALD));
		}
		
		if(GlobalValues.SABRE) {
			registrationList.add(WOODEN_SABER = new ItemSaber(ToolMaterial.WOOD));
			registrationList.add(STONE_SABER = new ItemSaber(ToolMaterial.STONE));
			registrationList.add(GOLDEN_SABER = new ItemSaber(ToolMaterial.GOLD));
			registrationList.add(IRON_SABER = new ItemSaber(ToolMaterial.IRON));
			registrationList.add(DIAMOND_SABER = new ItemSaber(ToolMaterial.EMERALD));
		}
		
		if(GlobalValues.KNIFE) {
			registrationList.add(WOODEN_KNIFE = new ItemKnife(ToolMaterial.WOOD));
			registrationList.add(STONE_KNIFE = new ItemKnife(ToolMaterial.STONE));
			registrationList.add(GOLDEN_KNIFE = new ItemKnife(ToolMaterial.GOLD));
			registrationList.add(IRON_KNIFE = new ItemKnife(ToolMaterial.IRON));
			registrationList.add(DIAMOND_KNIFE = new ItemKnife(ToolMaterial.EMERALD));
		}
		
		if(GlobalValues.KATANA) {
			registrationList.add(WOODEN_KATANA = new ItemKatana(ToolMaterial.WOOD));
			registrationList.add(STONE_KATANA = new ItemKatana(ToolMaterial.STONE));
			registrationList.add(GOLDEN_KATANA = new ItemKatana(ToolMaterial.GOLD));
			registrationList.add(IRON_KATANA = new ItemKatana(ToolMaterial.IRON));
			registrationList.add(DIAMOND_KATANA = new ItemKatana(ToolMaterial.EMERALD));
		}
		
		if(GlobalValues.JAVELIN) {
			registrationList.add(JAVELIN = new ItemJavelin());
		}
		
		if(GlobalValues.BLOWGUN) {
			registrationList.add(DART_NORMAL = new ItemDart("normal"));
			registrationList.add(DART_HUNGER = new ItemDart("hunger"));
			registrationList.add(DART_POISON = new ItemDart("poison"));
			registrationList.add(DART_POISON_2 = new ItemDart("poison_strong"));
			registrationList.add(DART_SLOW = new ItemDart("slowness"));
			registrationList.add(DART_WITHER = new ItemDart("wither"));
			registrationList.add(BLOWGUN = new ItemBlowGun());
		}
	}
	
	public static void registerItems() {
		
		//todo refactor recipe system
		for(Item item : registrationList) {
			GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
		}
	}
	
	public static void registerRecipes() {
		
		for(Item item : registrationList) {
			if(item instanceof ItemExtension) {
				((ItemExtension) (item)).registerRecipes();
			}
		}
		
		if(GlobalValues.MUSKET) {
			
			GameRegistry.addRecipe(new ItemStack(GUN_STOCK),
					"   ",
					"ssp",
					"   ",
					's', Items.stick,
					'p', Blocks.planks);
		
			GameRegistry.addRecipe(new ItemStack(MUSKET_BARREL),
					"   ",
					"iif",
					"  i",
					'i', Items.iron_ingot,
					'f', Items.flint_and_steel);
		
			GameRegistry.addRecipe(new ItemStack(BLUNDERBUSS_BARREL),
					"i  ",
					" if",
					"i i",
					'i', Items.iron_ingot,
					'f', Items.flint_and_steel);
		}
	}
	
	public static void registerEntity(Class c, String name, int counter) {
		EntityRegistry.registerModEntity(c, name, counter, ReforgedMod.instance, 120, 1, true);		
	}	
	
	public static void registerEntityRenderer(Class entityclass, Render renderclass) {
		RenderingRegistry.registerEntityRenderingHandler(entityclass, renderclass);
	}
	
	public static void registerEventHandler(ReforgedEvents eventclass) {
		FMLCommonHandler.instance().bus().register(eventclass);
	    MinecraftForge.EVENT_BUS.register(eventclass);
	}
	
}
