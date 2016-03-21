package org.silvercatcher.reforged.proxy;

import java.io.File;

import org.silvercatcher.reforged.ReforgedEvents;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.ReforgedReferences.GlobalValues;
import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.entities.EntityBulletBlunderbuss;
import org.silvercatcher.reforged.entities.EntityBulletMusket;
import org.silvercatcher.reforged.entities.EntityDart;
import org.silvercatcher.reforged.entities.EntityDynamite;
import org.silvercatcher.reforged.entities.EntityJavelin;
import org.silvercatcher.reforged.entities.TileEntityCaltropEntity;
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
		//MinecraftForge.EVENT_BUS.register(new ReforgedMonsterArmourer());
		ReforgedRegistry.createItems();
		ReforgedRegistry.registerItems();
		registerEntities();
		Thread versionCheck = new VersionChecker();
		versionCheck.start();
	}
	
	public void init(FMLInitializationEvent event) {
		
		ReforgedRegistry.registerRecipes();
	}
	
	//Items for Config
	public static boolean battleaxe;
	public static boolean blowgun;
	public static boolean boomerang;
	public static boolean firerod;
	public static boolean javelin;
	public static boolean katana;
	public static boolean knife;
	public static boolean musket;
	public static boolean nest_of_bees;
	public static boolean sabre;
	public static boolean keris;
	public static boolean caltrop;
	public static boolean dynamite;
	
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
		nest_of_bees = config.getBoolean("Nest Of Bees", "Items", false, "Enable the Nest Of Bees (BETA, only use for testing!)");
		sabre = config.getBoolean("Sabre", "Items", true, "Enable the Sabre");
		keris = config.getBoolean("Kris", "Items", true, "Enable the Kris");
		caltrop = config.getBoolean("Caltrop", "Items", true, "Enable the Caltrop");
		dynamite = config.getBoolean("Dynamite", "Items", true, "Enable the Dynamite");
		
		
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
	}
}
