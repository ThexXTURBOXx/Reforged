package org.silvercatcher.reforged.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.ReforgedReferences.GlobalValues;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

/**Thread of our Version Checker*/
public class VersionChecker implements Runnable {
  private static boolean isLatestVersion = true;
  private static String latestVersion = "";
  private static String downloadUrl = "";
  /**The URL to our json containing the latest version and changelog*/
  private static String jsonUrl = "https://raw.githubusercontent.com/TheOnlySilverClaw/Reforged/master/version.json";
  private static boolean beta = ReforgedMod.BETA;
  private Logger log = GlobalValues.log;

  /**Let the Version Checker run*/
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
	    	  	if(beta) {
	    	  		//Only released on GitHub
	    	  		if(s.contains("betaVersion")) {
	    	  			s = s.substring(0, s.indexOf("|"));
	    	  			s = s.replace("betaVersion", "");
	    	    	  	s = s.replace("\"", "");
	    	    	  	s = s.replace(",", "");
	    	    	  	s = s.replace("	", "");
	    	    	  	s = s.replace(":", "");
	    	    	  	latestVersion = s;
	    	  		}
	    	  	} else {
	    	  		//Released on GitHub and CurseForge
	    	  		if(s.contains("modVersion")) {
	    	  			s = s.substring(0, s.indexOf("|"));
	    	  			s = s.replace("modVersion", "");
	    	    	  	s = s.replace("\"", "");
	    	    	  	s = s.replace(",", "");
	    	    	  	s = s.replace("	", "");
	    	    	  	s = s.replace(":", "");
	    	    	  	latestVersion = s;
	    	  		}
	    	  	}
	    	}
		    if(beta) {
		    	//Only released on GitHub
		    	downloadUrl = "https://github.com/TheOnlySilverClaw/Reforged/releases";
		    } else {
	    		  //Released on GitHub and CurseForge
		    	downloadUrl = "http://minecraft.curseforge.com/projects/reforged-balkons-weapons-1-8";
		    }
		  	if(!latestVersion.equalsIgnoreCase(ReforgedMod.VERSION)) {
		  		log.info("Newer version of " + ReforgedMod.NAME + " available: " + latestVersion, new Object[0]);
		  		sendToVersionCheckMod();
		  	} else {
		  		log.info("Yay! You have the newest version of " + ReforgedMod.NAME + " :)", new Object[0]);
		  	}
	    } catch (IOException e) {
	      e.printStackTrace();
	      log.log(Level.WARN, AbstractLogger.CATCHING_MARKER, "Update check for " + ReforgedMod.NAME + " failed.", e);
	    } finally {
	      IOUtils.closeQuietly(in);
	    }
	    isLatestVersion = ReforgedMod.VERSION.equals(latestVersion);
	  }
	  
  	  /**@return whether Reforged is up-to-date or not*/
	  public static boolean isLatestVersion() {
		  return isLatestVersion;
	  }
	  
	  /**@return if true, the update is only available on GitHub; if false, then on CurseForge and GitHub*/
	  public static boolean isBeta() {
		  return beta;
	  }
	  
	  /**@return the latest version available*/
	  public static String getLatestVersion() {
		  if(latestVersion == "") {
			  latestVersion = ReforgedMod.VERSION;
		  }
		  return latestVersion;
	  }
	  
	  /**@return the Download-URL of Reforged*/
	  public static String getDownloadUrl() {
		  return downloadUrl;
	  }
	  
	  /**@return the current changelog of Reforged*/
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
    		  String enter = System.getProperty("line.separator");
	    	  if(beta) {
	    		  //Only released on GitHub
		    	  if(s.contains("betaLog")) {
		    		  s = s.replace("betaLog", "");
		    		  s = s.replace("\"", "");
		    		  s = s.replace(",", "");
		    		  s = s.replace("	", "");
		    		  s = s.replace(":", "");
		    		  s = s.replace("/n", enter);
					  changelog = s;
		    	  }	    		  
	    	  } else {
	    		  //Released on GitHub and CurseForge
		    	  if(s.contains("changeLog")) {
		    		  s = s.replace("changeLog", "");
		    		  s = s.replace("\"", "");
		    		  s = s.replace(",", "");
		    		  s = s.replace("	", "");
		    		  s = s.replace(":", "");
		    		  s = s.replace("/n", enter);
					  changelog = s;
		    	  }
	    	  }
	      }
	    } catch (IOException e) {
	      e.printStackTrace();
	    } finally {
	      IOUtils.closeQuietly(in);
	    }
	    return changelog;
	  }

	  /**Sending version to the Version Checker Mod by Dynious, if it's loaded*/
	  public void sendToVersionCheckMod() {
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
