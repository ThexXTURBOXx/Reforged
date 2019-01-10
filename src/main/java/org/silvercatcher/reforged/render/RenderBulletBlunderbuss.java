package org.silvercatcher.reforged.render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.silvercatcher.reforged.ReforgedReferences.Textures;
import org.silvercatcher.reforged.entities.EntityBulletBlunderbuss;
import org.silvercatcher.reforged.models.ModelBulletMusket;

@OnlyIn(Dist.CLIENT)
public class RenderBulletBlunderbuss extends ReforgedRender<EntityBulletBlunderbuss> {

	public RenderBulletBlunderbuss(RenderManager renderManager) {
		super(renderManager, new ModelBulletMusket(), 0.7F, 0);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBulletBlunderbuss entity) {
		return Textures.BULLET_MUSKET;
	}
}