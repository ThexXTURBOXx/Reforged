package org.silvercatcher.reforged.proxy;

import org.silvercatcher.reforged.ReforgedItems;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.entities.EntityWoodenBoomerang;
import org.silvercatcher.reforged.weapons.ItemBoomerang;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	ItemBoomerang boomerang;

	public void preInit(FMLPreInitializationEvent event) {
		
		ReforgedItems.createItems();
		ReforgedItems.registerItems();
		registerEntities();
		//Version Checker
		FMLInterModComms.sendRuntimeMessage(ReforgedMod.ID, "VersionChecker", "addVersionCheck", "https://raw.githubusercontent.com/ThexXTURBOXx/Reforged/master/version.json");
	}

	public void init(FMLInitializationEvent event) {
		
		ReforgedItems.registerRecipes();
	}
	
	protected void registerRenderers() {}
	
	private void registerEntities() {
		EntityRegistry.registerModEntity(EntityWoodenBoomerang.class, "FlyingWoodenBoomerang", 1, ReforgedMod.instance, 120, 3, true );
	}
}
