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
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.gameevent.InputEvent;
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
	public void customReach(InputEvent.MouseInputEvent e) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.mouseHelper.isLeftDown()) {
			if (!mc.player.inventory.getCurrentItem().isEmpty()) {
				Item i = mc.player.inventory.getCurrentItem().getItem();
				if (i instanceof ICustomReach && i instanceof ItemExtension) {
					ICustomReach icr = (ICustomReach) i;
					RayTraceResult rtr = Helpers.getMouseOverExtended(icr.reach());
					if (rtr != null) {
						Entity hit = rtr.entity;
						if (hit != null && mc.objectMouseOver.entity == null && hit != mc.player) {
							ReforgedMod.network.sendToServer(new MessageCustomReachAttack(hit.getEntityId()));
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onEntityConstructing(AttachCapabilitiesEvent<EntityLivingBase> e) {
		e.addCapability(IStunProperty.EXT_PROP_NAME, new ICapabilitySerializable<NBTPrimitive>() {

			IStunProperty inst = ReforgedMod.STUN_PROP.getDefaultInstance();

			@Override
			public void deserializeNBT(NBTPrimitive nbt) {
				ReforgedMod.STUN_PROP.getStorage().readNBT(ReforgedMod.STUN_PROP, inst, null, nbt);
			}

			@SuppressWarnings("unchecked")
			@Nonnull
			@Override
			public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable EnumFacing side) {
				return cap == ReforgedMod.STUN_PROP ? LazyOptional.<T>of(() -> (T) inst) : LazyOptional.empty();
			}

			@Override
			public NBTPrimitive serializeNBT() {
				NBTPrimitive nbt = new NBTTagInt(0);
				ReforgedMod.STUN_PROP.getStorage().readNBT(ReforgedMod.STUN_PROP, inst, null, nbt);
				return nbt;
			}

		});
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