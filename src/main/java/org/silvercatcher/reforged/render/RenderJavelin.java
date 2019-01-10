package org.silvercatcher.reforged.render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.silvercatcher.reforged.ReforgedReferences.Textures;
import org.silvercatcher.reforged.entities.EntityJavelin;
import org.silvercatcher.reforged.models.ModelJavelin;

@OnlyIn(Dist.CLIENT)
public class RenderJavelin extends ReforgedRender<EntityJavelin> {

	public RenderJavelin(RenderManager renderManager) {
		super(renderManager, new ModelJavelin(), 90);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityJavelin entity) {
		return Textures.JAVELIN;
	}
}