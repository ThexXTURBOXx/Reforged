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
        this.shapefront = new ModelRenderer(this, 58, 0);
        this.shapefront.setRotationPoint(0.0F, 0.3F, 0.3F);
        this.shapefront.addBox(1.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.shapebehind = new ModelRenderer(this, 0, 0);
        this.shapebehind.setRotationPoint(-0.6F, 0.0F, 0.0F);
        this.shapebehind.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.shapering = new ModelRenderer(this, 58, 2);
        this.shapering.setRotationPoint(-0.8F, 0.0F, 0.0F);
        this.shapering.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.shapebehind2 = new ModelRenderer(this, 0, 2);
        this.shapebehind2.setRotationPoint(-1.5F, 0.0F, 0.0F);
        this.shapebehind2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.shapefront.offsetX, this.shapefront.offsetY, this.shapefront.offsetZ);
        GlStateManager.translate(this.shapefront.rotationPointX * f5, this.shapefront.rotationPointY * f5, this.shapefront.rotationPointZ * f5);
        GlStateManager.scale(0.6D, 0.1D, 0.1D);
        GlStateManager.translate(-this.shapefront.offsetX, -this.shapefront.offsetY, -this.shapefront.offsetZ);
        GlStateManager.translate(-this.shapefront.rotationPointX * f5, -this.shapefront.rotationPointY * f5, -this.shapefront.rotationPointZ * f5);
        this.shapefront.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.shapebehind.offsetX, this.shapebehind.offsetY, this.shapebehind.offsetZ);
        GlStateManager.translate(this.shapebehind.rotationPointX * f5, this.shapebehind.rotationPointY * f5, this.shapebehind.rotationPointZ * f5);
        GlStateManager.scale(0.8D, 0.7D, 0.7D);
        GlStateManager.translate(-this.shapebehind.offsetX, -this.shapebehind.offsetY, -this.shapebehind.offsetZ);
        GlStateManager.translate(-this.shapebehind.rotationPointX * f5, -this.shapebehind.rotationPointY * f5, -this.shapebehind.rotationPointZ * f5);
        this.shapebehind.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.shapering.offsetX, this.shapering.offsetY, this.shapering.offsetZ);
        GlStateManager.translate(this.shapering.rotationPointX * f5, this.shapering.rotationPointY * f5, this.shapering.rotationPointZ * f5);
        GlStateManager.scale(0.1D, 0.7D, 0.7D);
        GlStateManager.translate(-this.shapering.offsetX, -this.shapering.offsetY, -this.shapering.offsetZ);
        GlStateManager.translate(-this.shapering.rotationPointX * f5, -this.shapering.rotationPointY * f5, -this.shapering.rotationPointZ * f5);
        this.shapering.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.shapebehind2.offsetX, this.shapebehind2.offsetY, this.shapebehind2.offsetZ);
        GlStateManager.translate(this.shapebehind2.rotationPointX * f5, this.shapebehind2.rotationPointY * f5, this.shapebehind2.rotationPointZ * f5);
        GlStateManager.scale(0.7D, 0.7D, 0.7D);
        GlStateManager.translate(-this.shapebehind2.offsetX, -this.shapebehind2.offsetY, -this.shapebehind2.offsetZ);
        GlStateManager.translate(-this.shapebehind2.rotationPointX * f5, -this.shapebehind2.rotationPointY * f5, -this.shapebehind2.rotationPointZ * f5);
        this.shapebehind2.render(f5);
        GlStateManager.popMatrix();
    }
}
