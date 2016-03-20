package org.silvercatcher.reforged.proxy;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.ReforgedReferences.GlobalValues;
import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.entities.EntityBulletBlunderbuss;
import org.silvercatcher.reforged.entities.EntityBulletMusket;
import org.silvercatcher.reforged.entities.EntityDart;
import org.silvercatcher.reforged.entities.EntityJavelin;
import org.silvercatcher.reforged.gui.ReloadOverlay;
import org.silvercatcher.reforged.render.RenderBoomerang;
import org.silvercatcher.reforged.render.RenderBulletBlunderbuss;
import org.silvercatcher.reforged.render.RenderBulletMusket;
import org.silvercatcher.reforged.render.RenderDart;
import org.silvercatcher.reforged.render.RenderJavelin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		
		super.preInit(event);
		MinecraftForge.EVENT_BUS.register(new ReloadOverlay());
		registerEntityRenderers(Minecraft.getMinecraft().getRenderManager());
	}
	
	@Override
	public void init(FMLInitializationEvent event) {

		super.init(event);
		registerItemRenderers();
	}
	
	@Override
	protected void registerItemRenderers() {
		
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		
		String inventory = "inventory";
		
		for(Item item : ReforgedRegistry.registrationList) {
			mesher.register(item, 0, new ModelResourceLocation(ReforgedMod.ID + ":" 
					+ item.getUnlocalizedName().substring(5), inventory));
		}
		
		if(GlobalValues.NEST_OF_BEES) {
			mesher.register(ReforgedAdditions.NEST_OF_BEES, 1, new ModelResourceLocation(ReforgedMod.ID
					+ ReforgedAdditions.NEST_OF_BEES.getUnlocalizedName().substring(5) + "_empty", inventory));
		
			mesher.register(ReforgedAdditions.NEST_OF_BEES, 2, new ModelResourceLocation(ReforgedMod.ID
					+ ReforgedAdditions.NEST_OF_BEES.getUnlocalizedName().substring(5) + "_powder", inventory));
		}
	}
	
	@Override
	protected void registerEntityRenderers(RenderManager manager) {
		
		if(GlobalValues.BOOMERANG) {
			ReforgedRegistry.registerEntityRenderer(EntityBoomerang.class, new RenderBoomerang(manager));
		}
		
		if(GlobalValues.MUSKET) {
			ReforgedRegistry.registerEntityRenderer(EntityBulletMusket.class, new RenderBulletMusket(manager));
			ReforgedRegistry.registerEntityRenderer(EntityBulletBlunderbuss.class, new RenderBulletBlunderbuss(manager));
		}
		
		if(GlobalValues.JAVELIN) {
			ReforgedRegistry.registerEntityRenderer(EntityJavelin.class, new RenderJavelin(manager));
		}
		
		if(GlobalValues.BLOWGUN) {
			ReforgedRegistry.registerEntityRenderer(EntityDart.class, new RenderDart(manager));
		}
	}
}
