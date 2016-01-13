package org.silvercatcher.reforged.proxy;

import org.silvercatcher.reforged.ReforgedItems;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.entities.EntityBulletMusket;
import org.silvercatcher.reforged.entities.EntityJavelin;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		
		ReforgedItems.createItems();
		ReforgedItems.registerItems();
		registerEntities();
		//Version Checker
		FMLInterModComms.sendRuntimeMessage(ReforgedMod.ID, "VersionChecker", "addVersionCheck",
				"https://raw.githubusercontent.com/ThexXTURBOXx/Reforged/master/version.json");
	}

	public void init(FMLInitializationEvent event) {
		
		ReforgedItems.registerRecipes();
	}
	
	protected void registerItemRenderers() {}
	
	protected void registerEntityRenderers() {}
	
	private void registerEntities() {
		int encount = 1;
		EntityRegistry.registerModEntity(EntityBoomerang.class, "Boomerang", encount++, ReforgedMod.instance, 120, 3, true);
		EntityRegistry.registerModEntity(EntityJavelin.class, "Javelin", encount++, ReforgedMod.instance, 120, 3, true);
		EntityRegistry.registerModEntity(EntityBulletMusket.class, "BulletMusket", encount++, ReforgedMod.instance, 120, 3, true);

	}
}
