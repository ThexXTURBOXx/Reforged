package org.silvercatcher.reforged.api;

import java.util.ArrayList;
import java.util.List;

import org.silvercatcher.reforged.ReforgedMod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;

public class InegrationRegistry {
	
	/**All the Dependencies for our mod*/
	public static final String Deps = "after:ProjectE;after:Thaumcraft";
	
	/**Saves all the instances of the Integration APIs*/
	public static final List<IntegrationBase> regList = new ArrayList<IntegrationBase>();
	
	/**The Creative Tab for all Integration-Items*/
    public static CreativeTabs tabReforgedIntegration;
	
	/**Adds all the Integration APIs to the List {@link InegrationRegistry#regList}*/
	public static void addAPIs() {
		if(Loader.isModLoaded(new Thaumcraft().getModName())) {
			regList.add(new Thaumcraft());
		}
		
		if(Loader.isModLoaded(new ProjectE().getModName())) {
			regList.add(new ProjectE());
		}
		
		if(!regList.isEmpty()) {
			tabReforgedIntegration = new CreativeTabs(ReforgedMod.ID + "_integration") {
				@Override
				public Item getTabIconItem() {
					return ReforgedAdditions.IRON_SABER;
				}
			};
		}
	}
}