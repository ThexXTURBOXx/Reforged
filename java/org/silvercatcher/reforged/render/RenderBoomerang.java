package org.silvercatcher.reforged.render;

import org.lwjgl.opengl.GL11;
import org.silvercatcher.reforged.api.ReforgedReferences.Textures;
import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.models.ModelBoomerang;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBoomerang extends ReforgedRender {
	
	float yawb = modifier;
	
	public RenderBoomerang(RenderManager renderManager) {
		super(renderManager, new ModelBoomerang(), 90);
	}
	
	@Override
	public void renderEntityModel(Entity theEntity, double x, double y, double z, float yaw, float partialTick) {
		GL11.glPushMatrix();
		bindTexture(getEntityTexture(theEntity));
		GL11.glTranslated(x, y, z);
		GL11.glScalef(scale, scale, scale);
		//If the number is set higher, then the Boomerang will rotate faster, otherwise slower
		yawb += 15;
		GL11.glRotated(yawb, 0, 1, 0);
		model.render(theEntity,(float) x,(float) y,(float) z, yawb, partialTick, 0.0475F);
		GL11.glPopMatrix();
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		
		EntityBoomerang entityBoomerang = (EntityBoomerang) entity;
		
		switch(entityBoomerang.getMaterial().name()) {
		
		case "EMERALD": return Textures.DIAMOND_BOOMERANG;
		
		case "GOLD": return Textures.GOLDEN_BOOMERANG;
		
		case "IRON": return Textures.IRON_BOOMERANG;
		
		case "STONE": return Textures.STONE_BOOMERANG;
		
		case "WOOD": return Textures.WOODEN_BOOMERANG;

		case "THAUMIUM": return Textures.THAUMIUM_BOOMERANG;
		
		case "DARK_MATTER": return Textures.DM_BOOMERANG;
		
		case "RED_MATTER": return Textures.RM_BOOMERANG;
		
		default: return null;
		
		}
	}
}