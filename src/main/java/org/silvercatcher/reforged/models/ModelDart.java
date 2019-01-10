package org.silvercatcher.reforged.models;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDart extends ReforgedModel {
	public ModelRenderer shapefront;
	public ModelRenderer shapebehind;
	public ModelRenderer shapering;
	public ModelRenderer shapebehind2;

	public ModelDart() {
		super();
		shapefront = new ModelRenderer(this, 58, 0);
		shapefront.setRotationPoint(0.0F, 0.3F, 0.3F);
		shapefront.addBox(1.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
		shapebehind = new ModelRenderer(this, 0, 0);
		shapebehind.setRotationPoint(-0.6F, 0.0F, 0.0F);
		shapebehind.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
		shapering = new ModelRenderer(this, 58, 2);
		shapering.setRotationPoint(-0.8F, 0.0F, 0.0F);
		shapering.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
		shapebehind2 = new ModelRenderer(this, 0, 2);
		shapebehind2.setRotationPoint(-1.5F, 0.0F, 0.0F);
		shapebehind2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		GlStateManager.pushMatrix();
		GlStateManager.translatef(shapefront.offsetX, shapefront.offsetY, shapefront.offsetZ);
		GlStateManager.translatef(shapefront.rotationPointX * f5, shapefront.rotationPointY * f5,
				shapefront.rotationPointZ * f5);
		GlStateManager.scaled(0.6D, 0.1D, 0.1D);
		GlStateManager.translatef(-shapefront.offsetX, -shapefront.offsetY, -shapefront.offsetZ);
		GlStateManager.translatef(-shapefront.rotationPointX * f5, -shapefront.rotationPointY * f5,
				-shapefront.rotationPointZ * f5);
		shapefront.render(f5);
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.translatef(shapebehind.offsetX, shapebehind.offsetY, shapebehind.offsetZ);
		GlStateManager.translatef(shapebehind.rotationPointX * f5, shapebehind.rotationPointY * f5,
				shapebehind.rotationPointZ * f5);
		GlStateManager.scaled(0.8D, 0.7D, 0.7D);
		GlStateManager.translatef(-shapebehind.offsetX, -shapebehind.offsetY, -shapebehind.offsetZ);
		GlStateManager.translatef(-shapebehind.rotationPointX * f5, -shapebehind.rotationPointY * f5,
				-shapebehind.rotationPointZ * f5);
		shapebehind.render(f5);
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.translatef(shapering.offsetX, shapering.offsetY, shapering.offsetZ);
		GlStateManager.translatef(shapering.rotationPointX * f5, shapering.rotationPointY * f5,
				shapering.rotationPointZ * f5);
		GlStateManager.scaled(0.1D, 0.7D, 0.7D);
		GlStateManager.translatef(-shapering.offsetX, -shapering.offsetY, -shapering.offsetZ);
		GlStateManager.translatef(-shapering.rotationPointX * f5, -shapering.rotationPointY * f5,
				-shapering.rotationPointZ * f5);
		shapering.render(f5);
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.translatef(shapebehind2.offsetX, shapebehind2.offsetY, shapebehind2.offsetZ);
		GlStateManager.translatef(shapebehind2.rotationPointX * f5, shapebehind2.rotationPointY * f5,
				shapebehind2.rotationPointZ * f5);
		GlStateManager.scaled(0.7D, 0.7D, 0.7D);
		GlStateManager.translatef(-shapebehind2.offsetX, -shapebehind2.offsetY, -shapebehind2.offsetZ);
		GlStateManager.translatef(-shapebehind2.rotationPointX * f5, -shapebehind2.rotationPointY * f5,
				-shapebehind2.rotationPointZ * f5);
		shapebehind2.render(f5);
		GlStateManager.popMatrix();
	}
}
