package org.silvercatcher.reforged.proxy;

import org.silvercatcher.reforged.ReforgedItems;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.gui.ReloadOverlay;
import org.silvercatcher.reforged.weapons.ReforgedItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.common.MinecraftForge;
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
	}
}
