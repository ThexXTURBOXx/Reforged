package org.silvercatcher.reforged.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;
import org.silvercatcher.reforged.ReforgedReferences.Textures;
import org.silvercatcher.reforged.entities.TileEntityCaltrop;
import org.silvercatcher.reforged.models.ModelCaltrop;
import org.silvercatcher.reforged.models.ReforgedModel;

public class RenderTileEntityCaltrop extends TileEntitySpecialRenderer<TileEntityCaltrop> {

    private final ReforgedModel model;

    public RenderTileEntityCaltrop() {
        model = new ModelCaltrop();
    }

    @Override
    public void render(TileEntityCaltrop te, double x, double y, double z, float scale, int i, float partialTicks) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        Minecraft.getMinecraft().renderEngine.bindTexture(Textures.CALTROP);
        GL11.glPushMatrix();
        model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}
