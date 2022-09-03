package org.silvercatcher.reforged.render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.silvercatcher.reforged.ReforgedReferences.Textures;
import org.silvercatcher.reforged.entities.EntityJavelin;
import org.silvercatcher.reforged.models.ModelJavelin;

@SideOnly(Side.CLIENT)
public class RenderJavelin extends ReforgedRender<EntityJavelin> {

    public RenderJavelin(RenderManager renderManager) {
        super(renderManager, new ModelJavelin(), 90);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityJavelin entity) {
        return Textures.JAVELIN;
    }
}
