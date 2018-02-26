package org.silvercatcher.reforged;

import java.util.ArrayList;
import java.util.List;

import org.silvercatcher.reforged.ReforgedReferences.GlobalValues;
import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.blocks.BlockCaltrop;
import org.silvercatcher.reforged.items.others.*;
import org.silvercatcher.reforged.items.weapons.*;
import org.silvercatcher.reforged.packet.MessageCustomReachAttack;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.minecraftforge.registries.IForgeRegistry;

public class ReforgedRegistry {

	// Counters
	public static int counterEntities = 0;

	/** Every item on that list gets registered */
	public static List<Item> registrationList = new ArrayList<Item>();
	public static List<Block> registrationListBlocks = new ArrayList<Block>();

	// Registry
	/** Adds all items to the registrationList */
	public static void createItems() {
		if (GlobalValues.NEST_OF_BEES) {
			registrationList.add(ReforgedAdditions.ARROW_BUNDLE = new ItemArrowBundle());
			registrationList.add(ReforgedAdditions.NEST_OF_BEES = new ItemNestOfBees());
		}

		if (GlobalValues.FIREROD) {
			registrationList.add(ReforgedAdditions.FIREROD = new ItemFireRod());
		}

		if (GlobalValues.MUSKET) {
			registrationList.add(ReforgedAdditions.MUSKET_BARREL = new Item().setUnlocalizedName("musket_barrel")
					.setCreativeTab(ReforgedMod.tabReforged));
			registrationList.add(ReforgedAdditions.BLUNDERBUSS_BARREL = new Item()
					.setUnlocalizedName("blunderbuss_barrel").setCreativeTab(ReforgedMod.tabReforged));
			registrationList.add(ReforgedAdditions.GUN_STOCK = new Item().setUnlocalizedName("gun_stock")
					.setCreativeTab(ReforgedMod.tabReforged));
			registrationList.add(ReforgedAdditions.MUSKET = new ItemMusket());
			if (GlobalValues.KNIFE) {
				registrationList
						.add(ReforgedAdditions.WOODEN_BAYONET_MUSKET = new ItemMusketWithBayonet(ToolMaterial.WOOD));
				registrationList
						.add(ReforgedAdditions.STONE_BAYONET_MUSKET = new ItemMusketWithBayonet(ToolMaterial.STONE));
				registrationList
						.add(ReforgedAdditions.GOLDEN_BAYONET_MUSKET = new ItemMusketWithBayonet(ToolMaterial.GOLD));
				registrationList
						.add(ReforgedAdditions.IRON_BAYONET_MUSKET = new ItemMusketWithBayonet(ToolMaterial.IRON));
				registrationList.add(
						ReforgedAdditions.DIAMOND_BAYONET_MUSKET = new ItemMusketWithBayonet(ToolMaterial.DIAMOND));
			}
			registrationList.add(ReforgedAdditions.MUSKET_BULLET = new ItemBulletMusket());
			registrationList.add(ReforgedAdditions.BLUNDERBUSS = new ItemBlunderbuss());
			registrationList.add(ReforgedAdditions.BLUNDERBUSS_SHOT = new ItemBulletBlunderbuss());
		}

		if (GlobalValues.BATTLEAXE) {
			registrationList.add(ReforgedAdditions.WOODEN_BATTLE_AXE = new ItemBattleAxe(ToolMaterial.WOOD));
			registrationList.add(ReforgedAdditions.STONE_BATTLE_AXE = new ItemBattleAxe(ToolMaterial.STONE));
		}
		// This has to be registered! Else, the Creative Tab would be broken!
		registrationList.add(ReforgedAdditions.GOLDEN_BATTLE_AXE = new ItemBattleAxe(ToolMaterial.GOLD));
		registrationList.add(ReforgedAdditions.IRON_BATTLE_AXE = new ItemBattleAxe(ToolMaterial.IRON));
		if (GlobalValues.BATTLEAXE) {
			registrationList.add(ReforgedAdditions.DIAMOND_BATTLE_AXE = new ItemBattleAxe(ToolMaterial.DIAMOND));
		}

		if (GlobalValues.BOOMERANG) {
			registrationList.add(ReforgedAdditions.WOODEN_BOOMERANG = new ItemBoomerang(ToolMaterial.WOOD));
			registrationList.add(ReforgedAdditions.STONE_BOOMERANG = new ItemBoomerang(ToolMaterial.STONE));
			registrationList.add(ReforgedAdditions.GOLDEN_BOOMERANG = new ItemBoomerang(ToolMaterial.GOLD));
			registrationList.add(ReforgedAdditions.IRON_BOOMERANG = new ItemBoomerang(ToolMaterial.IRON));
			registrationList.add(ReforgedAdditions.DIAMOND_BOOMERANG = new ItemBoomerang(ToolMaterial.DIAMOND));
		}

		if (GlobalValues.SABRE) {
			registrationList.add(ReforgedAdditions.WOODEN_SABER = new ItemSaber(ToolMaterial.WOOD));
			registrationList.add(ReforgedAdditions.STONE_SABER = new ItemSaber(ToolMaterial.STONE));
			registrationList.add(ReforgedAdditions.GOLDEN_SABER = new ItemSaber(ToolMaterial.GOLD));
			registrationList.add(ReforgedAdditions.IRON_SABER = new ItemSaber(ToolMaterial.IRON));
			registrationList.add(ReforgedAdditions.DIAMOND_SABER = new ItemSaber(ToolMaterial.DIAMOND));
		}

		if (GlobalValues.KNIFE) {
			registrationList.add(ReforgedAdditions.WOODEN_KNIFE = new ItemKnife(ToolMaterial.WOOD));
			registrationList.add(ReforgedAdditions.STONE_KNIFE = new ItemKnife(ToolMaterial.STONE));
			registrationList.add(ReforgedAdditions.GOLDEN_KNIFE = new ItemKnife(ToolMaterial.GOLD));
			registrationList.add(ReforgedAdditions.IRON_KNIFE = new ItemKnife(ToolMaterial.IRON));
			registrationList.add(ReforgedAdditions.DIAMOND_KNIFE = new ItemKnife(ToolMaterial.DIAMOND));
		}

		if (GlobalValues.KATANA) {
			registrationList.add(ReforgedAdditions.WOODEN_KATANA = new ItemKatana(ToolMaterial.WOOD));
			registrationList.add(ReforgedAdditions.STONE_KATANA = new ItemKatana(ToolMaterial.STONE));
			registrationList.add(ReforgedAdditions.GOLDEN_KATANA = new ItemKatana(ToolMaterial.GOLD));
			registrationList.add(ReforgedAdditions.IRON_KATANA = new ItemKatana(ToolMaterial.IRON));
			registrationList.add(ReforgedAdditions.DIAMOND_KATANA = new ItemKatana(ToolMaterial.DIAMOND));
		}

		if (GlobalValues.JAVELIN) {
			registrationList.add(ReforgedAdditions.JAVELIN = new ItemJavelin());
		}

		if (GlobalValues.KERIS) {
			registrationList.add(ReforgedAdditions.KERIS = new ItemKeris());
		}

		if (GlobalValues.BLOWGUN) {
			registrationList.add(ReforgedAdditions.DART_NORMAL = new ItemDart("normal"));
			registrationList.add(ReforgedAdditions.DART_HUNGER = new ItemDart("hunger"));
			registrationList.add(ReforgedAdditions.DART_POISON = new ItemDart("poison"));
			registrationList.add(ReforgedAdditions.DART_POISON_STRONG = new ItemDart("poison_strong"));
			registrationList.add(ReforgedAdditions.DART_SLOW = new ItemDart("slowness"));
			registrationList.add(ReforgedAdditions.DART_WITHER = new ItemDart("wither"));
			registrationList.add(ReforgedAdditions.BLOWGUN = new ItemBlowGun());
		}

		if (GlobalValues.CALTROP) {
			registrationListBlocks.add(ReforgedAdditions.CALTROP = new BlockCaltrop());
		}

		if (GlobalValues.DYNAMITE) {
			registrationList.add(ReforgedAdditions.DYNAMITE = new ItemDynamite());
		}

		if (GlobalValues.CROSSBOW) {
			registrationList.add(ReforgedAdditions.CROSSBOW = new ItemCrossbow());
			registrationList.add(ReforgedAdditions.CROSSBOW_BOLT = new ItemCrossbowBolt());
		}

		if (GlobalValues.PIKE) {
			registrationList.add(ReforgedAdditions.WOODEN_PIKE = new ItemPike(ToolMaterial.WOOD));
			registrationList.add(ReforgedAdditions.STONE_PIKE = new ItemPike(ToolMaterial.STONE));
			registrationList.add(ReforgedAdditions.GOLDEN_PIKE = new ItemPike(ToolMaterial.GOLD));
			registrationList.add(ReforgedAdditions.IRON_PIKE = new ItemPike(ToolMaterial.IRON));
			registrationList.add(ReforgedAdditions.DIAMOND_PIKE = new ItemPike(ToolMaterial.DIAMOND));
		}

		if (GlobalValues.MACE) {
			registrationList.add(ReforgedAdditions.WOODEN_MACE = new ItemMace(ToolMaterial.WOOD));
			registrationList.add(ReforgedAdditions.STONE_MACE = new ItemMace(ToolMaterial.STONE));
			registrationList.add(ReforgedAdditions.GOLDEN_MACE = new ItemMace(ToolMaterial.GOLD));
			registrationList.add(ReforgedAdditions.IRON_MACE = new ItemMace(ToolMaterial.IRON));
			registrationList.add(ReforgedAdditions.DIAMOND_MACE = new ItemMace(ToolMaterial.DIAMOND));
		}

		if (GlobalValues.DIRK) {
			registrationList.add(ReforgedAdditions.WOODEN_DIRK = new ItemDirk(ToolMaterial.WOOD));
			registrationList.add(ReforgedAdditions.STONE_DIRK = new ItemDirk(ToolMaterial.STONE));
			registrationList.add(ReforgedAdditions.GOLDEN_DIRK = new ItemDirk(ToolMaterial.GOLD));
			registrationList.add(ReforgedAdditions.IRON_DIRK = new ItemDirk(ToolMaterial.IRON));
			registrationList.add(ReforgedAdditions.DIAMOND_DIRK = new ItemDirk(ToolMaterial.DIAMOND));
		}

		if (GlobalValues.CANNON) {
			registrationList.add(ReforgedAdditions.CANNON = new ItemCannon().setUnlocalizedName("cannon")
					.setCreativeTab(ReforgedMod.tabReforged));
			registrationList.add(ReforgedAdditions.CANNON_BALL = new Item().setUnlocalizedName("cannon_ball")
					.setCreativeTab(ReforgedMod.tabReforged));
		}
	}

