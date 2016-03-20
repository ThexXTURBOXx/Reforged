package org.silvercatcher.reforged.render;

import org.silvercatcher.reforged.ReforgedReferences.Textures;
import org.silvercatcher.reforged.entities.EntityBulletBlunderbuss;
import org.silvercatcher.reforged.models.ModelBulletMusket;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBulletBlunderbuss extends ReforgedRender implements IRenderFactory<EntityBulletBlunderbuss> {
	
	public RenderBulletBlunderbuss(RenderManager renderManager) {
		super(renderManager, new ModelBulletMusket(), 0.7F, 0);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return Textures.BULLET_MUSKET;
	}

	@Override
	public Render<? super EntityBulletBlunderbuss> createRenderFor(RenderManager manager) {
		return this;
	}
}