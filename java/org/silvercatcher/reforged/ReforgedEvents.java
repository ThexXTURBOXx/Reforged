package org.silvercatcher.reforged;

import java.util.*;

import org.silvercatcher.reforged.api.ICustomReach;
import org.silvercatcher.reforged.api.ItemExtension;
import org.silvercatcher.reforged.packet.MessageCustomReachAttack;
import org.silvercatcher.reforged.props.StunProperty;
import org.silvercatcher.reforged.util.Helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ReforgedEvents {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void customReach(MouseEvent e) {
		if (e.button == 0 && e.buttonstate) {
			Minecraft mc = Minecraft.getMinecraft();
			if (mc.thePlayer.getCurrentEquippedItem() != null) {
				Item i = mc.thePlayer.getCurrentEquippedItem().getItem();
				if (i instanceof ICustomReach && i instanceof ItemExtension) {
					ICustomReach icr = (ICustomReach) i;
					Entity hit = Helpers.getMouseOverExtended(icr.reach()).entityHit;
					if (hit != null && mc.objectMouseOver.entityHit == null && hit != mc.thePlayer) {
						ReforgedMod.network.sendToServer(new MessageCustomReachAttack(hit.getEntityId()));
					}
				}
			}
		}
	}

	public static boolean notified = false;
	public static Map<UUID, Integer> map;

	static {
		map = new HashMap<UUID, Integer>();
	}

	@SubscribeEvent
	public void onTick(PlayerTickEvent e) {
		if (e.side == Side.CLIENT)
			notified = true;
		if (!notified) {
			notified = true;
			EntityPlayer p = e.player;
			String par = "\u00A7";
			if (ReforgedMod.battlegearDetected) {
				p.addChatMessage(new ChatComponentText(par + "7[" + par + "bReforged" + par + "7] " + par
						+ "cYou have \"Mine & Blade Battlegear 2 - Bullseye\" " + par + "cinstalled."));
				p.addChatMessage(new ChatComponentText(
						"" + par + "cPatch loaded, because it has incompatibility issues with Reforged."));
				p.addChatMessage(new ChatComponentText(
						"" + par + "cSome Weapons will act different (but they hopefully work)!"));
			}
		}
	}

	@SubscribeEvent
	public void onWorldTick(WorldTickEvent e) {
		for (Entity en : ((List<Entity>) e.world.loadedEntityList)) {
			if (en instanceof EntityLivingBase) {
				EntityLivingBase player = (EntityLivingBase) en;
				if (StunProperty.get(player).isStunned()) {
					if (!map.containsKey(player.getUniqueID()))
						map.put(player.getUniqueID(), 0);
					int i = map.get(player.getUniqueID());
					i++;
					if (player.lastTickPosX != player.posX)
						player.posX = player.lastTickPosX;
					if (player.lastTickPosY != player.posY)
						player.posY = player.lastTickPosY;
					if (player.lastTickPosZ != player.posZ)
						player.posZ = player.lastTickPosZ;
					map.put(player.getUniqueID(), i);
					if (map.get(player.getUniqueID()) >= 60) {
						StunProperty.get(player).setStunned(false);
						map.put(player.getUniqueID(), 0);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityLivingBase && StunProperty.get((EntityLivingBase) event.entity) == null)
			StunProperty.register((EntityLivingBase) event.entity);
	}

}