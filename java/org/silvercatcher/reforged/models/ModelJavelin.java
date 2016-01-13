package org.silvercatcher.reforged.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelJavelin extends ModelBase {
    public ModelRenderer stick;
    public ModelRenderer front1;
    public ModelRenderer front2;

    public ModelJavelin() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.front2 = new ModelRenderer(this, 0, 6);
        this.front2.setRotationPoint(-18.7F, 0.0F, 0.0F);
        this.front2.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.setRotateAngle(front2, 0.7853981633974483F, 0.0F, 0.0F);
        this.front1 = new ModelRenderer(this, 0, 2);
        this.front1.setRotationPoint(-16.7F, 0.0F, -0.75F);
        this.front1.addBox(0.0F, 0.0F, 0.0F, 1, 2, 2, 0.0F);
        this.setRotateAngle(front1, 0.7853981633974483F, 0.011077130710365668F, 0.0F);
        this.stick = new ModelRenderer(this, 0, 0);
        this.stick.setRotationPoint(-16.0F, 0.0F, 0.0F);
        this.stick.addBox(0.0F, 0.0F, 0.0F, 30, 1, 1, 0.0F);
        this.setRotateAngle(stick, 0.7853981633974483F, 0.0F, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.front2.render(f5);
        this.front1.render(f5);
        this.stick.render(f5);
    }
    
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
