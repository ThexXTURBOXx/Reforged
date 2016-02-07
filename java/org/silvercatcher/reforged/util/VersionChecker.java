package org.silvercatcher.reforged.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Logger;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.ReforgedReferences.GlobalValues;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class VersionChecker implements Runnable {
  private static boolean isLatestVersion = true;
  private static String latestVersion = "";
  private static String downloadUrl = "http://minecraft.curseforge.com/projects/reforged-balkons-weapons-1-8";
  private static String jsonUrl = "https://raw.githubusercontent.com/TheOnlySilverClaw/Reforged/master/version.json";
  private Logger log = GlobalValues.log;
  
  @Override
	public void run()
	  {
	    InputStream in = null;
	    try {
	      in = new URL(jsonUrl).openStream();
	    } catch (MalformedURLException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    
	    try {
	      for(String s : IOUtils.readLines(in)) {
	    	  if(s.contains("modVersion")) {
	    		  s = s.replace("modVersion", "");
	    		  s = s.replace("\"", "");
	    		  s = s.replace(",", "");
	    		  s = s.replace("	", "");
	    		  s = s.replace(":", "");
				  latestVersion = s;
	    		  	if(!s.equalsIgnoreCase(ReforgedMod.VERSION)) {
	    		  		log.info("Newer version of " + ReforgedMod.NAME + " available: " + s, new Object[0]);
	    		  		sendToVersionCheckMod();
	    		  	} else {
	    		  		log.info("Yay! You have the newest version of " + ReforgedMod.NAME + " :)", new Object[0]);
	    		  	}
	    	  }
	      }
	    } catch (IOException e) {
	      e.printStackTrace();
	    } finally {
	      IOUtils.closeQuietly(in);
	    }
	    isLatestVersion = ReforgedMod.VERSION.equals(latestVersion);
	  }
	  
	  public static boolean isLatestVersion() {
		  return isLatestVersion;
	  }
	  
	  public static String getLatestVersion() {
		  if(latestVersion == "") {
			  latestVersion = ReforgedMod.VERSION;
		  }
		  return latestVersion;
	  }
	  
	  public static String getDownloadUrl() {
		  return downloadUrl;
	  }
	  
	  public static String getChangelog() {
		String changelog = "";
	    InputStream in = null;
	    try {
	      in = new URL(jsonUrl).openStream();
	    } catch (MalformedURLException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    
	    try {
	      for(String s : IOUtils.readLines(in)) {
	    	  if(s.contains("changeLog")) {
	    		  String enter = System.getProperty("line.separator");
	    		  s = s.replace("changeLog", "");
	    		  s = s.replace("\"", "");
	    		  s = s.replace(",", "");
	    		  s = s.replace("	", "");
	    		  s = s.replace(":", "");
	    		  s = s.replace("/n", enter);
				  changelog = s;
	    	  }
	      }
	    } catch (IOException e) {
	      e.printStackTrace();
	    } finally {
	      IOUtils.closeQuietly(in);
	    }
	    return changelog;
	  }
	  
	  public void sendToVersionCheckMod() {
		  //Sending version to Version Checker Mod by Dynious, if it's loaded
		  if (Loader.isModLoaded("VersionChecker")) {
			  NBTTagCompound compound = new NBTTagCompound();
			  compound.setString("modDisplayName", ReforgedMod.NAME);
			  compound.setString("oldVersion", ReforgedMod.VERSION);
			  compound.setString("newVersion", latestVersion);
			  compound.setString("changeLog", getChangelog());
			  compound.setString("updateUrl", downloadUrl);
			  compound.setBoolean("isDirectLink", false);
			  FMLInterModComms.sendRuntimeMessage(ReforgedMod.ID, "VersionChecker", "addUpdate", compound);
			  }
		  }	  
}
