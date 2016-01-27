package org.silvercatcher.reforged.proxy;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.ReforgedReferences.GlobalValues;
import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.entities.EntityBulletMusket;
import org.silvercatcher.reforged.entities.EntityJavelin;
import org.silvercatcher.reforged.gui.ReloadOverlay;
import org.silvercatcher.reforged.items.others.ItemDart;
import org.silvercatcher.reforged.render.RenderBoomerang;
import org.silvercatcher.reforged.render.RenderBulletMusket;
import org.silvercatcher.reforged.render.RenderJavelin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		
		super.preInit(event);
		MinecraftForge.EVENT_BUS.register(new ReloadOverlay());

		if(GlobalValues.BLOWGUN) {
			for(int i = 0; i < ItemDart.dartVariants(); i++) {
				ModelBakery.addVariantName(ReforgedRegistry.DART, ItemDart.getDartModelName(i));
			}
		}
	}
	
	@Override
	public void init(FMLInitializationEvent event) {

		super.init(event);
		registerItemRenderers();
		registerEntityRenderers(Minecraft.getMinecraft().getRenderManager());
	}
	
	@Override
	protected void registerItemRenderers() {
		
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		
		String inventory = "inventory";
		
		for(Item item : ReforgedRegistry.registrationList) {
			mesher.register(item, 0, new ModelResourceLocation(ReforgedMod.ID + ":" 
					+ item.getUnlocalizedName().substring(5), inventory));
		}
		
		if(GlobalValues.BLOWGUN) {
			for(int i = 0; i < ItemDart.dartVariants(); i++) {
			
				mesher.register(ReforgedRegistry.DART, i, new ModelResourceLocation(
						ItemDart.getDartModelName(i), "inventory"));
			}
		}
		
		if(GlobalValues.NEST_OF_BEES) {
			mesher.register(ReforgedRegistry.NEST_OF_BEES, 1, new ModelResourceLocation(ReforgedMod.ID
					+ ReforgedRegistry.NEST_OF_BEES.getUnlocalizedName().substring(5) + "_empty", inventory));
		
			mesher.register(ReforgedRegistry.NEST_OF_BEES, 2, new ModelResourceLocation(ReforgedMod.ID
					+ ReforgedRegistry.NEST_OF_BEES.getUnlocalizedName().substring(5) + "_powder", inventory));
		}
	}
	
	@Override
	protected void registerEntityRenderers(RenderManager manager) {
		
		if(GlobalValues.BOOMERANG) {
			ReforgedRegistry.registerEntityRenderer(EntityBoomerang.class, new RenderBoomerang(manager));
		}
		
		if(GlobalValues.MUSKET) {
			ReforgedRegistry.registerEntityRenderer(EntityBulletMusket.class, new RenderBulletMusket(manager));
		}
		
		if(GlobalValues.JAVELIN) {
			ReforgedRegistry.registerEntityRenderer(EntityJavelin.class, new RenderJavelin(manager));
		}
	}
}
