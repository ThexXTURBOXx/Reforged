package org.silvercatcher.reforged.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import org.lwjgl.opengl.GL11;
import org.silvercatcher.reforged.ReforgedReferences.Textures;
import org.silvercatcher.reforged.entities.TileEntityCaltrop;
import org.silvercatcher.reforged.models.ModelCaltrop;
import org.silvercatcher.reforged.models.ReforgedModel;

public class RenderTileEntityCaltrop extends TileEntityRenderer<TileEntityCaltrop> {

	private final ReforgedModel model;

	public RenderTileEntityCaltrop() {
		model = new ModelCaltrop();
	}

	@Override
	public void render(TileEntityCaltrop te, double x, double y, double z, float partialTicks, int destroyStage) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
		Minecraft.getInstance().textureManager.bindTexture(Textures.CALTROP);
		model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}

}