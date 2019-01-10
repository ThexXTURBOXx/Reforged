package org.silvercatcher.reforged.models;

import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCannonHolder extends ReforgedModel {

	public ModelRenderer bottom;
	public ModelRenderer left_up;
	public ModelRenderer right_up;
	public ModelRenderer screw;

	public ModelCannonHolder() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.bottom = new ModelRenderer(this, 0, 20);
		this.bottom.setRotationPoint(-3.0000000000000013F, 0.0F, -0.9999999999999999F);
		this.bottom.addBox(0.0F, 0.0F, 0.0F, 6, 1, 2, 0.0F);
		this.left_up = new ModelRenderer(this, 16, 20);
		this.left_up.setRotationPoint(-3.0000000000000013F, -4.0F, -0.9999999999999999F);
		this.left_up.addBox(0.0F, 0.0F, 0.0F, 1, 4, 2, 0.0F);
		this.screw = new ModelRenderer(this, 14, 20);
		this.screw.setRotationPoint(-0.5000000000000011F, -0.5F, -0.5F);
		this.screw.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.right_up = new ModelRenderer(this, 22, 20);
		this.right_up.setRotationPoint(2.0000000000000018F, -4.0F, -0.9999999999999999F);
		this.right_up.addBox(0.0F, 0.0F, 0.0F, 1, 4, 2, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.right_up.render(f5);
		this.bottom.render(f5);
		this.screw.render(f5);
		this.left_up.render(f5);
	}

}
