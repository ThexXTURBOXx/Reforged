package org.silvercatcher.reforged.util;

import org.silvercatcher.reforged.ReforgedMod;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class VersionChecker extends Thread {
	
	public static final String link = "https://raw.githubusercontent.com/TheOnlySilverClaw/Reforged/master/versionchecker.json";
	
	@Override
	public void run() {
		if(Loader.isModLoaded("VersionChecker")) {
			FMLInterModComms.sendRuntimeMessage(ReforgedMod.ID, "VersionChecker", "addVersionCheck", link);
		}
	}
	
}
