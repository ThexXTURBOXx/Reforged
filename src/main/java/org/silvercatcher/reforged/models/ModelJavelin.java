package org.silvercatcher.reforged.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelJavelin extends ReforgedModel {
	public ModelRenderer stick;
	public ModelRenderer front1;
	public ModelRenderer front2;

	public ModelJavelin() {
		super();
		front2 = new ModelRenderer(this, 0, 6);
		front2.setRotationPoint(0.0F, 0.0F, 0.0F);
		front2.addBox(-1.3F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		setRotateAngle(front2, 0.7853981633974483F, 3.141592653589793F, 0.0F);
		front1 = new ModelRenderer(this, 0, 2);
		front1.setRotationPoint(0.0F, 0.0F, 0.0F);
		front1.addBox(-0.75F, -0.25F, -0.25F, 1, 1, 1, 0.0F);
		setRotateAngle(front1, 0.7853981633974483F, 3.141592653589793F, 0.0F);
		stick = new ModelRenderer(this, 0, 0);
		stick.setRotationPoint(0.0F, 0.0F, 0.0F);
		stick.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		setRotateAngle(stick, 0.7853981633974483F, 3.141592653589793F, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(front2.offsetX, front2.offsetY, front2.offsetZ);
		GlStateManager.translate(front2.rotationPointX * f5, front2.rotationPointY * f5, front2.rotationPointZ * f5);
		GlStateManager.scale(2.0D, 1.0D, 1.0D);
		GlStateManager.translate(-front2.offsetX, -front2.offsetY, -front2.offsetZ);
		GlStateManager.translate(-front2.rotationPointX * f5, -front2.rotationPointY * f5, -front2.rotationPointZ * f5);
		front2.render(f5);
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.translate(front1.offsetX, front1.offsetY, front1.offsetZ);
		GlStateManager.translate(front1.rotationPointX * f5, front1.rotationPointY * f5, front1.rotationPointZ * f5);
		GlStateManager.scale(1.0D, 2.0D, 2.0D);
		GlStateManager.translate(-front1.offsetX, -front1.offsetY, -front1.offsetZ);
		GlStateManager.translate(-front1.rotationPointX * f5, -front1.rotationPointY * f5, -front1.rotationPointZ * f5);
		front1.render(f5);
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.translate(stick.offsetX, stick.offsetY, stick.offsetZ);
		GlStateManager.translate(stick.rotationPointX * f5, stick.rotationPointY * f5, stick.rotationPointZ * f5);
		GlStateManager.scale(30.0D, 1.0D, 1.0D);
		GlStateManager.translate(-stick.offsetX, -stick.offsetY, -stick.offsetZ);
		GlStateManager.translate(-stick.rotationPointX * f5, -stick.rotationPointY * f5, -stick.rotationPointZ * f5);
		stick.render(f5);
		GlStateManager.popMatrix();
	}
}
