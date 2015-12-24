package org.silvercatcher.reforged.proxy;

import org.silvercatcher.reforged.ReforgedItems;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		
		ReforgedItems.init();
		ReforgedItems.registerItems();
	}
	
	public void init(FMLInitializationEvent event) {
		
		System.out.println("init --------------------------------- " + event.getSide());
	}
	
	protected void registerRenderers() {}
}
