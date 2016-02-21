package org.silvercatcher.reforged.api;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.common.Loader;

public class APIRegistry {
	
	/**Saves all the instances of the Integration APIs*/
	public static final List<APIBase> regList = new ArrayList<APIBase>();
	
	/**Adds all the Integration APIs to the List {@link APIRegistry#regList}*/
	public void addAPIs() {
		regList.add(new Thaumcraft());
	}
	
	/**Registers all the Integration APIs' Items*/
	public void registerAPIItems(APIBase ab) {
		ab.registerItems();
	}
	
	/**Registers all the Integration APIs' MaterialDefinitions*/
	public void registerAPIMatDefs(APIBase ab) {
		ab.registerMatDefs();
	}
	
	/**Master method for {@link APIRegistry#addAPIs()}, {@link APIRegistry#registerDefMaps()} and {@link APIRegistry#registerAPIs()}*/
	public void register() {
		addAPIs();		
		for(APIBase apibase : regList) {
			if(Loader.isModLoaded(apibase.modName)) {
				registerAPIMatDefs(apibase);
				registerAPIItems(apibase);
			}
		}		
	}
}