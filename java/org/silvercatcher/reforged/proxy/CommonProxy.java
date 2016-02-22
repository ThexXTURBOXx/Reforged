package org.silvercatcher.reforged.proxy;

import org.silvercatcher.reforged.ReforgedEvents;
import org.silvercatcher.reforged.ReforgedReferences.GlobalValues;
import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.entities.EntityBulletBlunderbuss;
import org.silvercatcher.reforged.entities.EntityBulletMusket;
import org.silvercatcher.reforged.entities.EntityDart;
import org.silvercatcher.reforged.entities.EntityJavelin;
import org.silvercatcher.reforged.util.VersionChecker;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		
		loadConfig(event);
		ReforgedRegistry.registerEventHandler(new ReforgedEvents());
		//MinecraftForge.EVENT_BUS.register(new ReforgedMonsterArmourer());
		ReforgedRegistry.createItems();
		ReforgedRegistry.registerItems();
		registerEntities();
		Thread versionCheckThread = new Thread(new VersionChecker(), "Version Check");
		versionCheckThread.start();
	}
	
	public void init(FMLInitializationEvent event) {
		
		ReforgedRegistry.registerRecipes();
	}
	
	//Items for Config
	public static boolean battleaxe;
	public static boolean blowgun;
	public static boolean boomerang;
	public static boolean firerod;
	public static boolean holy_cross;
	public static boolean javelin;
	public static boolean katana;
	public static boolean knife;
	public static boolean musket;
	public static boolean nest_of_bees;
	public static boolean sabre;
	public static boolean keris;
	public static boolean integration;
	
	//Others for Config
	public static boolean version_checker;
	
	private void loadConfig(FMLPreInitializationEvent e) {
		//Get an instance of Config
		Configuration config = new Configuration(e.getSuggestedConfigurationFile());
		
		//Load Config
		config.load();
		
		//Items
		battleaxe = config.getBoolean("Battleaxe", "Items", true, "Enable the Battleaxe");
		blowgun = config.getBoolean("Blowgun", "Items", true, "Enable the Blowgun plus Darts");
		boomerang = config.getBoolean("Boomerang", "Items", true, "Enable the Boomerang");
		firerod = config.getBoolean("Firerod", "Items", true, "Enable the Firerod");
		holy_cross = config.getBoolean("Holy Cross", "Items", true, "Enable the Holy Cross");
		javelin = config.getBoolean("Javelin", "Items", true, "Enable the Javelin");
		katana= config.getBoolean("Katana", "Items", true, "Enable the Katana");
		knife = config.getBoolean("Knife", "Items", true, "Enable the Knife");
		musket = config.getBoolean("Musket", "Items", true, "Enable the Musket and Blunderbuss");
		nest_of_bees = config.getBoolean("Nest Of Bees", "Items", false, "Enable the Nest Of Bees (BETA, only use for testing!)");
		sabre = config.getBoolean("Sabre", "Items", true, "Enable the Sabre");
		keris = config.getBoolean("Kris", "Items", true, "Enable the Kris");
		integration = config.getBoolean("Mod-Integration", "Items", true, "Enable Integration of Mods");
		
		//Others
		version_checker = config.getBoolean("Version Checker", "General", true, "Enable the Version Checker");		
		
		//Save config
		config.save();
	}
	
	protected void registerItemRenderers() {}
	
	protected void registerEntityRenderers(RenderManager manager) {}
	
	private void registerEntities() {
		
		if(GlobalValues.BOOMERANG) {
			ReforgedRegistry.registerEntity(EntityBoomerang.class, "Boomerang");
		}
		
		if(GlobalValues.JAVELIN) {
			ReforgedRegistry.registerEntity(EntityJavelin.class, "Javelin");
		}
		
		if(GlobalValues.MUSKET) {
			ReforgedRegistry.registerEntity(EntityBulletMusket.class, "BulletMusket");
			ReforgedRegistry.registerEntity(EntityBulletBlunderbuss.class, "BulletBlunderbuss");
		}
		
		if(GlobalValues.BLOWGUN) {
			ReforgedRegistry.registerEntity(EntityDart.class, "Dart");
		}	
	}
}