	/**
	 * Helper method for registering an Entity
	 * 
	 * @param c
	 *            The class of the Entity
	 * @param name
	 *            The name for the Entity
	 */
	public static void registerEntity(Class<? extends Entity> c, String name) {
		EntityRegistry.registerModEntity(new ResourceLocation(ReforgedMod.ID, name), c, name, ++counterEntities,
				ReforgedMod.instance, 120, 1, true);
	}

	/**
	 * Helper method for registering our EventHandler
	 * 
	 * @param ReforgedEvents
	 *            The instance of our EventHandler
	 */
	public static void registerEventHandler(Object event) {
		FMLCommonHandler.instance().bus().register(event);
		MinecraftForge.EVENT_BUS.register(event);
	}

	/**
	 * Helper method for registering an Custom IRecipe
	 * 
	 * @param name
	 *            The name for the Recipe
	 * @param recipe
	 *            The instance of the Recipe
	 * @param recipeclass
	 *            The class of the Recipe
	 * @param category
	 *            {@link Category#SHAPED} or {@link Category#SHAPELESS}?
	 */
	public static void registerIRecipe(String name, IRecipe recipe, Class<?> recipeclass, Category category) {
		String catString;
		if (category == Category.SHAPELESS) {
			catString = "after:minecraft:shapeless";
		} else if (category == Category.SHAPED) {
			catString = "after:minecraft:shaped";
		} else {
			throw new IllegalArgumentException("The Category called " + category.name() + " couldn't be found!");
		}
		// GameRegistry.addRecipe(recipe);
		RecipeSorter.register(ReforgedMod.ID + ":" + name, recipeclass, category, catString);
	}

