package org.silvercatcher.reforged.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelDynamite extends ReforgedModel {
	public ModelRenderer Dynamite;
	public ModelRenderer Fire;

	public ModelDynamite() {
		super();
		this.Fire = new ModelRenderer(this, 0, 6);
		this.Fire.setRotationPoint(-1.4F, 0.6F, 0.6F);
		this.Fire.addBox(0.0F, 0.0F, 0.0F, 14, 1, 1, 0.0F);
		this.setRotateAngle(Fire, 0.4553564018453205F, 0.0F, 0.0F);
		this.Dynamite = new ModelRenderer(this, 0, 0);
		this.Dynamite.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Dynamite.addBox(0.0F, 0.0F, 0.0F, 16, 3, 3, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(this.Fire.offsetX, this.Fire.offsetY, this.Fire.offsetZ);
		GlStateManager.translate(this.Fire.rotationPointX * f5, this.Fire.rotationPointY * f5,
				this.Fire.rotationPointZ * f5);
		GlStateManager.scale(0.2D, 0.2D, 0.2D);
		GlStateManager.translate(-this.Fire.offsetX, -this.Fire.offsetY, -this.Fire.offsetZ);
		GlStateManager.translate(-this.Fire.rotationPointX * f5, -this.Fire.rotationPointY * f5,
				-this.Fire.rotationPointZ * f5);
		this.Fire.render(f5);
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.translate(this.Dynamite.offsetX, this.Dynamite.offsetY, this.Dynamite.offsetZ);
		GlStateManager.translate(this.Dynamite.rotationPointX * f5, this.Dynamite.rotationPointY * f5,
				this.Dynamite.rotationPointZ * f5);
		GlStateManager.scale(0.5D, 0.5D, 0.5D);
		GlStateManager.translate(-this.Dynamite.offsetX, -this.Dynamite.offsetY, -this.Dynamite.offsetZ);
		GlStateManager.translate(-this.Dynamite.rotationPointX * f5, -this.Dynamite.rotationPointY * f5,
				-this.Dynamite.rotationPointZ * f5);
		this.Dynamite.render(f5);
		GlStateManager.popMatrix();
	}
}