package org.silvercatcher.reforged.api;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import org.silvercatcher.reforged.enchantments.EnchantmentGoalseeker;
import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.entities.EntityBulletBlunderbuss;
import org.silvercatcher.reforged.entities.EntityBulletMusket;
import org.silvercatcher.reforged.entities.EntityCannon;
import org.silvercatcher.reforged.entities.EntityCannonBall;
import org.silvercatcher.reforged.entities.EntityCrossbowBolt;
import org.silvercatcher.reforged.entities.EntityDart;
import org.silvercatcher.reforged.entities.EntityDynamite;
import org.silvercatcher.reforged.entities.EntityJavelin;
import org.silvercatcher.reforged.entities.TileEntityCaltrop;

/**
 * WARNING: If an Item is turned off in Config, it could cause a
 * NullPointerException here!
 */
public abstract class ReforgedAdditions {

	// Enchantments
	public static final Enchantment goalseeker = new EnchantmentGoalseeker();

	// Items
	public static Item TAB_ICON,

	ARROW_BUNDLE,

	GUN_STOCK, BLUNDERBUSS_BARREL, MUSKET_BARREL,

	BLUNDERBUSS, MUSKET, WOODEN_BAYONET_MUSKET, STONE_BAYONET_MUSKET, GOLDEN_BAYONET_MUSKET,
			IRON_BAYONET_MUSKET, DIAMOND_BAYONET_MUSKET,

	MUSKET_BULLET, BLUNDERBUSS_SHOT,

	NEST_OF_BEES,

	FIREROD,

	WOODEN_BATTLE_AXE, STONE_BATTLE_AXE, GOLDEN_BATTLE_AXE, IRON_BATTLE_AXE, DIAMOND_BATTLE_AXE,

	WOODEN_BOOMERANG, STONE_BOOMERANG, GOLDEN_BOOMERANG, IRON_BOOMERANG, DIAMOND_BOOMERANG,

	WOODEN_SABER, STONE_SABER, GOLDEN_SABER, IRON_SABER, DIAMOND_SABER,

	WOODEN_KATANA, STONE_KATANA, GOLDEN_KATANA, IRON_KATANA, DIAMOND_KATANA,

	WOODEN_KNIFE, STONE_KNIFE, GOLDEN_KNIFE, IRON_KNIFE, DIAMOND_KNIFE,

	JAVELIN,

	KERIS,

	DART_NORMAL, DART_HUNGER, DART_POISON, DART_POISON_STRONG, DART_SLOW, DART_WITHER, BLOWGUN,

	DYNAMITE,

	CROSSBOW, CROSSBOW_BOLT,

	WOODEN_PIKE, STONE_PIKE, GOLDEN_PIKE, IRON_PIKE, DIAMOND_PIKE,

	WOODEN_MACE, STONE_MACE, GOLDEN_MACE, IRON_MACE, DIAMOND_MACE,

	WOODEN_DIRK, STONE_DIRK, GOLDEN_DIRK, IRON_DIRK, DIAMOND_DIRK,

	CANNON, CANNON_BALL;

	// Blocks
	public static Block CALTROP;

	//Entities
	public static EntityType<EntityBoomerang> ENTITY_BOOMERANG;
	public static EntityType<EntityBulletBlunderbuss> ENTITY_BLUNDERBUSS;
	public static EntityType<EntityBulletMusket> ENTITY_MUSKET;
	public static EntityType<EntityCannon> ENTITY_CANNON;
	public static EntityType<EntityCannonBall> ENTITY_CANNON_BALL;
	public static EntityType<EntityCrossbowBolt> ENTITY_CROSSBOW;
	public static EntityType<EntityDart> ENTITY_DART;
	public static EntityType<EntityDynamite> ENTITY_DYNAMITE;
	public static EntityType<EntityJavelin> ENTITY_JAVELIN;

	//Tile Entities
	public static TileEntityType<TileEntityCaltrop> TILE_ENTITY_CALTROP;
}