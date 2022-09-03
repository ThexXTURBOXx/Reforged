package org.silvercatcher.reforged.render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.silvercatcher.reforged.ReforgedReferences.Textures;
import org.silvercatcher.reforged.entities.EntityBulletMusket;
import org.silvercatcher.reforged.models.ModelBulletMusket;

@SideOnly(Side.CLIENT)
public class RenderBulletMusket extends ReforgedRender<EntityBulletMusket> {

    public RenderBulletMusket(RenderManager renderManager) {
        super(renderManager, new ModelBulletMusket(), 0);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBulletMusket entity) {
        return Textures.BULLET_MUSKET;
    }
}
