package org.silvercatcher.reforged.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
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
		GlStateManager.translate(shapefront.offsetX, shapefront.offsetY, shapefront.offsetZ);
		GlStateManager.translate(shapefront.rotationPointX * f5, shapefront.rotationPointY * f5,
				shapefront.rotationPointZ * f5);
		GlStateManager.scale(0.6D, 0.1D, 0.1D);
		GlStateManager.translate(-shapefront.offsetX, -shapefront.offsetY, -shapefront.offsetZ);
		GlStateManager.translate(-shapefront.rotationPointX * f5, -shapefront.rotationPointY * f5,
				-shapefront.rotationPointZ * f5);
		shapefront.render(f5);
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.translate(shapebehind.offsetX, shapebehind.offsetY, shapebehind.offsetZ);
		GlStateManager.translate(shapebehind.rotationPointX * f5, shapebehind.rotationPointY * f5,
				shapebehind.rotationPointZ * f5);
		GlStateManager.scale(0.8D, 0.7D, 0.7D);
		GlStateManager.translate(-shapebehind.offsetX, -shapebehind.offsetY, -shapebehind.offsetZ);
		GlStateManager.translate(-shapebehind.rotationPointX * f5, -shapebehind.rotationPointY * f5,
				-shapebehind.rotationPointZ * f5);
		shapebehind.render(f5);
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.translate(shapering.offsetX, shapering.offsetY, shapering.offsetZ);
		GlStateManager.translate(shapering.rotationPointX * f5, shapering.rotationPointY * f5,
				shapering.rotationPointZ * f5);
		GlStateManager.scale(0.1D, 0.7D, 0.7D);
		GlStateManager.translate(-shapering.offsetX, -shapering.offsetY, -shapering.offsetZ);
		GlStateManager.translate(-shapering.rotationPointX * f5, -shapering.rotationPointY * f5,
				-shapering.rotationPointZ * f5);
		shapering.render(f5);
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.translate(shapebehind2.offsetX, shapebehind2.offsetY, shapebehind2.offsetZ);
		GlStateManager.translate(shapebehind2.rotationPointX * f5, shapebehind2.rotationPointY * f5,
				shapebehind2.rotationPointZ * f5);
		GlStateManager.scale(0.7D, 0.7D, 0.7D);
		GlStateManager.translate(-shapebehind2.offsetX, -shapebehind2.offsetY, -shapebehind2.offsetZ);
		GlStateManager.translate(-shapebehind2.rotationPointX * f5, -shapebehind2.rotationPointY * f5,
				-shapebehind2.rotationPointZ * f5);
		shapebehind2.render(f5);
		GlStateManager.popMatrix();
	}

}