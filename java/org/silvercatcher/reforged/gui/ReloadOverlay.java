package org.silvercatcher.reforged.gui;

import org.lwjgl.opengl.GL11;
import org.silvercatcher.reforged.api.AReloadable;
import org.silvercatcher.reforged.api.CompoundTags;
import org.silvercatcher.reforged.util.Helpers;

//import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
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
	private float amount = 0;
	private float amountUnchecked = 0;
	private float ticksBefore = -1;
	
	public ReloadOverlay() {
	
		super();
		minecraft = Minecraft.getMinecraft();
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderReload(RenderGameOverlayEvent event) {
		
		if(event instanceof RenderGameOverlayEvent.Post|| event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR) {
			return;
		}
		
		EntityPlayer player = minecraft.player;
		
		if(player != null && player.world != null) {
			
			ItemStack equipped = player.inventory.getCurrentItem();
						
			if(equipped != null && !equipped.isEmpty() && equipped.getItem() instanceof AReloadable) {
				
				AReloadable reloadable = (AReloadable) equipped.getItem();
				
				if(reloadable.giveCompound(equipped).getByte(CompoundTags.AMMUNITION) != AReloadable.loading) {
					return;
				}
				
				int amountUnchecked = CompoundTags.giveCompound(equipped).getInteger(CompoundTags.TIME);
				
				if(amountUnchecked > reloadable.getReloadTotal()) {
					amountUnchecked = 0;
				}
				
				float done = (float) amountUnchecked / (float) reloadable.getReloadTotal();
				
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
				
				int x0 = event.getResolution().getScaledWidth() / 2 - 88 + i * 20;
				int y0 = event.getResolution().getScaledHeight() - 3;
				
				Helpers.drawRectangle(x0, y0 - (int) (done * 16), x0 + 16, y0, color);
			}
		}
	}
}
