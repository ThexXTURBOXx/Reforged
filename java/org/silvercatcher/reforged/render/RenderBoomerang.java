package org.silvercatcher.reforged.render;

import org.lwjgl.opengl.GL11;
import org.silvercatcher.reforged.ReforgedReferences.Textures;
import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.material.MaterialManager;
import org.silvercatcher.reforged.models.ModelBoomerang;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBoomerang extends ReforgedRender {
	
	protected float yawb = modifier;
	
	public RenderBoomerang(RenderManager renderManager) {
		super(renderManager, new ModelBoomerang(), 90);
	}
	
	@Override
	public void renderEntityModel(Entity theEntity, double x, double y, double z, float yaw, float partialTick) {
		GL11.glPushMatrix();
		bindTexture(getEntityTexture(theEntity));
		GL11.glTranslated(x, y, z);
		GL11.glScalef(scale, scale, scale);
		int boomerangCount = 1;
		for(Object o : Minecraft.getMinecraft().theWorld.loadedEntityList) {
			if(o instanceof EntityBoomerang) {
				EntityPlayer player = Minecraft.getMinecraft().thePlayer;
				EntityBoomerang boomerang = (EntityBoomerang) o;
				Vec3 look = player.getLookVec();
				Vec3 boomerangvec = new Vec3(boomerang.posX - player.posX,
						(boomerang.getEntityBoundingBox().minY + boomerang.height / 2)
						- player.posY + player.getEyeHeight(),
						boomerang.posZ - player.posZ);
				double d0 = boomerangvec.lengthVector();
				double d1 = look.dotProduct(boomerangvec);
				boolean seen = d1 > 1 - 0.25 / d0;
				if(seen && Minecraft.getMinecraft().thePlayer.canEntityBeSeen(boomerang)) {
					boomerangCount++;
				}
			}
		}
		//If the number is set higher, then the Boomerang will rotate faster, otherwise slower
		yawb += ((double) 5 / boomerangCount);
		GL11.glRotated(yawb, 0, 1, 0);
		model.render(theEntity,(float) x,(float) y,(float) z, yawb, partialTick, 0.0475F);
		GL11.glPopMatrix();
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		
		EntityBoomerang entityBoomerang = (EntityBoomerang) entity;
		
		switch(entityBoomerang.getMaterialDefinition().getPrefix()) {
		case "diamond": return Textures.DIAMOND_BOOMERANG;
		case "golden": return Textures.GOLDEN_BOOMERANG;
		case "iron": return Textures.IRON_BOOMERANG;
		case "stone": return Textures.STONE_BOOMERANG;
		case "wooden": return Textures.WOODEN_BOOMERANG;
		default:
			if(MaterialManager.isFullyAdded(entityBoomerang.getMaterialDefinition().getMaterial())) {
				return MaterialManager.getTextures(entityBoomerang.getMaterialDefinition().getMaterial())[0];
			} else {
				return null;
			}		
		}
	}
}