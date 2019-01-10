package org.silvercatcher.reforged.models;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDynamite extends ReforgedModel {

	public ModelRenderer dynamite;
	public ModelRenderer fire;

	public ModelDynamite() {
		super();
		this.fire = new ModelRenderer(this, 0, 6);
		this.fire.setRotationPoint(-1.4F, 0.6F, 0.6F);
		this.fire.addBox(0.0F, 0.0F, 0.0F, 14, 1, 1, 0.0F);
		this.setRotateAngle(fire, 0.4553564018453205F, 0.0F, 0.0F);
		this.dynamite = new ModelRenderer(this, 0, 0);
		this.dynamite.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.dynamite.addBox(0.0F, 0.0F, 0.0F, 16, 3, 3, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		GlStateManager.pushMatrix();
		GlStateManager.translatef(this.fire.offsetX, this.fire.offsetY, this.fire.offsetZ);
		GlStateManager.translatef(this.fire.rotationPointX * f5, this.fire.rotationPointY * f5,
				this.fire.rotationPointZ * f5);
		GlStateManager.scaled(0.2D, 0.2D, 0.2D);
		GlStateManager.translatef(-this.fire.offsetX, -this.fire.offsetY, -this.fire.offsetZ);
		GlStateManager.translatef(-this.fire.rotationPointX * f5, -this.fire.rotationPointY * f5,
				-this.fire.rotationPointZ * f5);
		this.fire.render(f5);
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.translatef(this.dynamite.offsetX, this.dynamite.offsetY, this.dynamite.offsetZ);
		GlStateManager.translatef(this.dynamite.rotationPointX * f5, this.dynamite.rotationPointY * f5,
				this.dynamite.rotationPointZ * f5);
		GlStateManager.scaled(0.5D, 0.5D, 0.5D);
		GlStateManager.translatef(-this.dynamite.offsetX, -this.dynamite.offsetY, -this.dynamite.offsetZ);
		GlStateManager.translatef(-this.dynamite.rotationPointX * f5, -this.dynamite.rotationPointY * f5,
				-this.dynamite.rotationPointZ * f5);
		this.dynamite.render(f5);
		GlStateManager.popMatrix();
	}
}