package org.silvercatcher.reforged.gui;

import org.lwjgl.opengl.GL11;
import org.silvercatcher.reforged.items.weapons.AReloadable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
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
				
				drawRectangle(x0, y0 - (int) (done * 16), x0 + 16, y0, color);
			}
		}
	}
	
	/** Helper method for creating a Rectangle*/
	public void drawRectangle(int left, int top, int right, int bottom, float[] color) {
		
        if (left < right) {
            int j1 = left;
            left = right;
            right = j1;
        }

        if (top < bottom) {
            int j1 = top;
            top = bottom;
            bottom = j1;
        }
        
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(color[0], color[1], color[2], color[3]);
        
        //1.8
        worldrenderer.startDrawingQuads();
        worldrenderer.addVertex((double)left, (double)bottom, 0.0D);
        worldrenderer.addVertex((double)right, (double)bottom, 0.0D);
        worldrenderer.addVertex((double)right, (double)top, 0.0D);
        worldrenderer.addVertex((double)left, (double)top, 0.0D);
        
        //1.8.9
        /*
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos((double)left, (double)bottom, 0.0D).endVertex();
        worldrenderer.pos((double)right, (double)bottom, 0.0D).endVertex();
        worldrenderer.pos((double)right, (double)top, 0.0D).endVertex();
        worldrenderer.pos((double)left, (double)top, 0.0D).endVertex();
        */
        
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
	}
	
}
