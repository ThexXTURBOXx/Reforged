package org.silvercatcher.reforged.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.ReforgedReferences.GlobalValues;

public class VersionChecker implements Runnable {
  private static boolean isLatestVersion = false;
  private static String latestVersion = "";
  
  public void run()
  {
    InputStream in = null;
    try {
      in = new URL("https://raw.githubusercontent.com/TheOnlySilverClaw/Reforged/master/version.json").openStream();
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
    		  s = s.replace(" ", "");
    		  s = s.replace("	", "");
    		  s = s.replace(":", "");
			  latestVersion = s;
    		  if(!s.equals(ReforgedMod.VERSION)) {
    			  System.out.println("Newer version of the mod Reforged available: " + s);
    		  } else {
    			  System.out.println("Yay! You have the newest version of the mod Reforged :)");
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
    return latestVersion;
  }
}
