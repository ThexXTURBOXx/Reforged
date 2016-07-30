package org.silvercatcher.reforged;

import org.silvercatcher.reforged.api.ICustomReach;
import org.silvercatcher.reforged.api.ItemExtension;
import org.silvercatcher.reforged.packet.MessageCustomReachAttack;
import org.silvercatcher.reforged.util.Helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ReforgedEvents {
	
	@SubscribeEvent
	public void customReach(MouseEvent e) {
		if(e.button == 0 && e.buttonstate) {
			Minecraft mc = Minecraft.getMinecraft();
			if(mc.thePlayer.getCurrentEquippedItem() != null) {
				Item i = mc.thePlayer.getCurrentEquippedItem().getItem();
				if(i instanceof ICustomReach && i instanceof ItemExtension) {
					ICustomReach icr = (ICustomReach) i;
					Entity hit = Helpers.getMouseOverExtended((float) icr.reach()).entityHit;
					if(hit != null && mc.objectMouseOver.entityHit == null && hit != mc.thePlayer) {
						ReforgedMod.network.sendToServer(new MessageCustomReachAttack(hit.getEntityId()));
					}
				}
			}
		}
	}
	
}