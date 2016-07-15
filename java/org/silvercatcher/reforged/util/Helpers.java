package org.silvercatcher.reforged.util;

import net.minecraft.client.renderer.*;
import net.minecraft.util.BlockPos;
//For 1.8.9: import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class Helpers {
	
	/**Checks if the 2 given BlockPositions are equal*/
	public static boolean blockPosEqual(BlockPos pos1, BlockPos pos2) {
		int x1 = pos1.getX();
		int x2 = pos2.getX();
		int y1 = pos1.getY();
		int y2 = pos2.getY();
		int z1 = pos1.getZ();
		int z2 = pos2.getZ();
		return (x1 == x2 && y1 == y2 && z1 == z2);
	}
	
	/** Helper method for creating a Rectangle*/
	public static void drawRectangle(int left, int top, int right, int bottom, float[] color) {
		
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
        worldrenderer.addVertex(left, bottom, 0.0D);
        worldrenderer.addVertex(right, bottom, 0.0D);
        worldrenderer.addVertex(right, top, 0.0D);
        worldrenderer.addVertex(left, top, 0.0D);
        
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
