package org.silvercatcher.reforged.proxy;

import org.silvercatcher.reforged.ReforgedItems;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.entities.EntityDiamondBoomerang;
import org.silvercatcher.reforged.entities.EntityGoldenBoomerang;
import org.silvercatcher.reforged.entities.EntityIronBoomerang;
import org.silvercatcher.reforged.entities.EntityStoneBoomerang;
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
		EntityRegistry.registerModEntity(EntityStoneBoomerang.class, "FlyingStoneBoomerang", 2, ReforgedMod.instance, 120, 3, true );
		EntityRegistry.registerModEntity(EntityGoldenBoomerang.class, "FlyingGoldenBoomerang", 3, ReforgedMod.instance, 120, 3, true );
		EntityRegistry.registerModEntity(EntityIronBoomerang.class, "FlyingIronBoomerang", 4, ReforgedMod.instance, 120, 3, true );
		EntityRegistry.registerModEntity(EntityDiamondBoomerang.class, "FlyingDiamondBoomerang", 5, ReforgedMod.instance, 120, 3, true );
	}
}
