package org.silvercatcher.reforged.render;

import org.lwjgl.opengl.GL11;
import org.silvercatcher.reforged.ReforgedReferences.Textures;
import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.models.ModelBoomerang;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBoomerang extends Render
{
	protected ModelBoomerang model = new ModelBoomerang();

	public RenderBoomerang(RenderManager renderManager) {
		super(renderManager);
	}
	
	

	@Override
	public void doRender(Entity Bullet, double x, double y, double z, float yaw, float partialTick) {
		renderEntityModel(Bullet, x, y, z, yaw, partialTick);
	}

	public void renderEntityModel(Entity Boomerang, double x, double y, double z, float yaw, float partialTick) {
		EntityBoomerang B = (EntityBoomerang) Boomerang;
		GL11.glPushMatrix();
		float scale = 1;
		bindTexture(getEntityTexture(B));
		GL11.glTranslated(x, y, z);
		GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(-yaw, 0, 1F, 0);
		model.render(B, 0, 0, 0, 0, 0, 0.0475F);
		GL11.glPopMatrix();
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