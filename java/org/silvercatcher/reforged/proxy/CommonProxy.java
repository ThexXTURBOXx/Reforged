package org.silvercatcher.reforged.proxy;

import java.io.File;

import org.silvercatcher.reforged.*;
import org.silvercatcher.reforged.ReforgedReferences.GlobalValues;
import org.silvercatcher.reforged.entities.*;
import org.silvercatcher.reforged.util.VersionChecker;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		
		loadConfig(event);
		ReforgedRegistry.registerEventHandler(new ReforgedEvents());
		ReforgedRegistry.registerEventHandler(new ReforgedMonsterArmourer());
		ReforgedRegistry.createItems();
		ReforgedRegistry.registerItems();
		ReforgedRegistry.registerPackets();
		registerEntities();
		Thread versionCheck = new VersionChecker();
		versionCheck.start();
	}
	
	public void init(FMLInitializationEvent event) {
		
		ReforgedRegistry.registerRecipes();
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
	mace;
	
	//IDs
	public static int goalseekerid;
	
	private void loadConfig(FMLPreInitializationEvent e) {
		File configdir = new File(e.getModConfigurationDirectory(), ReforgedMod.NAME);
		File configfile = new File(configdir, "reforged.cfg");
		if(!configfile.exists()) configdir.mkdirs();
		//Get an instance of Config
		Configuration config = new Configuration(configfile);
		
		//Load Config
		config.load();
		
		//Items
		battleaxe = config.getBoolean("Battleaxe", "Items", true, "Enable the Battleaxe");
		blowgun = config.getBoolean("Blowgun", "Items", true, "Enable the Blowgun plus Darts");
		boomerang = config.getBoolean("Boomerang", "Items", true, "Enable the Boomerang");
		firerod = config.getBoolean("Firerod", "Items", true, "Enable the Firerod");
		javelin = config.getBoolean("Javelin", "Items", true, "Enable the Javelin");
		katana= config.getBoolean("Katana", "Items", true, "Enable the Katana");
		knife = config.getBoolean("Knife", "Items", true, "Enable the Knife");
		musket = config.getBoolean("Musket", "Items", true, "Enable the Musket and Blunderbuss");
		nest_of_bees = config.getBoolean("Nest Of Bees", "Items", true, "Enable the Nest Of Bees");
		sabre = config.getBoolean("Sabre", "Items", true, "Enable the Sabre");
		keris = config.getBoolean("Kris", "Items", true, "Enable the Kris");
		caltrop = config.getBoolean("Caltrop", "Items", true, "Enable the Caltrop");
		dynamite = config.getBoolean("Dynamite", "Items", true, "Enable the Dynamite");
		crossbow = config.getBoolean("Crossbow", "Items", true, "Enable the Crossbow plus Bolt");
		pike = config.getBoolean("Pike", "Items", true, "Enable the Pike");
		mace = config.getBoolean("Mace", "Items", true, "Enable the Mace");
		
		//IDs
		goalseekerid = config.getInt("Goalseeker", "IDs", 100, 0, 256, "This specifies the Enchantment ID of the Goalseeker-Enchantment");
		
		//Save config
		config.save();
	}
	
	protected void registerItemRenderers() {}
	
	protected void registerEntityRenderers(RenderManager manager) {}
	
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
}
