package org.silvercatcher.reforged;

import java.util.*;

import org.silvercatcher.reforged.api.ICustomReach;
import org.silvercatcher.reforged.api.ItemExtension;
import org.silvercatcher.reforged.packet.MessageCustomReachAttack;
import org.silvercatcher.reforged.props.IStunProperty;
import org.silvercatcher.reforged.util.Helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ReforgedEvents {

	public static boolean notified = false;

	public static Map<UUID, Integer> map;
	static {
		map = new HashMap<UUID, Integer>();
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void customReach(MouseEvent e) {
		if (e.getButton() == 0 && e.isButtonstate()) {
			Minecraft mc = Minecraft.getMinecraft();
			if (mc.player.inventory.getCurrentItem() != null && !mc.player.inventory.getCurrentItem().isEmpty()) {
				Item i = mc.player.inventory.getCurrentItem().getItem();
				if (i instanceof ICustomReach && i instanceof ItemExtension) {
					ICustomReach icr = (ICustomReach) i;
					Entity hit = Helpers.getMouseOverExtended(icr.reach()).entityHit;
					if (hit != null && mc.objectMouseOver.entityHit == null && hit != mc.player) {
						ReforgedMod.network.sendToServer(new MessageCustomReachAttack(hit.getEntityId()));
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onEntityConstructing(AttachCapabilitiesEvent e) {
		if (e.getObject() instanceof EntityLivingBase) {
			e.addCapability(IStunProperty.EXT_PROP_NAME, new ICapabilitySerializable<NBTPrimitive>() {

				IStunProperty inst = ReforgedMod.STUN_PROP.getDefaultInstance();

				@Override
				public void deserializeNBT(NBTPrimitive nbt) {
					ReforgedMod.STUN_PROP.getStorage().readNBT(ReforgedMod.STUN_PROP, inst, null, nbt);
				}

				@Override
				public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
					return capability == ReforgedMod.STUN_PROP ? ReforgedMod.STUN_PROP.<T>cast(inst) : null;
				}

				@Override
				public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
					return capability == ReforgedMod.STUN_PROP;
				}

				@Override
				public NBTPrimitive serializeNBT() {
					return (NBTPrimitive) ReforgedMod.STUN_PROP.getStorage().writeNBT(ReforgedMod.STUN_PROP, inst,
							null);
				}

			});
		}
	}

	// TODO TRANSLATION!!
	@SubscribeEvent
	public void onTick(PlayerTickEvent e) {
		if (e.side == Side.CLIENT)
			notified = true;
		if (!notified) {
			notified = true;
			EntityPlayer p = e.player;
			String par = "\u00A7";
			if (ReforgedMod.battlegearDetected) {
				p.sendMessage(new TextComponentString(par + "7[" + par + "bReforged" + par + "7] " + par
						+ "cYou have \"Mine & Blade Battlegear 2 - Bullseye\" " + par + "cinstalled."));
				p.sendMessage(new TextComponentString(
						"" + par + "cPatch loaded, because it has incompatibility issues with Reforged."));
				p.sendMessage(new TextComponentString(
						"" + par + "cSome Weapons will act different (but they hopefully work)!"));
			}
		}
	}

	@SubscribeEvent
	public void onWorldTick(WorldTickEvent e) {
		for (Entity en : (e.world.loadedEntityList)) {
			if (en instanceof EntityLivingBase) {
				EntityLivingBase player = (EntityLivingBase) en;
				IStunProperty prop = player.getCapability(ReforgedMod.STUN_PROP, null);
				if (prop != null && prop.isStunned()) {
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
						prop.setStunned(false);
						map.put(player.getUniqueID(), 0);
					}
				}
			}
		}
	}

}