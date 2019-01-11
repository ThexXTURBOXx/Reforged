package org.silvercatcher.reforged;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemTier;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import org.silvercatcher.reforged.ReforgedReferences.GlobalValues;
import org.silvercatcher.reforged.api.ICustomReach;
import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.blocks.BlockCaltrop;
import org.silvercatcher.reforged.items.others.ItemArrowBundle;
import org.silvercatcher.reforged.items.others.ItemBulletBlunderbuss;
import org.silvercatcher.reforged.items.others.ItemBulletMusket;
import org.silvercatcher.reforged.items.others.ItemCrossbowBolt;
import org.silvercatcher.reforged.items.others.ItemDart;
import org.silvercatcher.reforged.items.weapons.ItemBattleAxe;
import org.silvercatcher.reforged.items.weapons.ItemBlowGun;
import org.silvercatcher.reforged.items.weapons.ItemBlunderbuss;
import org.silvercatcher.reforged.items.weapons.ItemBoomerang;
import org.silvercatcher.reforged.items.weapons.ItemCannon;
import org.silvercatcher.reforged.items.weapons.ItemCrossbow;
import org.silvercatcher.reforged.items.weapons.ItemDirk;
import org.silvercatcher.reforged.items.weapons.ItemDynamite;
import org.silvercatcher.reforged.items.weapons.ItemFireRod;
import org.silvercatcher.reforged.items.weapons.ItemJavelin;
import org.silvercatcher.reforged.items.weapons.ItemKatana;
import org.silvercatcher.reforged.items.weapons.ItemKeris;
import org.silvercatcher.reforged.items.weapons.ItemKnife;
import org.silvercatcher.reforged.items.weapons.ItemMace;
import org.silvercatcher.reforged.items.weapons.ItemMusket;
import org.silvercatcher.reforged.items.weapons.ItemMusketWithBayonet;
import org.silvercatcher.reforged.items.weapons.ItemNestOfBees;
import org.silvercatcher.reforged.items.weapons.ItemPike;
import org.silvercatcher.reforged.items.weapons.ItemSaber;
import org.silvercatcher.reforged.packet.MessageCustomReachAttack;

public class ReforgedRegistry {

	// Counters
	public static int counterEntities = 0;

	/**
	 * Every item on that list gets registered
	 */
	public static List<Item> registrationList = new ArrayList<>();
	public static List<Block> registrationListBlocks = new ArrayList<>();
	public static List<EntityType<?>> registrationListEntities = new ArrayList<>();
	public static List<TileEntityType<?>> registrationListTileEntities = new ArrayList<>();

	// Registry

