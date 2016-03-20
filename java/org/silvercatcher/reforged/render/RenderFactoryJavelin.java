package org.silvercatcher.reforged.render;

import org.silvercatcher.reforged.ReforgedReferences.Textures;
import org.silvercatcher.reforged.entities.EntityJavelin;
import org.silvercatcher.reforged.models.ModelJavelin;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFactoryJavelin implements IRenderFactory<EntityJavelin> {
	
	@Override
	public Render<EntityJavelin> createRenderFor(RenderManager manager) {
		return new RenderJavelin(manager);
	}
	
	public static class RenderJavelin extends ReforgedRender {
		
		public RenderJavelin(RenderManager renderManager) {
			super(renderManager, new ModelJavelin(), 90);
		}
		
		@Override
		protected ResourceLocation getEntityTexture(Entity entity) {
			return Textures.JAVELIN;
		}
	}
}