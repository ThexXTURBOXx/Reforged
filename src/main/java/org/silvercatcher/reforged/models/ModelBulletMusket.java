package org.silvercatcher.reforged.models;

import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBulletMusket extends ReforgedModel {

	ModelRenderer bullet;

	public ModelBulletMusket() {
		super();
		bullet = new ModelRenderer(this, 0, 0);
		bullet.addBox(0F, 0F, 0F, 1, 1, 1);
		bullet.setRotationPoint(0F, 0F, 0F);
		bullet.setTextureSize(64, 32);
		bullet.mirror = true;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		bullet.render(f5);
	}
}