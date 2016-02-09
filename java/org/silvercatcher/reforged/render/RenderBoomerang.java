package org.silvercatcher.reforged.render;

import org.silvercatcher.reforged.ReforgedReferences.Textures;
import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.models.ModelBoomerang;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBoomerang extends ReforgedRender {
	
	public RenderBoomerang(RenderManager renderManager) {
		super(renderManager, new ModelBoomerang(), 90);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		
		EntityBoomerang entityBoomerang = (EntityBoomerang) entity;
		
		switch(entityBoomerang.getMaterial()) {
		
		case EMERALD: return Textures.DIAMOND_BOOMERANG;
		
		case GOLD: return Textures.GOLDEN_BOOMERANG;
		
		case IRON: return Textures.IRON_BOOMERANG;
		
		case STONE: return Textures.STONE_BOOMERANG;
		
		case WOOD: return Textures.WOODEN_BOOMERANG;
		
		default: return null;
		
		}
	}
}