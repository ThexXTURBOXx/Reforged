package org.silvercatcher.reforged.render;

import org.silvercatcher.reforged.ReforgedReferences.Textures;
import org.silvercatcher.reforged.entities.EntityDart;
import org.silvercatcher.reforged.models.ModelDart;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDart extends ReforgedRender {
	
	public RenderDart(RenderManager renderManager) {
		super(renderManager, new ModelDart(), -90);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		
		EntityDart entityDart = (EntityDart) entity;
		
		switch(entityDart.getEffect()) {
		
		case "normal": return Textures.NORMAL_DART;
		
		case "hunger": return Textures.HUNGER_DART;
		
		case "poison": return Textures.POISON_DART;
		
		case "poison_strong": return Textures.POISON_2_DART;
		
		case "slowness": return Textures.SLOW_DART;
					 	 
		case "wither": return Textures.WITHER_DART;
		
		default: throw new IllegalArgumentException("No Dart-Effect called " + entityDart.getEffect() + " found!");
				
		}
	}
	
}