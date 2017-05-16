package org.silvercatcher.reforged.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelJavelin extends ReforgedModel {
    public ModelRenderer stick;
    public ModelRenderer front1;
    public ModelRenderer front2;

    public ModelJavelin() {
    	super();
        front2 = new ModelRenderer(this, 0, 6);
        front2.setRotationPoint(-18.7F, 0.0F, 0.0F);
        front2.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        setRotateAngle(front2, 0.7853981633974483F, 0.0F, 0.0F);
        front1 = new ModelRenderer(this, 0, 2);
        front1.setRotationPoint(-16.7F, 0.0F, -0.75F);
        front1.addBox(0.0F, 0.0F, 0.0F, 1, 2, 2, 0.0F);
        setRotateAngle(front1, 0.7853981633974483F, 0.011077130710365668F, 0.0F);
        stick = new ModelRenderer(this, 0, 0);
        stick.setRotationPoint(-16.0F, 0.0F, 0.0F);
        stick.addBox(0.0F, 0.0F, 0.0F, 30, 1, 1, 0.0F);
        setRotateAngle(stick, 0.7853981633974483F, 0.0F, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        front2.render(f5);
        front1.render(f5);
        stick.render(f5);
    }
}
