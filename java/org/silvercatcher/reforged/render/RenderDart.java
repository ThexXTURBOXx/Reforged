package org.silvercatcher.reforged.render;

import org.lwjgl.opengl.GL11;
import org.silvercatcher.reforged.ReforgedReferences.Textures;
import org.silvercatcher.reforged.entities.EntityDart;
import org.silvercatcher.reforged.models.ModelDart;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDart extends Render
{
	protected ModelDart model = new ModelDart();

	public RenderDart(RenderManager renderManager) {
		super(renderManager);
	}
	
	

	@Override
	public void doRender(Entity Bullet, double x, double y, double z, float yaw, float partialTick) {
		renderEntityModel(Bullet, x, y, z, yaw, partialTick);
	}

	public void renderEntityModel(Entity Dart, double x, double y, double z, float yaw, float partialTick) {
		EntityDart D = (EntityDart) Dart;
		GL11.glPushMatrix();
		float scale = 1;
		bindTexture(getEntityTexture(D));
		GL11.glTranslated(x, y, z);
		GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(-yaw, 0, 1F, 0);
		model.render(D, 0, 0, 0, 0, 0, 0.0475F);
		GL11.glPopMatrix();
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
		
		default: throw new IllegalArgumentException("No Item called " + entityDart.getEffect() + " found!");
				
		}
	}
}