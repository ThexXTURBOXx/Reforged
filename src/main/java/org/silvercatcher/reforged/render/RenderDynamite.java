package org.silvercatcher.reforged.render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.silvercatcher.reforged.ReforgedReferences.Textures;
import org.silvercatcher.reforged.entities.EntityDynamite;
import org.silvercatcher.reforged.models.ModelDynamite;

@SideOnly(Side.CLIENT)
public class RenderDynamite extends ReforgedRender<EntityDynamite> {

    public RenderDynamite(RenderManager renderManager) {
        super(renderManager, new ModelDynamite(), 0);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityDynamite entity) {
        return Textures.DYNAMITE;
    }
}
