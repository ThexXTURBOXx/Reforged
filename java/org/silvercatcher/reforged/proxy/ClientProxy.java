package org.silvercatcher.reforged.proxy;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.ReforgedReferences.GlobalValues;
import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.entities.*;
import org.silvercatcher.reforged.gui.ReloadOverlay;
import org.silvercatcher.reforged.render.*;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.*;
import net.minecraftforge.fml.common.event.*;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		
		super.preInit(event);
		MinecraftForge.EVENT_BUS.register(new ReloadOverlay());
	}
	
	@Override
	public void init(FMLInitializationEvent event) {

		super.init(event);
		registerItemRenderers();
		registerEntityRenderers();
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
	
	@Override
	protected void registerItemRenderers() {
		
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		
		String inventory = "inventory";
		/*
		if(GlobalValues.CROSSBOW) {
			ModelBakery.registerItemVariants(ReforgedAdditions.CROSSBOW, new ResourceLocation(ReforgedMod.ID + ":crossbow"),
					new ResourceLocation(ReforgedMod.ID + ":crossbow_1"), new ResourceLocation(ReforgedMod.ID + ":crossbow_2"), new ResourceLocation(ReforgedMod.ID + ":crossbow_3"),
					new ResourceLocation(ReforgedMod.ID + ":crossbow_4"), new ResourceLocation(ReforgedMod.ID + ":crossbow_5"));
		}
		*/
		for(Item item : ReforgedRegistry.registrationList) {
			mesher.register(item, 0, new ModelResourceLocation(ReforgedMod.ID + ":" 
					+ item.getUnlocalizedName().substring(5), inventory));
		}
		
		for(Block item : ReforgedRegistry.registrationListBlocks) {
			mesher.register(Item.getItemFromBlock(item), 0, new ModelResourceLocation(ReforgedMod.ID + ":" 
					+ item.getUnlocalizedName().substring(5), inventory));
		}
		
		if(GlobalValues.NEST_OF_BEES) {
			mesher.register(ReforgedAdditions.NEST_OF_BEES, 1, new ModelResourceLocation(ReforgedMod.ID + ":"
					+ ReforgedAdditions.NEST_OF_BEES.getUnlocalizedName().substring(5) + "_empty", inventory));
		
			mesher.register(ReforgedAdditions.NEST_OF_BEES, 2, new ModelResourceLocation(ReforgedMod.ID + ":"
					+ ReforgedAdditions.NEST_OF_BEES.getUnlocalizedName().substring(5) + "_powder", inventory));
		}
		/*
		if(GlobalValues.CROSSBOW) {
			for(int i = 1; i <= 5; i++) {
				mesher.register(ReforgedAdditions.CROSSBOW, i, new ModelResourceLocation(ReforgedMod.ID + ":"
						+ "crossbow_" + i));				
			}
		}*/
	}
	
	@Override
	protected void registerEntityRenderers() {
		
		if(GlobalValues.BOOMERANG) {
			RenderingRegistry.registerEntityRenderingHandler(EntityBoomerang.class, new IRenderFactory<EntityBoomerang>() {
				@Override
				public Render<? super EntityBoomerang> createRenderFor(RenderManager manager) {
					return new RenderBoomerang(manager);
				}
			});
		}
		
		if(GlobalValues.MUSKET) {
			RenderingRegistry.registerEntityRenderingHandler(EntityBulletMusket.class, new IRenderFactory<EntityBulletMusket>() {
				@Override
				public Render<? super EntityBulletMusket> createRenderFor(RenderManager manager) {
					return new RenderBulletMusket(manager);
				}
			});
			RenderingRegistry.registerEntityRenderingHandler(EntityBulletBlunderbuss.class, new IRenderFactory<EntityBulletBlunderbuss>() {
				@Override
				public Render<? super EntityBulletBlunderbuss> createRenderFor(RenderManager manager) {
					return new RenderBulletBlunderbuss(manager);
				}
			});
		}
		
		if(GlobalValues.JAVELIN)
			RenderingRegistry.registerEntityRenderingHandler(EntityJavelin.class, new IRenderFactory<EntityJavelin>() {
				@Override
				public Render<? super EntityJavelin> createRenderFor(RenderManager manager) {
					return new RenderJavelin(manager);
				}
			});
		if(GlobalValues.BLOWGUN)
			RenderingRegistry.registerEntityRenderingHandler(EntityDart.class, new IRenderFactory<EntityDart>() {
				@Override
				public Render<? super EntityDart> createRenderFor(RenderManager manager) {
					return new RenderDart(manager);
				}
			});
		if(GlobalValues.CALTROP) ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCaltropEntity.class, new RenderTileEntityCaltrop());
		if(GlobalValues.DYNAMITE)
			RenderingRegistry.registerEntityRenderingHandler(EntityDynamite.class, new IRenderFactory<EntityDynamite>() {
				@Override
				public Render<? super EntityDynamite> createRenderFor(RenderManager manager) {
					return new RenderDynamite(manager);
				}
			});
		if(GlobalValues.CROSSBOW)
			RenderingRegistry.registerEntityRenderingHandler(EntityCrossbowBolt.class, new IRenderFactory<EntityCrossbowBolt>() {
				@Override
				public Render<? super EntityCrossbowBolt> createRenderFor(RenderManager manager) {
					return new RenderCrossbowBolt(manager);
				}
			});
	}
}
