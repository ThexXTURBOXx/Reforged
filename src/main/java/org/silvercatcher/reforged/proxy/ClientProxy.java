package org.silvercatcher.reforged.proxy;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.ReforgedReferences.GlobalValues;
import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.entities.*;
import org.silvercatcher.reforged.gui.ReloadOverlay;
import org.silvercatcher.reforged.render.*;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {

		super.preInit(event);
		MinecraftForge.EVENT_BUS.register(new ReloadOverlay());
		registerEntityRenderers();
	}

	@Override
	protected void registerEntityRenderers() {

		if (GlobalValues.BOOMERANG) {
			RenderingRegistry.registerEntityRenderingHandler(EntityBoomerang.class,
					new IRenderFactory<EntityBoomerang>() {
						@Override
						public Render<? super EntityBoomerang> createRenderFor(RenderManager manager) {
							return new RenderBoomerang(manager);
						}
					});
		}

		if (GlobalValues.CROSSBOW) {
			RenderingRegistry.registerEntityRenderingHandler(EntityCrossbowBolt.class,
					new IRenderFactory<EntityCrossbowBolt>() {
						@Override
						public Render<? super EntityCrossbowBolt> createRenderFor(RenderManager manager) {
							return new RenderCrossbowBolt(manager);
						}
					});
		}

		if (GlobalValues.MUSKET) {
			RenderingRegistry.registerEntityRenderingHandler(EntityBulletMusket.class,
					new IRenderFactory<EntityBulletMusket>() {
						@Override
						public Render<? super EntityBulletMusket> createRenderFor(RenderManager manager) {
							return new RenderBulletMusket(manager);
						}
					});
			RenderingRegistry.registerEntityRenderingHandler(EntityBulletBlunderbuss.class,
					new IRenderFactory<EntityBulletBlunderbuss>() {
						@Override
						public Render<? super EntityBulletBlunderbuss> createRenderFor(RenderManager manager) {
							return new RenderBulletBlunderbuss(manager);
						}
					});
		}

		if (GlobalValues.JAVELIN)
			RenderingRegistry.registerEntityRenderingHandler(EntityJavelin.class, new IRenderFactory<EntityJavelin>() {
				@Override
				public Render<? super EntityJavelin> createRenderFor(RenderManager manager) {
					return new RenderJavelin(manager);
				}
			});
		if (GlobalValues.BLOWGUN)
			RenderingRegistry.registerEntityRenderingHandler(EntityDart.class, new IRenderFactory<EntityDart>() {
				@Override
				public Render<? super EntityDart> createRenderFor(RenderManager manager) {
					return new RenderDart(manager);
				}
			});
		if (GlobalValues.CALTROP)
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCaltrop.class, new RenderTileEntityCaltrop());
		if (GlobalValues.DYNAMITE)
			RenderingRegistry.registerEntityRenderingHandler(EntityDynamite.class,
					new IRenderFactory<EntityDynamite>() {
						@Override
						public Render<? super EntityDynamite> createRenderFor(RenderManager manager) {
							return new RenderDynamite(manager);
						}
					});
	}

	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		super.registerItemRenderer(item, meta, id);
		ModelLoader.setCustomModelResourceLocation(item, meta,
				new ModelResourceLocation(ReforgedMod.ID + ":" + id, "inventory"));
	}

	@Override
	@SubscribeEvent
	protected void registerItemRenderers(ModelRegistryEvent event) {

		String inventory = "inventory";

		for (Item item : ReforgedRegistry.registrationList) {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(
					ReforgedMod.ID + ":" + item.getUnlocalizedName().substring(5), inventory));
		}

		for (Block item : ReforgedRegistry.registrationListBlocks) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(item), 0, new ModelResourceLocation(
					ReforgedMod.ID + ":" + item.getUnlocalizedName().substring(5), inventory));
		}

	}

}