package org.silvercatcher.reforged;

import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class ReforgedEvents {
	
	public static boolean notificated = false;
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent e) {
		if(!notificated) {
			notificated = true;
			TextComponentString s1 = new TextComponentString("§a[§eReforged§a] §cThis is an ALPHA-Release for 1.9");
			TextComponentString s2 = new TextComponentString("§cDon't report any bugs you find in this version!");
			e.player.addChatComponentMessage(s1);
			e.player.addChatComponentMessage(s2);
			MinecraftForge.EVENT_BUS.unregister(this);
		}
	}
	
}
