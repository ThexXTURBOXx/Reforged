package org.silvercatcher.reforged.render;

import org.lwjgl.opengl.GL11;
import org.silvercatcher.reforged.models.ReforgedModel;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class ReforgedRender<T extends Entity> extends Render<T> {
	
	ReforgedModel model;
	float scale = 1;
	int modifier = 0;
	
	protected ReforgedRender(RenderManager renderManager, ReforgedModel model, int rotationModifier) {
		super(renderManager);
		this.model = model;
		modifier = rotationModifier;
	}
	
	protected ReforgedRender(RenderManager renderManager, ReforgedModel model, float scale, int rotationModifier) {
		this(renderManager, model, rotationModifier);
		this.scale = scale;
	}
	
	@Override
	public void doRender(T bullet, double x, double y, double z, float yaw, float partialTicks) {
		super.doRender(bullet, x, y, z, yaw, partialTicks);
		renderEntityModel(bullet, x, y, z, yaw, partialTicks);
	}
	
	/**If you find any little issues while flying, just change partialTick in the render-method to 0. Could fix it... 
	I am not sure if I should let it like this, but for now it works ^^ I will change it, when needed
	 - ThexXTURBOXx*/
	public void renderEntityModel(T theEntity, double x, double y, double z, float yaw, float partialTicks) {
		GL11.glPushMatrix();
		bindTexture(getEntityTexture(theEntity));
		GL11.glTranslated(x, y, z);
		GL11.glScalef(scale, scale, scale);
		GL11.glRotated(theEntity.prevRotationYaw + (theEntity.rotationYaw - theEntity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotated(theEntity.prevRotationPitch + (theEntity.rotationPitch - theEntity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
		model.render(theEntity,(float) x,(float) y,(float) z, yaw + modifier, partialTicks, 0.0475F);
		GL11.glPopMatrix();
	}
	
	@Override
	protected abstract ResourceLocation getEntityTexture(T entity);
	
}