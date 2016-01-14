package org.silvercatcher.reforged.render;

import org.lwjgl.opengl.GL11;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.models.ModelBoomerang;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBoomerang extends Render
{
	protected ModelBase model;
	
	private static final ResourceLocation wood = new ResourceLocation(ReforgedMod.ID + ":textures/entity/wooden_boomerang.png");
	private static final ResourceLocation stone = new ResourceLocation(ReforgedMod.ID + ":textures/entity/stone_boomerang.png");
	private static final ResourceLocation iron = new ResourceLocation(ReforgedMod.ID + ":textures/entity/iron_boomerang.png");
	private static final ResourceLocation gold = new ResourceLocation(ReforgedMod.ID + ":textures/entity/golden_boomerang.png");
	private static final ResourceLocation diamond = new ResourceLocation(ReforgedMod.ID + ":textures/entity/diamond_boomerang.png");

	public RenderBoomerang(RenderManager renderManager) {
		super(renderManager);
		this.model = new ModelBoomerang();
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
		model.render(B, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0475F);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		
		switch(EntityBoomerang.material) {
		
		case EMERALD: return diamond;
		
		case GOLD: return gold;
		
		case IRON: return iron;
		
		case STONE: return stone;
		
		case WOOD: return wood;
		
		default: return null;
		
		}
	}
}