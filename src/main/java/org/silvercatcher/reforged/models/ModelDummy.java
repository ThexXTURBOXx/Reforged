package org.silvercatcher.reforged.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDummy extends ModelBase {

    public ModelRenderer armLeft;
    public ModelRenderer armRight;
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer stick;
    public ModelRenderer inside;

    public ModelDummy() {
        (this.armLeft = new ModelRenderer(this, 0, 24)).addBox(0.0f, 0.0f, 0.0f, 10, 4, 4);
        this.armLeft.setRotationPoint(6.0f, 18.0f, -2.0f);
        (this.armRight = new ModelRenderer(this, 0, 24)).addBox(-10.0f, 0.0f, 0.0f, 10, 4, 4);
        this.armRight.setRotationPoint(-6.0f, 18.0f, -2.0f);
        (this.body = new ModelRenderer(this, 40, 0)).addBox(0.0f, 0.0f, 0.0f, 6, 8, 6, 3.0f);
        this.body.setRotationPoint(-3.0f, 11.0f, -3.0f);
        (this.inside = new ModelRenderer(this, 40, 14)).addBox(0.0f, 0.0f, 0.0f, 6, 8, 6, 2.0f);
        this.inside.setRotationPoint(-3.0f, 11.0f, -3.0f);
        (this.head = new ModelRenderer(this, 0, 0)).addBox(-5.0f, 0.0f, -5.0f, 6, 6, 6, 2.0f);
        this.head.setRotationPoint(1.5f, 25.0f, 1.5f);
        (this.stick = new ModelRenderer(this, 24, 0)).addBox(0.0f, 0.0f, 0.0f, 4, 10, 4);
        this.stick.setRotationPoint(-2.0f, 0.0f, -2.0f);
    }

    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3,
                       final float f4, final float f5) {
        this.armLeft.render(f5);
        this.armRight.render(f5);
        this.body.render(f5);
        this.inside.render(f5);
        this.head.render(f5);
        this.stick.render(f5);
    }

}
