package org.silvercatcher.reforged.api;

import org.silvercatcher.reforged.enchantments.EnchantmentGoalseeker;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;

/**WARNING: If an Item is turned off in Config,
 * it could cause a NullPointerException here!*/
public class ReforgedAdditions {
	
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
	
	public static Item KERIS;

	public static Item DART_NORMAL;
	public static Item DART_HUNGER;
	public static Item DART_POISON;
	public static Item DART_POISON_STRONG;
	public static Item DART_SLOW;
	public static Item DART_WITHER;
	public static Item BLOWGUN;
	
	public static Item DYNAMITE;
	
	//Blocks
	public static Block CALTROP;
	
	//Enchantments
    public static final Enchantment goalseeker = new EnchantmentGoalseeker(100);
}