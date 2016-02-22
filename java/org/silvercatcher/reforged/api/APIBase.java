package org.silvercatcher.reforged.api;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;

public abstract class APIBase {
	
	/**Saves all the instances of the Integration APIs*/
	public static List<Item> regListItems = new ArrayList<Item>();
	
	/**Saves the name of the mod, that needs to get integrated*/
	protected static String modName;
	
	/**Registers all the Items of the Integration-modules*/
	public abstract void registerItems();
	
	/**Registers all the MaterialDefinitions of the Integration-modules*/
	public abstract void registerMatDefs();
	
	/**@return The name of the mod, which gets integrated*/
	public static String getModName() {
		return modName;
	}
	
	/**Simple Registry for the Item*/
	protected void simpleReg(Item item) {
		regListItems.add(item.setCreativeTab(APIRegistry.tabReforgedIntegration));
	}
}