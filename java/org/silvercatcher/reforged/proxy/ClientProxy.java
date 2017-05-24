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
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.*;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent e) {

		super.preInit(e);
		MinecraftForge.EVENT_BUS.register(new ReloadOverlay());
	}

	@Override
	public void init(FMLInitializationEvent e) {

		super.init(e);
		registerItemRenderers();
		registerEntityRenderers(Minecraft.getMinecraft().getRenderManager());
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
	}

	@Override
	protected void registerItemRenderers() {

		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

		String inventory = "inventory";

		if (GlobalValues.CROSSBOW) {
			ModelBakery.addVariantName(ReforgedAdditions.CROSSBOW,
					new String[] { ReforgedMod.ID + ":crossbow", ReforgedMod.ID + ":crossbow_1",
							ReforgedMod.ID + ":crossbow_2", ReforgedMod.ID + ":crossbow_3",
							ReforgedMod.ID + ":crossbow_4", ReforgedMod.ID + ":crossbow_5" });
		}

		for (Item item : ReforgedRegistry.registrationList) {
			mesher.register(item, 0, new ModelResourceLocation(
					ReforgedMod.ID + ":" + item.getUnlocalizedName().substring(5), inventory));
		}

		for (Block item : ReforgedRegistry.registrationListBlocks) {
			mesher.register(Item.getItemFromBlock(item), 0, new ModelResourceLocation(
					ReforgedMod.ID + ":" + item.getUnlocalizedName().substring(5), inventory));
		}

		if (GlobalValues.NEST_OF_BEES) {
			mesher.register(ReforgedAdditions.NEST_OF_BEES, 1, new ModelResourceLocation(
					ReforgedMod.ID + ":" + ReforgedAdditions.NEST_OF_BEES.getUnlocalizedName().substring(5) + "_empty",
					inventory));

			mesher.register(ReforgedAdditions.NEST_OF_BEES, 2, new ModelResourceLocation(
					ReforgedMod.ID + ":" + ReforgedAdditions.NEST_OF_BEES.getUnlocalizedName().substring(5) + "_powder",
					inventory));
		}

		if (GlobalValues.CROSSBOW) {
			for (int i = 1; i <= 5; i++) {
				mesher.register(ReforgedAdditions.CROSSBOW, i,
						new ModelResourceLocation(ReforgedMod.ID + ":" + "crossbow_" + i));
			}
		}
	}

	@Override
	protected void registerEntityRenderers(RenderManager manager) {

		if (GlobalValues.BOOMERANG)
			ReforgedRegistry.registerEntityRenderer(EntityBoomerang.class, new RenderBoomerang(manager));

		if (GlobalValues.MUSKET) {
			ReforgedRegistry.registerEntityRenderer(EntityBulletMusket.class, new RenderBulletMusket(manager));
			ReforgedRegistry.registerEntityRenderer(EntityBulletBlunderbuss.class,
					new RenderBulletBlunderbuss(manager));
		}

		if (GlobalValues.JAVELIN)
			ReforgedRegistry.registerEntityRenderer(EntityJavelin.class, new RenderJavelin(manager));
		if (GlobalValues.BLOWGUN)
			ReforgedRegistry.registerEntityRenderer(EntityDart.class, new RenderDart(manager));
		if (GlobalValues.CALTROP)
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCaltropEntity.class, new RenderTileEntityCaltrop());
		if (GlobalValues.DYNAMITE)
			ReforgedRegistry.registerEntityRenderer(EntityDynamite.class, new RenderDynamite(manager));
		if (GlobalValues.CROSSBOW)
			ReforgedRegistry.registerEntityRenderer(EntityCrossbowBolt.class, new RenderBoltCrossbow(manager));
	}
}
