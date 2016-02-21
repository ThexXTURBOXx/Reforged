package org.silvercatcher.reforged.api;

public abstract class APIBase {
	
	/**Saves the name of the mod, that needs to get integrated*/
	protected String modName;
	
	/**Registers all the Items of the Integration-modules*/
	public abstract void registerItems();
	
	/**Registers all the Items of the Integration-modules*/
	public abstract void registerMatDefs();
	
	/**@return The name of the mod, which gets integrated*/
	public String getModName() {
		return modName;
	}	
}
