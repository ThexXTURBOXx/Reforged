package org.silvercatcher.reforged.gui;

import org.lwjgl.opengl.GL11;
import org.silvercatcher.reforged.items.weapons.AReloadable;
import org.silvercatcher.reforged.util.Helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ReloadOverlay extends Gui {
	
	//My way to setup colors >:D
	//The values are {Red, Green, Blue}
	//1 means f (full color)
	private static final float[] red    = new float[] {1, 0    , 0, 1};
	private static final float[] orange = new float[] {1, 0.66F, 0, 1};
	private static final float[] yellow = new float[] {1, 1    , 0, 1};
	private static final float[] green  = new float[] {0, 1    , 0, 1};
	
	private final Minecraft minecraft;
	
	public ReloadOverlay() {
	
		super();
		minecraft = Minecraft.getMinecraft();
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderReload(RenderGameOverlayEvent event) {
		
		if(event instanceof RenderGameOverlayEvent.Post|| event.type != RenderGameOverlayEvent.ElementType.HOTBAR) {
			return ;
		}
		
		EntityPlayer player = minecraft.thePlayer;
		
		if(player != null && player.worldObj != null) {
			
			ItemStack equipped = player.getCurrentEquippedItem();
						
			if(equipped != null && equipped.getItem() instanceof AReloadable) {
				
				AReloadable reloadable = (AReloadable) equipped.getItem();
				
				int reloadLeft = (int) (reloadable.getReloadStarted(equipped)
						- player.worldObj.getWorldTime());
				
				if(reloadLeft < 0) return;
				
				if(reloadLeft > reloadable.getReloadTotal()) {
					reloadLeft = reloadable.getReloadTotal();
				}
				
				float done = (float) reloadLeft / reloadable.getReloadTotal();
				
				GL11.glColor4f(1F, 1F, 1F, 1F);
				GL11.glDisable(GL11.GL_LIGHTING);
				
				int i;
				for (i = 0; i < 9; i++) {
					if (player.inventory.getStackInSlot(i) == equipped) {
						break;
					}
				}
				
				float[] color;
				
				if(done > 0.5f) {
					if(done > 0.75f) {
						color = green;
					} else {
						color = yellow;
					}
				} else {
					if(done > 0.25f) {
						color = orange;
					} else {
						color = red;
					}
				}
				
				int x0 = event.resolution.getScaledWidth() / 2 - 88 + i * 20;
				int y0 = event.resolution.getScaledHeight() - 3;
				
				Helpers.drawRectangle(x0, y0 - (int) (done * 16), x0 + 16, y0, color);
			}
		}
	}
}
