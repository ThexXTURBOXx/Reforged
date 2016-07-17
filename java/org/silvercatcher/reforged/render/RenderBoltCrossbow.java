package org.silvercatcher.reforged.render;

import org.lwjgl.opengl.GL11;
import org.silvercatcher.reforged.entities.EntityCrossbowBolt;

import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBoltCrossbow extends Render {
    private static final ResourceLocation arrowTextures = new ResourceLocation("textures/entity/arrow.png");
    private static final String __OBFID = "CL_00000978";

    public RenderBoltCrossbow(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    public void doRender(EntityCrossbowBolt arrow, double x, double y, double z, float p_180551_8_, float p_180551_9_)
    {
        this.bindEntityTexture(arrow);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.rotate(arrow.prevRotationYaw + (arrow.rotationYaw - arrow.prevRotationYaw) * p_180551_9_ - 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(arrow.prevRotationPitch + (arrow.rotationPitch - arrow.prevRotationPitch) * p_180551_9_, 0.0F, 0.0F, 1.0F);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        byte b0 = 0;
        float f2 = 0.0F;
        float f3 = 0.5F;
        float f4 = (float)(0 + b0 * 10) / 32.0F;
        float f5 = (float)(5 + b0 * 10) / 32.0F;
        float f6 = 0.0F;
        float f7 = 0.15625F;
        float f8 = (float)(5 + b0 * 10) / 32.0F;
        float f9 = (float)(10 + b0 * 10) / 32.0F;
        float f10 = 0.05625F;
        GlStateManager.enableRescaleNormal();
        float f11 = (float)arrow.arrowShake - p_180551_9_;

        if (f11 > 0.0F)
        {
            float f12 = -MathHelper.sin(f11 * 3.0F) * f11;
            GlStateManager.rotate(f12, 0.0F, 0.0F, 1.0F);
        }

        GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(f10, f10, f10);
        GlStateManager.translate(-4.0F, 0.0F, 0.0F);
        GL11.glNormal3f(f10, 0.0F, 0.0F);
        worldrenderer.startDrawingQuads();
        worldrenderer.addVertexWithUV(-7.0D, -2.0D, -2.0D, (double)f6, (double)f8);
        worldrenderer.addVertexWithUV(-7.0D, -2.0D, 2.0D, (double)f7, (double)f8);
        worldrenderer.addVertexWithUV(-7.0D, 2.0D, 2.0D, (double)f7, (double)f9);
        worldrenderer.addVertexWithUV(-7.0D, 2.0D, -2.0D, (double)f6, (double)f9);
        tessellator.draw();
        GL11.glNormal3f(-f10, 0.0F, 0.0F);
        worldrenderer.startDrawingQuads();
        worldrenderer.addVertexWithUV(-7.0D, 2.0D, -2.0D, (double)f6, (double)f8);
        worldrenderer.addVertexWithUV(-7.0D, 2.0D, 2.0D, (double)f7, (double)f8);
        worldrenderer.addVertexWithUV(-7.0D, -2.0D, 2.0D, (double)f7, (double)f9);
        worldrenderer.addVertexWithUV(-7.0D, -2.0D, -2.0D, (double)f6, (double)f9);
        tessellator.draw();

        for (int i = 0; i < 4; ++i)
        {
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, f10);
            worldrenderer.startDrawingQuads();
            worldrenderer.addVertexWithUV(-8.0D, -2.0D, 0.0D, (double)f2, (double)f4);
            worldrenderer.addVertexWithUV(8.0D, -2.0D, 0.0D, (double)f3, (double)f4);
            worldrenderer.addVertexWithUV(8.0D, 2.0D, 0.0D, (double)f3, (double)f5);
            worldrenderer.addVertexWithUV(-8.0D, 2.0D, 0.0D, (double)f2, (double)f5);
            tessellator.draw();
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(arrow, x, y, z, p_180551_8_, p_180551_9_);
    }

    protected ResourceLocation getEntityTexture(EntityCrossbowBolt p_180550_1_)
    {
        return arrowTextures;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntityCrossbowBolt)entity);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity>) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doe
     *  
     * @param entityYaw The yaw rotation of the passed entity
     */
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.doRender((EntityCrossbowBolt)entity, x, y, z, entityYaw, partialTicks);
    }
}