	/**
	 * Adds all items to the registrationList
	 */
	public static void createItems() {
		if (GlobalValues.NEST_OF_BEES) {
			registrationList.add(ReforgedAdditions.ARROW_BUNDLE = new ItemArrowBundle());
			registrationList.add(ReforgedAdditions.NEST_OF_BEES = new ItemNestOfBees());
		}

		if (GlobalValues.FIREROD) {
			registrationList.add(ReforgedAdditions.FIREROD = new ItemFireRod());
		}

		if (GlobalValues.MUSKET) {
			registrationList.add(ReforgedAdditions.MUSKET_BARREL = new Item(new Item.Builder().group(ReforgedMod.tabReforged))
					.setRegistryName(new ResourceLocation(ReforgedMod.ID, "musket_barrel")));
			registrationList.add(ReforgedAdditions.BLUNDERBUSS_BARREL = new Item(new Item.Builder().group(ReforgedMod.tabReforged))
					.setRegistryName(new ResourceLocation(ReforgedMod.ID, "blunderbuss_barrel")));
			registrationList.add(ReforgedAdditions.GUN_STOCK = new Item(new Item.Builder().group(ReforgedMod.tabReforged))
					.setRegistryName(new ResourceLocation(ReforgedMod.ID, "gun_stock")));
			registrationList.add(ReforgedAdditions.MUSKET = new ItemMusket());
			if (GlobalValues.KNIFE) {
				registrationList
						.add(ReforgedAdditions.WOODEN_BAYONET_MUSKET = new ItemMusketWithBayonet(ItemTier.WOOD));
				registrationList
						.add(ReforgedAdditions.STONE_BAYONET_MUSKET = new ItemMusketWithBayonet(ItemTier.STONE));
				registrationList
						.add(ReforgedAdditions.GOLDEN_BAYONET_MUSKET = new ItemMusketWithBayonet(ItemTier.GOLD));
				registrationList
						.add(ReforgedAdditions.IRON_BAYONET_MUSKET = new ItemMusketWithBayonet(ItemTier.IRON));
				registrationList.add(
						ReforgedAdditions.DIAMOND_BAYONET_MUSKET = new ItemMusketWithBayonet(ItemTier.DIAMOND));
			}
			registrationList.add(ReforgedAdditions.MUSKET_BULLET = new ItemBulletMusket());
			registrationList.add(ReforgedAdditions.BLUNDERBUSS = new ItemBlunderbuss());
			registrationList.add(ReforgedAdditions.BLUNDERBUSS_SHOT = new ItemBulletBlunderbuss());
		}

		if (GlobalValues.BATTLEAXE) {
			registrationList.add(ReforgedAdditions.WOODEN_BATTLE_AXE = new ItemBattleAxe(ItemTier.WOOD));
			registrationList.add(ReforgedAdditions.STONE_BATTLE_AXE = new ItemBattleAxe(ItemTier.STONE));
		}
		// This has to be registered! Else, the Creative Tab would be broken!
		registrationList.add(ReforgedAdditions.GOLDEN_BATTLE_AXE = new ItemBattleAxe(ItemTier.GOLD));
		registrationList.add(ReforgedAdditions.IRON_BATTLE_AXE = new ItemBattleAxe(ItemTier.IRON));
		if (GlobalValues.BATTLEAXE) {
			registrationList.add(ReforgedAdditions.DIAMOND_BATTLE_AXE = new ItemBattleAxe(ItemTier.DIAMOND));
		}

		if (GlobalValues.BOOMERANG) {
			registrationList.add(ReforgedAdditions.WOODEN_BOOMERANG = new ItemBoomerang(ItemTier.WOOD));
			registrationList.add(ReforgedAdditions.STONE_BOOMERANG = new ItemBoomerang(ItemTier.STONE));
			registrationList.add(ReforgedAdditions.GOLDEN_BOOMERANG = new ItemBoomerang(ItemTier.GOLD));
			registrationList.add(ReforgedAdditions.IRON_BOOMERANG = new ItemBoomerang(ItemTier.IRON));
			registrationList.add(ReforgedAdditions.DIAMOND_BOOMERANG = new ItemBoomerang(ItemTier.DIAMOND));
		}

		if (GlobalValues.SABRE) {
			registrationList.add(ReforgedAdditions.WOODEN_SABER = new ItemSaber(ItemTier.WOOD));
			registrationList.add(ReforgedAdditions.STONE_SABER = new ItemSaber(ItemTier.STONE));
			registrationList.add(ReforgedAdditions.GOLDEN_SABER = new ItemSaber(ItemTier.GOLD));
			registrationList.add(ReforgedAdditions.IRON_SABER = new ItemSaber(ItemTier.IRON));
			registrationList.add(ReforgedAdditions.DIAMOND_SABER = new ItemSaber(ItemTier.DIAMOND));
		}

		if (GlobalValues.KNIFE) {
			registrationList.add(ReforgedAdditions.WOODEN_KNIFE = new ItemKnife(ItemTier.WOOD));
			registrationList.add(ReforgedAdditions.STONE_KNIFE = new ItemKnife(ItemTier.STONE));
			registrationList.add(ReforgedAdditions.GOLDEN_KNIFE = new ItemKnife(ItemTier.GOLD));
			registrationList.add(ReforgedAdditions.IRON_KNIFE = new ItemKnife(ItemTier.IRON));
			registrationList.add(ReforgedAdditions.DIAMOND_KNIFE = new ItemKnife(ItemTier.DIAMOND));
		}

		if (GlobalValues.KATANA) {
			registrationList.add(ReforgedAdditions.WOODEN_KATANA = new ItemKatana(ItemTier.WOOD));
			registrationList.add(ReforgedAdditions.STONE_KATANA = new ItemKatana(ItemTier.STONE));
			registrationList.add(ReforgedAdditions.GOLDEN_KATANA = new ItemKatana(ItemTier.GOLD));
			registrationList.add(ReforgedAdditions.IRON_KATANA = new ItemKatana(ItemTier.IRON));
			registrationList.add(ReforgedAdditions.DIAMOND_KATANA = new ItemKatana(ItemTier.DIAMOND));
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
			registrationList.add(ReforgedAdditions.WOODEN_PIKE = new ItemPike(ItemTier.WOOD));
			registrationList.add(ReforgedAdditions.STONE_PIKE = new ItemPike(ItemTier.STONE));
			registrationList.add(ReforgedAdditions.GOLDEN_PIKE = new ItemPike(ItemTier.GOLD));
			registrationList.add(ReforgedAdditions.IRON_PIKE = new ItemPike(ItemTier.IRON));
			registrationList.add(ReforgedAdditions.DIAMOND_PIKE = new ItemPike(ItemTier.DIAMOND));
		}

		if (GlobalValues.MACE) {
			registrationList.add(ReforgedAdditions.WOODEN_MACE = new ItemMace(ItemTier.WOOD));
			registrationList.add(ReforgedAdditions.STONE_MACE = new ItemMace(ItemTier.STONE));
			registrationList.add(ReforgedAdditions.GOLDEN_MACE = new ItemMace(ItemTier.GOLD));
			registrationList.add(ReforgedAdditions.IRON_MACE = new ItemMace(ItemTier.IRON));
			registrationList.add(ReforgedAdditions.DIAMOND_MACE = new ItemMace(ItemTier.DIAMOND));
		}

		if (GlobalValues.DIRK) {
			registrationList.add(ReforgedAdditions.WOODEN_DIRK = new ItemDirk(ItemTier.WOOD));
			registrationList.add(ReforgedAdditions.STONE_DIRK = new ItemDirk(ItemTier.STONE));
			registrationList.add(ReforgedAdditions.GOLDEN_DIRK = new ItemDirk(ItemTier.GOLD));
			registrationList.add(ReforgedAdditions.IRON_DIRK = new ItemDirk(ItemTier.IRON));
			registrationList.add(ReforgedAdditions.DIAMOND_DIRK = new ItemDirk(ItemTier.DIAMOND));
		}

		if (GlobalValues.CANNON) {
			registrationList.add(ReforgedAdditions.CANNON = new ItemCannon());
			registrationList.add(ReforgedAdditions.CANNON_BALL = new Item(new Item.Builder().group(ReforgedMod.tabReforged))
					.setRegistryName(new ResourceLocation(ReforgedMod.ID, "cannon_ball")));
		}
	}

