package org.silvercatcher.reforged.proxy;

import java.io.File;

import org.silvercatcher.reforged.*;
import org.silvercatcher.reforged.ReforgedReferences.GlobalValues;
import org.silvercatcher.reforged.entities.*;
import org.silvercatcher.reforged.props.*;
import org.silvercatcher.reforged.util.VersionChecker;

import net.minecraft.item.Item;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		loadConfig(event);
		ReforgedRegistry.registerEventHandler(new ReforgedEvents());
		ReforgedRegistry.registerEventHandler(new ReforgedMonsterArmourer());
		ReforgedRegistry.createItems();
		ReforgedRegistry.registerItems();
		ReforgedRegistry.registerPackets();
		CapabilityManager.INSTANCE.register(IStunProperty.class, new StorageStun(), DefaultStunImpl.class);
		registerEntities();
		Thread versionCheck = new VersionChecker();
		versionCheck.start();
	}
	
	public void init(FMLInitializationEvent event) {
		ReforgedRegistry.registerRecipes();
		GameRegistry.registerTileEntity(TileEntityCaltropEntity.class, "caltrop");
	}
	
	public void postInit(FMLPostInitializationEvent event) {
        ReforgedMod.battlegearDetected = Loader.isModLoaded("battlegear2");
	}
	
	//Items for Config
	public static boolean battleaxe,
	blowgun,
	boomerang,
	firerod,
	javelin,
	katana,
	knife,
	musket,
	nest_of_bees,
	sabre,
	keris,
	caltrop,
	dynamite,
	crossbow,
	pike,
	mace,
	dirk;
	
	//IDs
	public static int goalseekerid;
	
	public static final String items = "Items";
	
	private void loadConfig(FMLPreInitializationEvent e) {
		File configdir = new File(e.getModConfigurationDirectory(), ReforgedMod.NAME);
		File configfile = new File(configdir, "reforged.cfg");
		if(!configfile.exists()) configdir.mkdirs();
		//Get an instance of Config
		Configuration config = new Configuration(configfile);
		
		//Load Config
		config.load();
		
		//Items
		battleaxe = config.getBoolean("Battleaxe", items, true, "Enable the Battleaxe");
		blowgun = config.getBoolean("Blowgun", items, true, "Enable the Blowgun plus Darts");
		boomerang = config.getBoolean("Boomerang", items, true, "Enable the Boomerang");
		firerod = config.getBoolean("Firerod", items, true, "Enable the Firerod");
		javelin = config.getBoolean("Javelin", items, true, "Enable the Javelin");
		katana= config.getBoolean("Katana", items, true, "Enable the Katana");
		knife = config.getBoolean("Knife", items, true, "Enable the Knife");
		musket = config.getBoolean("Musket", items, true, "Enable the Musket and Blunderbuss");
		nest_of_bees = config.getBoolean("Nest Of Bees", items, true, "Enable the Nest Of Bees");
		sabre = config.getBoolean("Sabre", items, true, "Enable the Sabre");
		keris = config.getBoolean("Kris", items, true, "Enable the Kris");
		caltrop = config.getBoolean("Caltrop", items, true, "Enable the Caltrop");
		dynamite = config.getBoolean("Dynamite", items, true, "Enable the Dynamite");
		crossbow = config.getBoolean("Crossbow", items, true, "Enable the Crossbow plus Bolt");
		pike = config.getBoolean("Pike", items, true, "Enable the Pike");
		mace = config.getBoolean("Mace", items, true, "Enable the Mace");
		dirk = config.getBoolean("Dirk", items, true, "Enable the Dirk");
		
		//IDs
		goalseekerid = config.getInt("Goalseeker", "IDs", 100, 0, 256, "This specifies the Enchantment ID of the Goalseeker-Enchantment");
		
		//Save config
		config.save();
	}
	
	protected void registerItemRenderers() {}
	
	protected void registerEntityRenderers() {}
	
	private void registerEntities() {
		
		if(GlobalValues.BOOMERANG) ReforgedRegistry.registerEntity(EntityBoomerang.class, "Boomerang");
		if(GlobalValues.JAVELIN) ReforgedRegistry.registerEntity(EntityJavelin.class, "Javelin");
		
		if(GlobalValues.MUSKET) {
			ReforgedRegistry.registerEntity(EntityBulletMusket.class, "BulletMusket");
			ReforgedRegistry.registerEntity(EntityBulletBlunderbuss.class, "BulletBlunderbuss");
		}
		
		if(GlobalValues.BLOWGUN) ReforgedRegistry.registerEntity(EntityDart.class, "Dart");
		if(GlobalValues.CALTROP) GameRegistry.registerTileEntity(TileEntityCaltropEntity.class, "Caltrop");
		if(GlobalValues.DYNAMITE) ReforgedRegistry.registerEntity(EntityDynamite.class, "Dynamite");
		if(GlobalValues.CROSSBOW) ReforgedRegistry.registerEntity(EntityCrossbowBolt.class, "BoltCrossbow");
	}
	
	public void registerItemRenderer(Item item, int meta, String id) {}
	
}
