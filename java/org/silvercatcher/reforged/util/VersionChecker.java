package org.silvercatcher.reforged.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.ReforgedReferences.GlobalValues;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class VersionChecker implements Runnable {
  private static boolean isLatestVersion = true;
  private static String latestVersion = "";
  private static String downloadUrl = "http://minecraft.curseforge.com/projects/reforged";
  private static String jsonUrl = "https://raw.githubusercontent.com/TheOnlySilverClaw/Reforged/master/version.json";
  
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
    		  if(!s.equals(ReforgedMod.VERSION)) {
    			  System.out.println("Newer version of the mod Reforged available: " + s);
    		  } else {
    			  System.out.println("Yay! You have the newest version of the mod Reforged :)");
    		  }
    		  if (Loader.isModLoaded("VersionChecker")) {
  				NBTTagCompound compound = new NBTTagCompound();
  				compound.setString("modDisplayName", ReforgedMod.NAME);
  				compound.setString("oldVersion", ReforgedMod.VERSION);
  				compound.setString("newVersion", latestVersion);
  				compound.setString("changeLog", getChangelog());
  				if (downloadUrl != null) {
  					compound.setString("updateUrl", downloadUrl);
  					compound.setBoolean("isDirectLink", false);
  				}
  				FMLInterModComms.sendRuntimeMessage(ReforgedMod.ID, "VersionChecker", "addUpdate", compound);
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
    		  s = s.replace("\n", enter);
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
}
