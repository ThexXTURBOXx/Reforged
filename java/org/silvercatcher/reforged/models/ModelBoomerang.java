package org.silvercatcher.reforged.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
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
        this.tile6 = new ModelRenderer(this, 56, 0);
        this.tile6.setRotationPoint(0.0F, 0.0F, -7.0F);
        this.tile6.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
        this.tile2 = new ModelRenderer(this, 0, 4);
        this.tile2.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.tile2.addBox(0.0F, 0.0F, 0.0F, 2, 2, 5, 0.0F);
        this.tile1 = new ModelRenderer(this, 0, 0);
        this.tile1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.tile1.addBox(0.0F, 0.0F, 0.0F, 7, 2, 2, 0.0F);
        this.tile4 = new ModelRenderer(this, 14, 3);
        this.tile4.setRotationPoint(-1.0F, 1.0F, -8.0F);
        this.tile4.addBox(0.0F, 0.0F, 0.0F, 4, 1, 7, 0.0F);
        this.tile3 = new ModelRenderer(this, 0, 11);
        this.tile3.setRotationPoint(-1.0F, 1.0F, -1.0F);
        this.tile3.addBox(0.0F, 0.0F, 0.0F, 11, 1, 4, 0.0F);
        this.tile5 = new ModelRenderer(this, 56, 0);
        this.tile5.setRotationPoint(7.0F, 0.0F, 0.0F);
        this.tile5.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.tile6.render(f5);
        this.tile2.render(f5);
        this.tile1.render(f5);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.tile4.offsetX, this.tile4.offsetY, this.tile4.offsetZ);
        GlStateManager.translate(this.tile4.rotationPointX * f5, this.tile4.rotationPointY * f5, this.tile4.rotationPointZ * f5);
        GlStateManager.scale(1.0D, 0.1D, 1.0D);
        GlStateManager.translate(-this.tile4.offsetX, -this.tile4.offsetY, -this.tile4.offsetZ);
        GlStateManager.translate(-this.tile4.rotationPointX * f5, -this.tile4.rotationPointY * f5, -this.tile4.rotationPointZ * f5);
        this.tile4.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.tile3.offsetX, this.tile3.offsetY, this.tile3.offsetZ);
        GlStateManager.translate(this.tile3.rotationPointX * f5, this.tile3.rotationPointY * f5, this.tile3.rotationPointZ * f5);
        GlStateManager.scale(1.0D, 0.1D, 1.0D);
        GlStateManager.translate(-this.tile3.offsetX, -this.tile3.offsetY, -this.tile3.offsetZ);
        GlStateManager.translate(-this.tile3.rotationPointX * f5, -this.tile3.rotationPointY * f5, -this.tile3.rotationPointZ * f5);
        this.tile3.render(f5);
        GlStateManager.popMatrix();
        this.tile5.render(f5);
    }
}
