package org.silvercatcher.reforged.render;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.entities.EntityBulletMusket;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBulletMusket extends RenderEntity
{
	private static ResourceLocation texture = 
			new ResourceLocation(ReforgedMod.ID, "textures/entity/BulletMusket.png");
	
    public RenderBulletMusket(RenderManager modelbase)
    {
    	super(modelbase);
    }
    
    public void renderBM(EntityBulletMusket par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        super.doRender(par1Entity, par2, par4, par6, par8, par9);
        bindTexture(this.texture);
    }
    
    @Override
	public void doRender(Entity entity, double x, double y, double z, float u, float v)
    {
    	renderBM((EntityBulletMusket) entity, (double) x, (double) y, (double) z, u, v);
    }
}