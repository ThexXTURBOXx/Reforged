package org.silvercatcher.reforged.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.silvercatcher.reforged.items.weapons.IReloadable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ReloadOverlay extends Gui {

	private static final int red 		= 0xff000000;
	private static final int yellow 	= 0xffff0000;
	private static final int orange 	= 0xff660000;
	private static final int green 		= 0x0000ff00;
	
	private final Minecraft minecraft;
	
	public ReloadOverlay() {
	
		super();
		minecraft = Minecraft.getMinecraft();
	}
	
	@SubscribeEvent
	public void renderReload(RenderGameOverlayEvent event) {
		
		if(event instanceof RenderGameOverlayEvent.Post|| event.type != RenderGameOverlayEvent.ElementType.HOTBAR) {
			return ;
		}
		
		EntityPlayer player = minecraft.thePlayer;
		
		if(player != null && player.worldObj != null) {
			
			ItemStack equipped = player.getCurrentEquippedItem();
						
			if(equipped != null && equipped.getItem() instanceof IReloadable) {
				
				IReloadable reloadable = (IReloadable) equipped.getItem();
				
				int reloadLeft = (int) (reloadable.getReloadStarted(equipped)
						- player.worldObj.getWorldTime());
				
				if(reloadLeft < 0) return;
				
				if(reloadLeft > reloadable.getReloadTotal()) {
					reloadLeft = reloadable.getReloadTotal();
				}
				
				float done = (float) reloadLeft / reloadable.getReloadTotal();
				
				GL11.glColor4f(1F, 1F, 1F, 1F);
				GL11.glDisable(GL11.GL_LIGHTING);
				
				System.out.println("done: " + done);
				
				int i;
				for (i = 0; i < 9; i++)
				{
					if (player.inventory.getStackInSlot(i) == equipped)
					{
						break;
					}
				}
				
				int color;
				
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
				
				
				//System.out.println("color: " + color);
				
				int x0 = event.resolution.getScaledWidth() / 2 - 88 + i * 20;
				int y0 = event.resolution.getScaledHeight() -3;
				
				drawRect(x0, y0, x0 + 16, y0 - (int) (done * 16), color);
			}
		}
	}
}
