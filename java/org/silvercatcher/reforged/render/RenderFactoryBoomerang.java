package org.silvercatcher.reforged.render;

import org.silvercatcher.reforged.ReforgedReferences.Textures;
import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.material.MaterialManager;
import org.silvercatcher.reforged.models.ModelBoomerang;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFactoryBoomerang implements IRenderFactory<EntityBoomerang> {
	
	@Override
	public Render<EntityBoomerang> createRenderFor(RenderManager manager) {
		return new RenderBoomerang(manager);
	}
	
	public static class RenderBoomerang extends ReforgedRender {
		
		public RenderBoomerang(RenderManager renderManager) {
			super(renderManager, new ModelBoomerang(), 90);
		}
		
		@Override
		protected ResourceLocation getEntityTexture(Entity entity) {
			
			EntityBoomerang entityBoomerang = (EntityBoomerang) entity;
			
			switch(entityBoomerang.getMaterial()) {
			
			case DIAMOND: return Textures.DIAMOND_BOOMERANG;
			
			case GOLD: return Textures.GOLDEN_BOOMERANG;
			
			case IRON: return Textures.IRON_BOOMERANG;
			
			case STONE: return Textures.STONE_BOOMERANG;
			
			case WOOD: return Textures.WOODEN_BOOMERANG;
			
			default: if(MaterialManager.isFullyAdded(entityBoomerang.getMaterial())) {
				return MaterialManager.getTextures(entityBoomerang.getMaterial())[0];
			} else {
				throw new IllegalArgumentException("The ToolMaterial called " + entityBoomerang.getMaterial().name() + " couldn't be found!");
			}
			
			}
		}
	}	
}