	/**
	 * Helper method for registering an Entity
	 *
	 * @param entityType The type to register
	 */
	public static <T extends Entity> EntityType<T> registerEntity(EntityType<T> entityType) {
		registrationListEntities.add(entityType);
		return entityType;
	}

	/**
	 * Helper method for registering an Entity
	 *
	 * @param entityType The type to register
	 */
	public static <T extends TileEntity> TileEntityType<T> registerTileEntity(TileEntityType<T> entityType) {
		registrationListTileEntities.add(entityType);
		return entityType;
	}

	/**
	 * Helper method for registering our EventHandler
	 *
	 * @param event The instance of our EventHandler
	 */
	public static void registerEventHandler(Object event) {
		MinecraftForge.EVENT_BUS.register(event);
	}

	/**
	 * Helper method for registering an Custom IRecipe
	 *
	 * @param name        The name for the Recipe
	 * @param recipe      The instance of the Recipe
	 * @param recipeclass The class of the Recipe
	 */
	public static void registerIRecipe(String name, IRecipe recipe, Class<?> recipeclass) {
		/*String catString;
		if (category == Category.SHAPELESS) {
			catString = "after:minecraft:shapeless";
		} else if (category == Category.SHAPED) {
			catString = "after:minecraft:shaped";
		} else {
			throw new IllegalArgumentException("The Category called " + category.name() + " couldn't be found!");
		}
		// GameRegistry.addRecipe(recipe);
		RecipeSorter.register(ReforgedMod.ID + ":" + name, recipeclass, category, catString);*/
	}

