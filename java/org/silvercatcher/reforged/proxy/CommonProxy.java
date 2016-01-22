package org.silvercatcher.reforged.proxy;

import org.silvercatcher.reforged.ReforgedEvents;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.ReforgedMonsterArmourer;
import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.entities.EntityBulletMusket;
import org.silvercatcher.reforged.entities.EntityJavelin;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		
		//ReforgedRegistry.registerEventHandler(new ReforgedEvents());
		//MinecraftForge.EVENT_BUS.register(new ReforgedMonsterArmourer());
		ReforgedRegistry.createItems();
		ReforgedRegistry.registerItems();
		registerEntities();
		loadConfig(event);
		//Version Checker
		FMLInterModComms.sendRuntimeMessage(ReforgedMod.ID, "VersionChecker", "addVersionCheck",
				"https://raw.githubusercontent.com/TheOnlySilverClaw/Reforged/master/version.json");
	}

	public void init(FMLInitializationEvent event) {
		
		ReforgedRegistry.registerRecipes();
	}
	
	public static int boomerang_distance;
	
	private void loadConfig(FMLPreInitializationEvent e) {
		Configuration config = new Configuration(e.getSuggestedConfigurationFile());
		config.load();
		boomerang_distance = config.getInt("Boomerang-Autocollect", config.CATEGORY_GENERAL, 3, 0, 32, "The distance in which the Boomerang should get auto-collected by the thrower (0 means disable)");
		config.save();
	}
	
	protected void registerItemRenderers() {}
	
	protected void registerEntityRenderers(RenderManager manager) {}
	
	private void registerEntities() {

		int count = 1;
		ReforgedRegistry.registerEntity(EntityBoomerang.class, "Boomerang", count++);
		ReforgedRegistry.registerEntity(EntityJavelin.class, "Javelin", count++);
		ReforgedRegistry.registerEntity(EntityBulletMusket.class, "BulletMusket", count++);
	}
}
