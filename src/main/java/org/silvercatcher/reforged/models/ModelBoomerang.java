package org.silvercatcher.reforged.models;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBoomerang extends ReforgedModel {

	public ModelRenderer tile1;
	public ModelRenderer tile2;
	public ModelRenderer tile3;
	public ModelRenderer tile4;
	public ModelRenderer tile5;
	public ModelRenderer tile6;

	public ModelBoomerang() {
		super();
		tile6 = new ModelRenderer(this, 56, 0);
		tile6.setRotationPoint(0.0F, 0.0F, -7.0F);
		tile6.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
		tile2 = new ModelRenderer(this, 0, 4);
		tile2.setRotationPoint(0.0F, 0.0F, -5.0F);
		tile2.addBox(0.0F, 0.0F, 0.0F, 2, 2, 5, 0.0F);
		tile1 = new ModelRenderer(this, 0, 0);
		tile1.setRotationPoint(0.0F, 0.0F, 0.0F);
		tile1.addBox(0.0F, 0.0F, 0.0F, 7, 2, 2, 0.0F);
		tile4 = new ModelRenderer(this, 14, 3);
		tile4.setRotationPoint(-1.0F, 1.0F, -8.0F);
		tile4.addBox(0.0F, 0.0F, 0.0F, 4, 1, 7, 0.0F);
		tile3 = new ModelRenderer(this, 0, 11);
		tile3.setRotationPoint(-1.0F, 1.0F, -1.0F);
		tile3.addBox(0.0F, 0.0F, 0.0F, 11, 1, 4, 0.0F);
		tile5 = new ModelRenderer(this, 56, 0);
		tile5.setRotationPoint(7.0F, 0.0F, 0.0F);
		tile5.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		tile6.render(f5);
		tile2.render(f5);
		tile1.render(f5);
		GlStateManager.pushMatrix();
		GlStateManager.translatef(tile4.offsetX, tile4.offsetY, tile4.offsetZ);
		GlStateManager.translatef(tile4.rotationPointX * f5, tile4.rotationPointY * f5, tile4.rotationPointZ * f5);
		GlStateManager.scaled(1.0D, 0.1D, 1.0D);
		GlStateManager.translatef(-tile4.offsetX, -tile4.offsetY, -tile4.offsetZ);
		GlStateManager.translatef(-tile4.rotationPointX * f5, -tile4.rotationPointY * f5, -tile4.rotationPointZ * f5);
		tile4.render(f5);
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.translatef(tile3.offsetX, tile3.offsetY, tile3.offsetZ);
		GlStateManager.translatef(tile3.rotationPointX * f5, tile3.rotationPointY * f5, tile3.rotationPointZ * f5);
		GlStateManager.scaled(1.0D, 0.1D, 1.0D);
		GlStateManager.translatef(-tile3.offsetX, -tile3.offsetY, -tile3.offsetZ);
		GlStateManager.translatef(-tile3.rotationPointX * f5, -tile3.rotationPointY * f5, -tile3.rotationPointZ * f5);
		tile3.render(f5);
		GlStateManager.popMatrix();
		tile5.render(f5);
	}
}