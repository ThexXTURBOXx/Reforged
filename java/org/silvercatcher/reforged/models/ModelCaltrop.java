package org.silvercatcher.reforged.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCaltrop extends ReforgedModel {
	public ModelRenderer spike1;
	public ModelRenderer spike2;
	public ModelRenderer spike3;
	public ModelRenderer spike_up;

	public ModelCaltrop() {
		super();
		spike2 = new ModelRenderer(this, 0, 0);
		spike2.setRotationPoint(-0.5F, -24.0F, 0.4F);
		spike2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
		setRotateAngle(spike2, 0.0F, -0.5462880558742251F, 0.0F);
		spike3 = new ModelRenderer(this, 0, 0);
		spike3.setRotationPoint(0.4F, -24.0F, 0.0F);
		spike3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
		setRotateAngle(spike3, 0.0F, -2.5953045977155678F, 0.0F);
		spike_up = new ModelRenderer(this, 0, 0);
		spike_up.setRotationPoint(0.2F, -24.0F, -0.3F);
		spike_up.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
		setRotateAngle(spike_up, 0.0F, -0.8196066167365371F, 0.0F);
		spike1 = new ModelRenderer(this, 0, 0);
		spike1.setRotationPoint(0.0F, -24.0F, 0.0F);
		spike1.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		spike2.render(f5);
		spike3.render(f5);
		spike_up.render(f5);
		spike1.render(f5);
	}
}