	/** Registers all our Packets */
	public static void registerPackets() {
		ReforgedMod.network = NetworkRegistry.INSTANCE.newSimpleChannel(ReforgedMod.ID);
		int packetId = 0;
		ReforgedMod.network.registerMessage(MessageCustomReachAttack.Handler.class, MessageCustomReachAttack.class,
				packetId++, Side.SERVER);
	}

	@SubscribeEvent
	/** Registers all blocks out of the registrationListBlocks */
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> reg = event.getRegistry();
		for (Block block : registrationListBlocks) {
			reg.register(block
					.setRegistryName(new ResourceLocation(ReforgedMod.ID, block.getUnlocalizedName().substring(5))));
		}
	}

	@SubscribeEvent
	/** Registers all items out of the registrationList */
	public void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> reg = event.getRegistry();
		// Register all Items
		for (Item item : registrationList) {
			reg.register(
					item.setRegistryName(new ResourceLocation(ReforgedMod.ID, item.getUnlocalizedName().substring(5))));
		}
		for (Block block : registrationListBlocks) {
			ItemBlock itemBlock = new ItemBlock(block);
			itemBlock.setRegistryName(block.getRegistryName());
			ReforgedMod.proxy.registerItemRenderer(itemBlock, 0, block.getUnlocalizedName().substring(5));
			reg.register(itemBlock);
		}

	}

}