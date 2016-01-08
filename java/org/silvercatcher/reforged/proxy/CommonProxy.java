package org.silvercatcher.reforged.proxy;

import org.silvercatcher.reforged.ReforgedItems;
import org.silvercatcher.reforged.ReforgedMod;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		
		ReforgedItems.createItems();
		ReforgedItems.registerItems();
	}
	
	public void init(FMLInitializationEvent event) {
		
		ReforgedItems.registerRecipes();
	}
	
	protected void registerRenderers() {}
	public void generateMysteriousParticles(Entity theEntity) {
		
	}
}
