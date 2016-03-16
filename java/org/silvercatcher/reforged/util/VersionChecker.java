package org.silvercatcher.reforged.util;

import org.silvercatcher.reforged.ReforgedMod;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class VersionChecker extends Thread {
	
	public static final String curseID = "241392-reforged";
	public static final String curseIntegrationID = "242591-reforged-integration";
	
	@Override
	public void run() {
		if(Loader.isModLoaded("VersionChecker")) {
			NBTTagCompound cpd = new NBTTagCompound();
			cpd.setString("curseProjectName", curseID);
			cpd.setString("curseFilenameParser", "Reforged []-1.8.9");
			cpd.setString("modDisplayName", ReforgedMod.NAME);
			cpd.setString("oldVersion", ReforgedMod.VERSION);
			FMLInterModComms.sendRuntimeMessage(ReforgedMod.ID, "VersionChecker", "addCurseCheck", cpd);
			if(Loader.isModLoaded("ReforgedIntegrationCore")) {
				NBTTagCompound cpd1 = new NBTTagCompound();
				cpd1.setString("curseProjectName", curseIntegrationID);
				cpd1.setString("curseFilenameParser", "1.8.9 Reforged Integration []");
				cpd1.setString("modDisplayName", "Reforged Integration");
				FMLInterModComms.sendRuntimeMessage("ReforgedIntegrationCore", "VersionChecker", "addCurseCheck", cpd1);				
			}
		}
	}
	
}
