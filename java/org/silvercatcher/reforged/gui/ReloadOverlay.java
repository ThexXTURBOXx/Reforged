package org.silvercatcher.reforged.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.silvercatcher.reforged.items.weapons.IReloadable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ReloadOverlay extends Gui {

	private final Minecraft minecraft;
	
	public ReloadOverlay() {
	
		super();
		minecraft = Minecraft.getMinecraft();
	}
	
	@SubscribeEvent
	public void renderReload(RenderGameOverlayEvent event) {
		
		if(event instanceof RenderGameOverlayEvent.Post || event.type != RenderGameOverlayEvent.ElementType.HOTBAR) {
			return ;
		}
		
		EntityPlayer player = minecraft.thePlayer;
		
		if(player != null) {
			
			ItemStack equipped = player.getCurrentEquippedItem();
						
			if(equipped != null && equipped.getItem() instanceof IReloadable) {
				
				IReloadable reloadable = (IReloadable) equipped.getItem();
				
				float done = reloadable.getReloadDone(equipped) / reloadable.getReloadTotal();
								
				GL11.glColor4f(1F, 1F, 1F, 1F);
				GL11.glDisable(GL11.GL_LIGHTING);
				
				
				int i;
				for (i = 0; i < 9; i++)
				{
					if (player.inventory.getStackInSlot(i) == equipped)
					{
						break;
					}
				}
				int x0 = event.resolution.getScaledWidth() / 2 - 88 + i * 20;
				int y0 = event.resolution.getScaledHeight() - 3;
				
				ReadableColor color = ReadableColor.RED;
				drawRect(x0, y0, x0 + 16, y0 - (int) (done * 16),
						color.getAlphaByte() << color.getRedByte() << color.getGreenByte() << color.getBlueByte());
			}
		}
	}
}