	/**
	 * Registers all our Packets
	 */
	public static void registerPackets() {
		ReforgedMod.network = NetworkRegistry.newSimpleChannel(new ResourceLocation(ReforgedMod.ID, "reforged_channel"),
				() -> "1.13", (hallo) -> true, (hallo) -> true);
		int packetId = 0;
		ReforgedMod.network.registerMessage(packetId++, MessageCustomReachAttack.class,
				(message, buffer) -> buffer.writeVarInt(message.getEntityId()),
				(buffer) -> new MessageCustomReachAttack(buffer.readVarInt()),
				(message, supplier) -> {
					final EntityPlayerMP player = supplier.get().getSender();
					if (player == null || player.getServer() == null)
						return;
					player.getServer().addScheduledTask(() -> {
						Entity theEntity = player.world.getEntityByID(message.getEntityId());
						if (player.inventory.getCurrentItem().isEmpty() || theEntity == null)
							return;
						if (player.inventory.getCurrentItem().getItem() instanceof ICustomReach) {
							ICustomReach theExtendedReachWeapon = (ICustomReach) player.inventory.getCurrentItem()
									.getItem();
							double distanceSq = player.getDistanceSq(theEntity);
							double reachSq = theExtendedReachWeapon.reach() * theExtendedReachWeapon.reach();
							if (reachSq >= distanceSq) {
								player.attackTargetEntityWithCurrentItem(theEntity);
							}
						}
					});
				});
	}

	@SubscribeEvent
	/** Registers all entities out of the registrationListEntities */
	public void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
		IForgeRegistry<EntityType<?>> reg = event.getRegistry();
		for (EntityType<?> entityType : registrationListEntities) {
			reg.register(entityType);
		}
	}

	@SubscribeEvent
	/** Registers all entities out of the registrationListTileEntities */
	public void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event) {
		IForgeRegistry<TileEntityType<?>> reg = event.getRegistry();
		for (TileEntityType<?> entityType : registrationListTileEntities) {
			reg.register(entityType);
		}
	}

	@SubscribeEvent
	/** Registers all blocks out of the registrationListBlocks */
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> reg = event.getRegistry();
		for (Block block : registrationListBlocks) {
			reg.register(block
					.setRegistryName(new ResourceLocation(ReforgedMod.ID, block.getRegistryName().getPath().substring(5))));
		}
	}

	@SubscribeEvent
	/** Registers all items out of the registrationList */
	public void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> reg = event.getRegistry();
		// Register all Items
		for (Item item : registrationList) {
			reg.register(
					item.setRegistryName(new ResourceLocation(ReforgedMod.ID, item.getRegistryName().getPath().substring(5))));
		}
		for (Block block : registrationListBlocks) {
			ItemBlock itemBlock = new ItemBlock(block, new Item.Builder());
			itemBlock.setRegistryName(block.getRegistryName());
			ReforgedMod.proxy.registerItemRenderer(itemBlock, 0, block.getRegistryName().getPath().substring(5));
			reg.register(itemBlock);
		}

	}

}