package org.silvercatcher.reforged.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class ReforgedRender extends Render {
	
	ModelBase model;
	float scale = 1;
	
	protected ReforgedRender(RenderManager renderManager, ModelBase model) {
		super(renderManager);
		this.model = model;
	}
	protected ReforgedRender(RenderManager renderManager, ModelBase model, float scale) {
		super(renderManager);
		this.model = model;
		this.scale = scale;
	}
	
	@Override
	public void doRender(Entity Bullet, double x, double y, double z, float yaw, float partialTick) {
		renderEntityModel(Bullet, x, y, z, yaw, partialTick);
	}

	public void renderEntityModel(Entity theEntity, double x, double y, double z, float yaw, float partialTick) {
		GL11.glPushMatrix();
		bindTexture(getEntityTexture(theEntity));
		GL11.glTranslated(x, y, z);
		GL11.glScalef(scale, scale, scale);
		model.render(theEntity,(float) x,(float) y,(float) z, yaw, partialTick, 0.0475F);
		GL11.glPopMatrix();
	}
}