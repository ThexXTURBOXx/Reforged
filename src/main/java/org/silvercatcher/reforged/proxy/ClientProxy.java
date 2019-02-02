package org.silvercatcher.reforged.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.silvercatcher.reforged.ReforgedReferences.GlobalValues;
import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.entities.EntityBulletBlunderbuss;
import org.silvercatcher.reforged.entities.EntityBulletMusket;
import org.silvercatcher.reforged.entities.EntityCannon;
import org.silvercatcher.reforged.entities.EntityCannonBall;
import org.silvercatcher.reforged.entities.EntityCrossbowBolt;
import org.silvercatcher.reforged.entities.EntityDart;
import org.silvercatcher.reforged.entities.EntityDynamite;
import org.silvercatcher.reforged.entities.EntityJavelin;
import org.silvercatcher.reforged.entities.TileEntityCaltrop;
import org.silvercatcher.reforged.render.RenderBoomerang;
import org.silvercatcher.reforged.render.RenderBulletBlunderbuss;
import org.silvercatcher.reforged.render.RenderBulletMusket;
import org.silvercatcher.reforged.render.RenderCannon;
import org.silvercatcher.reforged.render.RenderCannonBall;
import org.silvercatcher.reforged.render.RenderCrossbowBolt;
import org.silvercatcher.reforged.render.RenderDart;
import org.silvercatcher.reforged.render.RenderDynamite;
import org.silvercatcher.reforged.render.RenderJavelin;
import org.silvercatcher.reforged.render.RenderTileEntityCaltrop;

public class ClientProxy extends CommonProxy {

	@Override
	public void setupClient(FMLClientSetupEvent event) {
		super.setupClient(event);
		System.out.println("CLIENT SETUP");
		registerEntityRenderers();
	}

	@Override
	public void registerEntityRenderers() {
		if (GlobalValues.BOOMERANG.get()) {
			RenderingRegistry.registerEntityRenderingHandler(EntityBoomerang.class, RenderBoomerang::new);
		}

		if (GlobalValues.CROSSBOW.get()) {
			RenderingRegistry.registerEntityRenderingHandler(EntityCrossbowBolt.class, RenderCrossbowBolt::new);
		}

		if (GlobalValues.MUSKET.get()) {
			RenderingRegistry.registerEntityRenderingHandler(EntityBulletMusket.class, RenderBulletMusket::new);
			RenderingRegistry.registerEntityRenderingHandler(EntityBulletBlunderbuss.class, RenderBulletBlunderbuss::new);
		}

		if (GlobalValues.JAVELIN.get())
			RenderingRegistry.registerEntityRenderingHandler(EntityJavelin.class, RenderJavelin::new);
		if (GlobalValues.BLOWGUN.get())
			RenderingRegistry.registerEntityRenderingHandler(EntityDart.class, RenderDart::new);
		if (GlobalValues.CALTROP.get())
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCaltrop.class, new RenderTileEntityCaltrop());
		if (GlobalValues.DYNAMITE.get())
			RenderingRegistry.registerEntityRenderingHandler(EntityDynamite.class, RenderDynamite::new);

		if (GlobalValues.CANNON.get()) {
			RenderingRegistry.registerEntityRenderingHandler(EntityCannon.class, RenderCannon::new);
			RenderingRegistry.registerEntityRenderingHandler(EntityCannonBall.class, RenderCannonBall::new);
		}
	}

	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		super.registerItemRenderer(item, meta, id);
		/*ModelLoader.setCustomModelResourceLocation(item, meta,
				new ModelResourceLocation(ReforgedMod.ID + ":" + id, "inventory"));*/
	}

	@Override
	@SubscribeEvent
	protected void registerItemRenderers(ModelRegistryEvent event) {

		String inventory = "inventory";

		/*for (Item item : ReforgedRegistry.registrationList) {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(
					ReforgedMod.ID + ":" + item.getUnlocalizedName().substring(5), inventory));
		}

		for (Block item : ReforgedRegistry.registrationListBlocks) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(item), 0, new ModelResourceLocation(
					ReforgedMod.ID + ":" + item.getUnlocalizedName().substring(5), inventory));
		}*/

	}

}