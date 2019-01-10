package org.silvercatcher.reforged.render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.silvercatcher.reforged.ReforgedReferences.Textures;
import org.silvercatcher.reforged.entities.EntityCannonBall;
import org.silvercatcher.reforged.models.ModelBulletMusket;

@OnlyIn(Dist.CLIENT)
public class RenderCannonBall extends ReforgedRender<EntityCannonBall> {

	public RenderCannonBall(RenderManager renderManager) {
		super(renderManager, new ModelBulletMusket(), 0);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCannonBall entity) {
		return Textures.CANNON_BALL;
	}
}