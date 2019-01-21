package org.silvercatcher.reforged.render;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;
import org.silvercatcher.reforged.ReforgedReferences.Textures;
import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.material.MaterialManager;
import org.silvercatcher.reforged.models.ModelBoomerang;

@OnlyIn(Dist.CLIENT)
public class RenderBoomerang extends ReforgedRender<EntityBoomerang> {

	public RenderBoomerang(RenderManager renderManager) {
		super(renderManager, new ModelBoomerang(), 90);
		System.out.println("ALLAHUADDDD");
	}

	@Nonnull
	@ParametersAreNonnullByDefault
	@Override
	protected ResourceLocation getEntityTexture(EntityBoomerang entity) {
		if (entity.getMaterialDefinition() == null)
			return Textures.WOODEN_BOOMERANG;
		switch (entity.getMaterialDefinition().getPrefix()) {
			case "diamond":
				return Textures.DIAMOND_BOOMERANG;
			case "golden":
				return Textures.GOLDEN_BOOMERANG;
			case "iron":
				return Textures.IRON_BOOMERANG;
			case "stone":
				return Textures.STONE_BOOMERANG;
			case "wooden":
				return Textures.WOODEN_BOOMERANG;
			default:
				if (MaterialManager.isFullyAdded(entity.getMaterialDefinition().getMaterial())) {
					return MaterialManager.getTextures(entity.getMaterialDefinition().getMaterial())[0];
				} else {
					return Textures.WOODEN_BOOMERANG;
				}
		}
	}

	@Override
	public void renderEntityModel(EntityBoomerang theEntity, double x, double y, double z, float yaw,
								  float partialTick) {
		GL11.glPushMatrix();
		bindEntityTexture(theEntity);
		GL11.glTranslated(x, y, z);
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(
				(theEntity.prevRotationYaw + (theEntity.rotationYaw - theEntity.prevRotationYaw) * partialTick) - 90F,
				0.0F, 1.0F, 0.0F);
		model.render(theEntity, (float) x, (float) y, (float) z, yaw, partialTick, 0.0475F);
		GL11.glPopMatrix();
	}

}