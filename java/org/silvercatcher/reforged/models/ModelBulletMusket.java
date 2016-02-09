package org.silvercatcher.reforged.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBulletMusket extends ReforgedModel {	
	ModelRenderer Bullet;
	
	public ModelBulletMusket()
	{
		textureWidth = 64;
		textureHeight = 32;
		
		Bullet = new ModelRenderer(this, 0, 0);
		Bullet.addBox(0F, 0F, 0F, 1, 1, 1);
		Bullet.setRotationPoint(0F, 0F, 0F);
		Bullet.setTextureSize(64, 32);
		Bullet.mirror = true;
	}
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		Bullet.render(f5);
	}
}