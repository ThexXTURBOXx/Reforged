package org.silvercatcher.reforged.util;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.Entity;
//For 1.8.9: import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.*;
import net.minecraftforge.fml.client.FMLClientHandler;

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
	
	/**Thanks to Jabelar!!!*/
	public static MovingObjectPosition getMouseOverExtended(float distance) {
	    Minecraft mc = FMLClientHandler.instance().getClient();
	    Entity theRenderViewEntity = mc.getRenderViewEntity();
	    AxisAlignedBB theViewBoundingBox = new AxisAlignedBB(
	            theRenderViewEntity.posX-0.5D,
	            theRenderViewEntity.posY-0.0D,
	            theRenderViewEntity.posZ-0.5D,
	            theRenderViewEntity.posX+0.5D,
	            theRenderViewEntity.posY+1.5D,
	            theRenderViewEntity.posZ+0.5D
	            );
	    MovingObjectPosition returnMOP = null;
	    if (mc.theWorld != null) {
	        double var2 = distance;
	        returnMOP = theRenderViewEntity.rayTrace(var2, 0);
	        double calcdist = var2;
	        Vec3 pos = theRenderViewEntity.getPositionEyes(0);
	        var2 = calcdist;
	        if (returnMOP != null) {
	            calcdist = returnMOP.hitVec.distanceTo(pos);
	        }
	        Vec3 lookvec = theRenderViewEntity.getLook(0);
	        Vec3 var8 = pos.addVector(lookvec.xCoord * var2, lookvec.yCoord * var2, lookvec.zCoord * var2);
	        Entity pointedEntity = null;
	        float var9 = 1.0F;
	        List<Entity> list = mc.theWorld.getEntitiesWithinAABBExcludingEntity(theRenderViewEntity, 
	              theViewBoundingBox.addCoord(
	                    lookvec.xCoord * var2,
	                    lookvec.yCoord * var2,
	                    lookvec.zCoord * var2).expand(var9, var9, var9));
	        double d = calcdist;
	        for (Entity entity : list) {
	            if (entity.canBeCollidedWith()) {
	                float bordersize = entity.getCollisionBorderSize();
	                AxisAlignedBB aabb = new AxisAlignedBB(
	                      entity.posX-entity.width/2, 
	                      entity.posY, 
	                      entity.posZ-entity.width/2, 
	                      entity.posX+entity.width/2, 
	                      entity.posY+entity.height, 
	                      entity.posZ+entity.width/2);
	                aabb.expand(bordersize, bordersize, bordersize);
	                MovingObjectPosition mop0 = aabb.calculateIntercept(pos, var8);
	                if (aabb.isVecInside(pos)) {
	                    if (0.0D < d || d == 0.0D) {
	                        pointedEntity = entity;
	                        d = 0.0D;
	                    }
	                } else if (mop0 != null) {
	                    double d1 = pos.distanceTo(mop0.hitVec);
	                    if (d1 < d || d == 0.0D) {
	                        pointedEntity = entity;
	                        d = d1;
	                    }
	                }
	            }
	        }
	        if (pointedEntity != null && (d < calcdist || returnMOP == null)) {
	             returnMOP = new MovingObjectPosition(pointedEntity);
	        }
	    }
	    return returnMOP;
	}
	
}
