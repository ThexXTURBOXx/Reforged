package org.silvercatcher.reforged;

import org.silvercatcher.reforged.util.VersionChecker;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.registry.LanguageRegistry;

public class ReforgedEvents {	
	/*
	//Mod integration
	//Copper integration
	@SubscribeEvent
	public void onOreRegistryReg(OreRegisterEvent e) {
		if(e.Name.equalsIgnoreCase("ingotcopper")) {
			GameRegistry.registerItem(ReforgedRegistry.COPPER_BOOMERANG, ReforgedRegistry.COPPER_BOOMERANG.getName());
			GameRegistry.registerItem(ReforgedRegistry.COPPER_BATTLE_AXE, ReforgedRegistry.COPPER_BATTLE_AXE.getName());
		}
	}
	*/
	
	public boolean notificated = false;
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent e) {
		//Version Checker Notification
		if(!notificated) {
			notificated = true;
			if(!VersionChecker.isLatestVersion()) {
				EntityPlayer p = e.player;
				ChatStyle gold = new ChatStyle().setColor(EnumChatFormatting.GOLD);
				IChatComponent chat = new ChatComponentText("");
				chat.setChatStyle(gold);
				chat.appendText("[" + ReforgedMod.NAME + "] ");
				chat.appendText(new LanguageRegistry().instance().getStringLocalization("versionchecker.ingame.outdated") + ": " + VersionChecker.getLatestVersion());
				p.addChatMessage(chat);
				IChatComponent chat2 = new ChatComponentText("");
				ChatStyle link = new ChatStyle();
				link.setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, VersionChecker.getDownloadUrl()));
				link.setColor(EnumChatFormatting.AQUA);
				chat2.appendText(new LanguageRegistry().instance().getStringLocalization("versionchecker.ingame.downloadclick") + ": ").setChatStyle(gold);
				chat2.appendText("[" + new LanguageRegistry().instance().getStringLocalization("versionchecker.ingame.download") + "]").setChatStyle(link);
				p.addChatComponentMessage(chat2);
			}
		}
	}
}
