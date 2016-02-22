package org.silvercatcher.reforged;

import org.silvercatcher.reforged.api.ReforgedReferences.GlobalValues;
import org.silvercatcher.reforged.util.VersionChecker;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class ReforgedEvents {	
	
	/**Says whether the player got already notified by the Version Checker message*/
	public boolean notificated = false;
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent e) {
		//Version Checker Chat Notification
		if(!notificated) {
			notificated = true;
			if(!VersionChecker.isLatestVersion() && GlobalValues.VERSION_CHECKER) {
				EntityPlayer p = e.player;
				ChatStyle version = new ChatStyle().setColor(EnumChatFormatting.AQUA);
				ChatStyle modname = new ChatStyle();
				ChatStyle download = new ChatStyle().setColor(EnumChatFormatting.GREEN);
				ChatStyle tooltip = new ChatStyle().setColor(EnumChatFormatting.YELLOW);
				ChatStyle data = modname.createShallowCopy();
				ChatStyle data1 = download.createShallowCopy();
				IChatComponent msg = new ChatComponentTranslation("versionchecker.ingame.downloadclick").setChatStyle(tooltip);
				download.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, msg));
				IChatComponent chat = new ChatComponentText("");
				IChatComponent msg1 = new ChatComponentText("Installed: " + ReforgedMod.VERSION).setChatStyle(version);
				data.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, msg1));
				String ModName = ReforgedMod.NAME;
				if(VersionChecker.isBeta()) ModName = ModName + " BETA";
				chat.appendSibling(new ChatComponentText("§6[§b" + ModName + "§6] ").setChatStyle(data));
				chat.appendSibling(new ChatComponentTranslation("versionchecker.ingame.outdated").setChatStyle(data));
				chat.appendText(": ");
				chat.appendText("§d" + VersionChecker.getLatestVersion());
				p.addChatMessage(chat);
				chat = new ChatComponentText("");
				chat.appendText("§6[");
				data1.setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, VersionChecker.getDownloadUrl()));
				chat.appendSibling(new ChatComponentTranslation("versionchecker.ingame.download").setChatStyle(data1));
				chat.appendText("§6]");
				p.addChatMessage(chat);
			}
		}
	}
}
