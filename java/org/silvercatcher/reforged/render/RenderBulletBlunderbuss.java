package org.silvercatcher.reforged.render;

import org.silvercatcher.reforged.ReforgedReferences.Textures;
import org.silvercatcher.reforged.models.ModelBulletMusket;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBulletBlunderbuss extends ReforgedRender {
	
	public RenderBulletBlunderbuss(RenderManager renderManager) {
		super(renderManager, new ModelBulletMusket());
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return Textures.BULLET_MUSKET;
	}
}