package org.silvercatcher.reforged.models;

import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCannonBase extends ReforgedModel {

	public ModelRenderer bottom;
	public ModelRenderer middle;
	public ModelRenderer up;

	public ModelCannonBase() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.bottom = new ModelRenderer(this, 0, 14);
		this.bottom.setRotationPoint(-2.0000000000000004F, 0.0F, -2.0000000000000004F);
		this.bottom.addBox(0.0F, 0.0F, 0.0F, 4, 2, 4, 0.0F);
		this.up = new ModelRenderer(this, 21, 14);
		this.up.setRotationPoint(-0.9999999999999998F, -2.0F, -0.9999999999999998F);
		this.up.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2, 0.0F);
		this.middle = new ModelRenderer(this, 12, 14);
		this.middle.setRotationPoint(-1.5000000000000002F, -1.0F, -1.5000000000000002F);
		this.middle.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.middle.render(f5);
		this.up.render(f5);
		this.bottom.render(f5);
	}

}
