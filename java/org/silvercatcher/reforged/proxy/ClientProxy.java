package org.silvercatcher.reforged.proxy;

import org.silvercatcher.reforged.ReforgedItems;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.entities.EntityDiamondBoomerang;
import org.silvercatcher.reforged.entities.EntityGoldenBoomerang;
import org.silvercatcher.reforged.entities.EntityIronBoomerang;
import org.silvercatcher.reforged.entities.EntityStoneBoomerang;
import org.silvercatcher.reforged.entities.EntityWoodenBoomerang;
import org.silvercatcher.reforged.gui.ReloadOverlay;
import org.silvercatcher.reforged.render.RendererDiamondBoomerang;
import org.silvercatcher.reforged.render.RendererGoldenBoomerang;
import org.silvercatcher.reforged.render.RendererIronBoomerang;
import org.silvercatcher.reforged.render.RendererStoneBoomerang;
import org.silvercatcher.reforged.render.RendererWoodenBoomerang;
import org.silvercatcher.reforged.weapons.ItemBoomerang;
import org.silvercatcher.reforged.weapons.ReforgedItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		MinecraftForge.EVENT_BUS.register(new ReloadOverlay());
	}
	
	@Override
	public void init(FMLInitializationEvent event) {

		super.init(event);
		registerRenderers();
	}
	
	@Override
	protected void registerRenderers() {
		
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		
		for(ReforgedItem item : ReforgedItems.registratonList) {
			mesher.register(item, 0, new ModelResourceLocation(ReforgedMod.ID + ":" 
					+ item.getName(), "inventory"));
		}
		
		//Boomerangs
		RenderingRegistry.registerEntityRenderingHandler(EntityWoodenBoomerang.class, new RendererWoodenBoomerang(Minecraft.getMinecraft().getRenderManager()));
		RenderingRegistry.registerEntityRenderingHandler(EntityStoneBoomerang.class, new RendererStoneBoomerang(Minecraft.getMinecraft().getRenderManager()));
		RenderingRegistry.registerEntityRenderingHandler(EntityGoldenBoomerang.class, new RendererGoldenBoomerang(Minecraft.getMinecraft().getRenderManager()));
		RenderingRegistry.registerEntityRenderingHandler(EntityIronBoomerang.class, new RendererIronBoomerang(Minecraft.getMinecraft().getRenderManager()));
		RenderingRegistry.registerEntityRenderingHandler(EntityDiamondBoomerang.class, new RendererDiamondBoomerang(Minecraft.getMinecraft().getRenderManager()));
	}
}
