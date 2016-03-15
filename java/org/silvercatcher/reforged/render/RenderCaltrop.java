package org.silvercatcher.reforged.render;

import org.silvercatcher.reforged.ReforgedReferences.Textures;
import org.silvercatcher.reforged.models.ModelCaltrop;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCaltrop extends ReforgedRender {
	
	public RenderCaltrop(RenderManager renderManager) {
		super(renderManager, new ModelCaltrop(), 0);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return Textures.CALTROP;
	}
}