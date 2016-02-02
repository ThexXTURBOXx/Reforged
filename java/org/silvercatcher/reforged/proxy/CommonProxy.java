package org.silvercatcher.reforged.proxy;

import org.silvercatcher.reforged.ReforgedEvents;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.ReforgedReferences.GlobalValues;
import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.entities.EntityBulletMusket;
import org.silvercatcher.reforged.entities.EntityDart;
import org.silvercatcher.reforged.entities.EntityJavelin;
import org.silvercatcher.reforged.util.VersionChecker;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {

		loadConfig(event);
		ReforgedRegistry.registerEventHandler(new ReforgedEvents());
		//MinecraftForge.EVENT_BUS.register(new ReforgedMonsterArmourer());
		ReforgedRegistry.createItems();
		ReforgedRegistry.registerItems();
		registerEntities();
		//Version Checker
		Thread versionCheckThread = new Thread(new VersionChecker(), "Version Check");
		versionCheckThread.start();
	}

	public void init(FMLInitializationEvent event) {
		
		ReforgedRegistry.registerRecipes();
	}
	
	//Items for Config
	public static boolean arrow_bundle;
	public static boolean battleaxe;
	public static boolean blowgun;
	public static boolean boomerang;
	public static boolean firerod;
	public static boolean holy_cross;
	public static boolean javelin;
	public static boolean knife;
	public static boolean musket;
	public static boolean nest_of_bees;
	public static boolean sabre;
	
	//Others for Config
	public static int boomerang_distance;
	
	private void loadConfig(FMLPreInitializationEvent e) {
		//Get an instance of Config
		Configuration config = new Configuration(e.getSuggestedConfigurationFile());
		
		//Load Config
		config.load();
		
		//Items
		arrow_bundle = config.getBoolean("Arrow Bundle", "Items", true, "Enable the Arrow Bundle");
		battleaxe = config.getBoolean("Battleaxe", "Items", true, "Enable the Battleaxe");
		blowgun = config.getBoolean("Blowgun", "Items", true, "Enable the Blowgun plus Darts");
		boomerang = config.getBoolean("Boomerang", "Items", true, "Enable the Boomerang");
		firerod = config.getBoolean("Firerod", "Items", true, "Enable the Firerod");
		holy_cross = config.getBoolean("Holy Cross", "Items", true, "Enable the Holy Cross");
		javelin = config.getBoolean("Javelin", "Items", true, "Enable the Javelin");
		knife = config.getBoolean("Knife", "Items", true, "Enable the Knife");
		musket = config.getBoolean("Musket", "Items", true, "Enable the Musket and Blunderbuss");
		nest_of_bees = config.getBoolean("Nest Of Bees", "Items", false, "Enable the Nest Of Bees (Beta, Only use for testing!)");
		arrow_bundle = config.getBoolean("Sabre", "Items", true, "Enable the Sabre");
		
		//Others
		boomerang_distance = config.getInt("Boomerang-Autocollect", "Others", 3, 0, 32, "The distance in which the Boomerang should get auto-collected by the thrower (0 means disable)");
		
		//Save config
		config.save();
	}
	
	protected void registerItemRenderers() {}
	
	protected void registerEntityRenderers(RenderManager manager) {}
	
	private void registerEntities() {

		int count = 1;
		
		if(GlobalValues.BOOMERANG) {
			ReforgedRegistry.registerEntity(EntityBoomerang.class, "Boomerang", count++);
		}
		
		if(GlobalValues.JAVELIN) {
			ReforgedRegistry.registerEntity(EntityJavelin.class, "Javelin", count++);
		}
		
		if(GlobalValues.MUSKET) {
			ReforgedRegistry.registerEntity(EntityBulletMusket.class, "BulletMusket", count++);
		}
		
		if(GlobalValues.BLOWGUN) {
			ReforgedRegistry.registerEntity(EntityDart.class, "Dart", count++);
		}	
	}
}
