package org.silvercatcher.reforged.proxy;

import org.silvercatcher.reforged.ReforgedItems;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.gui.ReloadOverlay;
import org.silvercatcher.reforged.render.RenderBoomerang;
import org.silvercatcher.reforged.weapons.ItemBoomerang;
import org.silvercatcher.reforged.weapons.ReforgedItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
		
		//mesher.register(ReforgedItems.WOODEN_BOOMERANG, 1, new ModelResourceLocation(
			//	ReforgedMod.ID + ":" + ReforgedItems.WOODEN_BOOMERANG.getName(), "inventory"));
		
		RenderManager manager = Minecraft.getMinecraft().getRenderManager();
		
		//Boomerangs
		RenderingRegistry.registerEntityRenderingHandler(EntityBoomerang.class, new RenderBoomerang(manager));
	}
}
