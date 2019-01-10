package org.silvercatcher.reforged.render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.silvercatcher.reforged.ReforgedReferences.Textures;
import org.silvercatcher.reforged.entities.EntityDart;
import org.silvercatcher.reforged.models.ModelDart;

@OnlyIn(Dist.CLIENT)
public class RenderDart extends ReforgedRender<EntityDart> {

	public RenderDart(RenderManager renderManager) {
		super(renderManager, new ModelDart(), -90);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityDart entity) {

		switch (entity.getEffect()) {

			case "normal":
				return Textures.NORMAL_DART;

			case "hunger":
				return Textures.HUNGER_DART;

			case "poison":
				return Textures.POISON_DART;

			case "poison_strong":
				return Textures.POISON_2_DART;

			case "slowness":
				return Textures.SLOW_DART;

			case "wither":
				return Textures.WITHER_DART;

			default:
				throw new IllegalArgumentException("No Item called " + entity.getEffect() + " found!");

		}
	}
}