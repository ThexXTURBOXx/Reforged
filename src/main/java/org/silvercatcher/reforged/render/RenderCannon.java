package org.silvercatcher.reforged.render;

import org.lwjgl.opengl.GL11;
import org.silvercatcher.reforged.ReforgedReferences.Textures;
import org.silvercatcher.reforged.entities.EntityCannon;
import org.silvercatcher.reforged.models.*;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCannon extends Render<EntityCannon> {

	public RenderCannon(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(EntityCannon bullet, double x, double y, double z, float yaw, float partialTicks) {
		super.doRender(bullet, x, y, z, yaw, partialTicks);
		renderEntityModel(bullet, x, y, z, yaw, partialTicks);
	}

	public void renderEntityModel(EntityCannon theEntity, double x, double y, double z, float yaw, float partialTicks) {
		float scale = 2.0f;
		GL11.glPushMatrix();
		bindTexture(getEntityTexture(theEntity));
		GL11.glTranslated(x, y + 0.09 * scale, z);
		GL11.glRotated(180, 1, 0, 1);
		GL11.glScalef(scale, scale, scale);
		new ModelCannonBase().render(theEntity, (float) x, (float) y, (float) z, yaw, partialTicks, 0.0475F);
		GL11.glTranslated(0, -0.14, 0);
		GL11.glRotated(
				theEntity.prevRotationYaw + (theEntity.rotationYaw - theEntity.prevRotationYaw) * partialTicks - 90.0F,
				0.0F, 1.0F, 0.0F);
		new ModelCannonHolder().render(theEntity, (float) x, (float) y, (float) z, yaw, partialTicks, 0.0475F);
		GL11.glTranslated(0, -0.14, 0);
		GL11.glRotated(
				theEntity.prevRotationPitch + (theEntity.rotationPitch - theEntity.prevRotationPitch) * partialTicks,
				1.0F, 0.0F, 0.0F);
		new ModelCannonChannel().render(theEntity, (float) x, (float) y, (float) z, yaw, partialTicks, 0.0475F);
		GL11.glScalef(1 / scale, 1 / scale, 1 / scale);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCannon entity) {
		return Textures.CANNON;
	}

}