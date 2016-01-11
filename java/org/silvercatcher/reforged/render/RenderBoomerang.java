package org.silvercatcher.reforged.render;

import org.lwjgl.opengl.GL11;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.items.weapons.ItemBoomerang;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBoomerang extends Render {

	private static ResourceLocation wood_texture = 
			new ResourceLocation(ReforgedMod.ID, "textures/entity/wooden_boomerang.png");
	private static ResourceLocation stone_texture = 
			new ResourceLocation(ReforgedMod.ID, "textures/entity/stone_boomerang.png");
	private static ResourceLocation iron_texture = 
			new ResourceLocation(ReforgedMod.ID, "textures/entity/iron_boomerang.png");
	private static ResourceLocation gold_texture = 
			new ResourceLocation(ReforgedMod.ID, "textures/entity/golden_boomerang.png");
	private static ResourceLocation diamon_texture = 
			new ResourceLocation(ReforgedMod.ID, "textures/entity/diamond_boomerang.png");

	public RenderBoomerang(RenderManager renderManager) {
		super(renderManager);
	}
	
	private void renderBoomerang(EntityBoomerang entityarrow, double d0, double d1, double d2, float f, float f1) {
		
		bindEntityTexture(entityarrow);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d0, (float) d1, (float) d2);
		GL11.glRotatef(entityarrow.prevRotationPitch + (entityarrow.rotationPitch - entityarrow.prevRotationPitch) * f1, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef((entityarrow.prevRotationYaw + (entityarrow.rotationYaw - entityarrow.prevRotationYaw) * f1) - 90F, 0.0F, 1.0F, 0.0F);
		Tessellator tess = Tessellator.getInstance();
		WorldRenderer worldrenderer = tess.getWorldRenderer();
		float ft0 = 0.0F;
		float ft1 = 0.5F;
		float ft2 = 1.0F;
		float fh = 0.08F;
		float f2 = 0.2F;
		float f3 = 0.9F;
		float f4 = 1F - f2;
		float ft3 = 0.5F;
		float ft4 = 0.65625F;
		GL11.glTranslatef(-0.5F, 0F, -0.5F);
		GL11.glColor3f(1F, 1F, 1F);
		GL11.glNormal3f(0F, 1F, 0F);
		worldrenderer.startDrawingQuads();
		worldrenderer.setColorOpaque_F(1F, 1F, 1F);
		worldrenderer.addVertexWithUV(0D, 0D, 1D, ft1, ft0);
		worldrenderer.addVertexWithUV(1D, 0D, 1D, ft0, ft0);
		worldrenderer.addVertexWithUV(1D, 0D, 0D, ft0, ft1);
		worldrenderer.addVertexWithUV(0D, 0D, 0D, ft1, ft1);
		worldrenderer.addVertexWithUV(0D, 0D, 1D, ft2, ft0);
		worldrenderer.addVertexWithUV(1D, 0D, 1D, ft1, ft0);
		worldrenderer.addVertexWithUV(1D, 0D, 0D, ft1, ft1);
		worldrenderer.addVertexWithUV(0D, 0D, 0D, ft2, ft1);
		tess.draw();
		GL11.glNormal3f(0F, -1F, 0F);
		worldrenderer.startDrawingQuads();
		worldrenderer.setColorOpaque_F(1F, 1F, 1F);
		worldrenderer.addVertexWithUV(1D, 0D, 0D, ft0, ft1);
		worldrenderer.addVertexWithUV(1D, 0D, 1D, ft1, ft1);
		worldrenderer.addVertexWithUV(0D, 0D, 1D, ft1, ft0);
		worldrenderer.addVertexWithUV(0D, 0D, 0D, ft0, ft0);
		worldrenderer.addVertexWithUV(1D, 0D, 0D, ft1, ft1);
		worldrenderer.addVertexWithUV(1D, 0D, 1D, ft2, ft1);
		worldrenderer.addVertexWithUV(0D, 0D, 1D, ft2, ft0);
		worldrenderer.addVertexWithUV(0D, 0D, 0D, ft1, ft0);
		tess.draw();
		float sqrt2 = (float) Math.sqrt(2);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glNormal3f(-sqrt2, 0F, sqrt2);
		worldrenderer.startDrawingQuads();
		worldrenderer.setColorOpaque_F(1F, 1F, 1F);
		worldrenderer.addVertexWithUV(f2, -fh, f4, ft1, ft3);
		worldrenderer.addVertexWithUV(f2, fh, f4, ft1, ft4);
		worldrenderer.addVertexWithUV(f3, fh, f4, ft0, ft4);
		worldrenderer.addVertexWithUV(f3, -fh, f4, ft0, ft3);
		worldrenderer.addVertexWithUV(f2, -fh, f4, ft2, ft3);
		worldrenderer.addVertexWithUV(f2, fh, f4, ft2, ft4);
		worldrenderer.addVertexWithUV(f3, fh, f4, ft1, ft4);
		worldrenderer.addVertexWithUV(f3, -fh, f4, ft1, ft3);
		worldrenderer.setColorOpaque_F(1F, 1F, 1F);
		worldrenderer.addVertexWithUV(f2, -fh, f4, ft1, ft3);
		worldrenderer.addVertexWithUV(f2, fh, f4, ft1, ft4);
		worldrenderer.addVertexWithUV(f2, fh, f2, ft0, ft4);
		worldrenderer.addVertexWithUV(f2, -fh, f2, ft0, ft3);
		worldrenderer.addVertexWithUV(f2, -fh, f4, ft2, ft3);
		worldrenderer.addVertexWithUV(f2, fh, f4, ft2, ft4);
		worldrenderer.addVertexWithUV(f2, fh, f2, ft1, ft4);
		worldrenderer.addVertexWithUV(f2, -fh, f2, ft1, ft3);
		tess.draw();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
	
	@Override
	public void doRender(Entity entity, double d0, double d1, double d2,
	 float f, float f1) {
		renderBoomerang((EntityBoomerang) entity, d0, d1, d2, f, f1);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		
		return wood_texture;
		/*
		//todo: find a better way and burn this...
		
		if(((EntityBoomerang) entity).getMaterial() == null) {
			System.err.println("wtf!");
			System.exit(1);
		}
		switch((((EntityBoomerang) entity).getMaterial())) {
		case EMERALD: return diamon_texture;
		case GOLD: return gold_texture;
		case IRON: return iron_texture;
		case STONE: return stone_texture;
		case WOOD: return wood_texture;
		default: throw new IllegalArgumentException("Dafuq is dis...");
		}*/
	}
}
