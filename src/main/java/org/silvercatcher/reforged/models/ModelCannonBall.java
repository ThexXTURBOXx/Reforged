package org.silvercatcher.reforged.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCannonBall extends ReforgedModel {

    public ModelRenderer ball;

    public ModelCannonBall() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.ball = new ModelRenderer(this, 0, 0);
        this.ball.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.ball.addBox(0.0F, 0.0F, 0.0F, 8, 8, 8, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.ball.render(f5);
    }

}
