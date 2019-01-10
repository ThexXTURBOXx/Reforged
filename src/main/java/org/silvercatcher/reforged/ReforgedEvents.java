package org.silvercatcher.reforged;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.OptionalCapabilityInstance;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import org.silvercatcher.reforged.api.ICustomReach;
import org.silvercatcher.reforged.api.ItemExtension;
import org.silvercatcher.reforged.packet.MessageCustomReachAttack;
import org.silvercatcher.reforged.props.DefaultStunImpl;
import org.silvercatcher.reforged.props.IStunProperty;
import org.silvercatcher.reforged.util.Helpers;

public class ReforgedEvents {

	public static boolean notified = false;

	public static Map<UUID, Integer> map;

	static {
		map = new HashMap<>();
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void customReach(GuiScreenEvent.MouseClickedEvent e) {
		if (e.getButton() == 0) {
			Minecraft mc = Minecraft.getInstance();
			if (mc.player.inventory.getCurrentItem() != null && !mc.player.inventory.getCurrentItem().isEmpty()) {
				Item i = mc.player.inventory.getCurrentItem().getItem();
				if (i instanceof ICustomReach && i instanceof ItemExtension) {
					ICustomReach icr = (ICustomReach) i;
					Entity hit = Helpers.getMouseOverExtended(icr.reach()).entity;
					if (hit != null && mc.objectMouseOver.entity == null && hit != mc.player) {
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

				@Nonnull
				@Override
				public <T> OptionalCapabilityInstance<T> getCapability(@Nonnull Capability<T> cap) {
					return cap == ReforgedMod.STUN_PROP ? ReforgedMod.STUN_PROP.<T>cast(inst) : null;
				}

				@Nonnull
				@Override
				public <T> OptionalCapabilityInstance<T> getCapability(@Nonnull Capability<T> cap, @Nullable EnumFacing side) {
					return getCapability(cap);
				}

				@Override
				public NBTPrimitive serializeNBT() {
					return (NBTPrimitive) ReforgedMod.STUN_PROP.getStorage().writeNBT(ReforgedMod.STUN_PROP, inst, null);
				}

			});
		}
	}

	// TODO TRANSLATION!!
	@SubscribeEvent
	public void onTick(PlayerTickEvent e) {
		if (e.side == LogicalSide.CLIENT)
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
		if (!e.world.isRemote) {
			Iterator<Entity> iter = e.world.loadedEntityList.iterator();
			while (iter.hasNext()) {
				Entity en = iter.next();
				if (en instanceof EntityLivingBase) {
					EntityLivingBase player = (EntityLivingBase) en;
					IStunProperty prop = player.getCapability(ReforgedMod.STUN_PROP).orElse(new DefaultStunImpl());
					if (prop.isStunned()) {
						if (!map.containsKey(player.getUniqueID()))
							map.put(player.getUniqueID(), 0);
						int i = map.get(player.getUniqueID());
						i++;
						player.motionX = 0;
						player.motionY = 0;
						player.motionZ = 0;
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

}