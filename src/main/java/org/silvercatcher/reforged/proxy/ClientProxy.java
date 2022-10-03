package org.silvercatcher.reforged.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.ReforgedReferences.GlobalValues;
import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.entities.EntityBulletBlunderbuss;
import org.silvercatcher.reforged.entities.EntityBulletMusket;
import org.silvercatcher.reforged.entities.EntityCrossbowBolt;
import org.silvercatcher.reforged.entities.EntityDart;
import org.silvercatcher.reforged.entities.EntityDummy;
import org.silvercatcher.reforged.entities.EntityDynamite;
import org.silvercatcher.reforged.entities.EntityJavelin;
import org.silvercatcher.reforged.entities.TileEntityCaltrop;
import org.silvercatcher.reforged.gui.ReloadOverlay;
import org.silvercatcher.reforged.render.RenderBoomerang;
import org.silvercatcher.reforged.render.RenderBulletBlunderbuss;
import org.silvercatcher.reforged.render.RenderBulletMusket;
import org.silvercatcher.reforged.render.RenderCrossbowBolt;
import org.silvercatcher.reforged.render.RenderDart;
import org.silvercatcher.reforged.render.RenderDummy;
import org.silvercatcher.reforged.render.RenderDynamite;
import org.silvercatcher.reforged.render.RenderJavelin;
import org.silvercatcher.reforged.render.RenderTileEntityCaltrop;

public class ClientProxy extends CommonProxy {

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        registerItemRenderers();
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
                    RenderBoomerang::new);
        }

        if (GlobalValues.CROSSBOW) {
            RenderingRegistry.registerEntityRenderingHandler(EntityCrossbowBolt.class,
                    RenderCrossbowBolt::new);
        }

        if (GlobalValues.MUSKET) {
            RenderingRegistry.registerEntityRenderingHandler(EntityBulletMusket.class,
                    RenderBulletMusket::new);
            RenderingRegistry.registerEntityRenderingHandler(EntityBulletBlunderbuss.class,
                    RenderBulletBlunderbuss::new);
        }

        if (GlobalValues.JAVELIN)
            RenderingRegistry.registerEntityRenderingHandler(EntityJavelin.class, RenderJavelin::new);
        if (GlobalValues.BLOWGUN)
            RenderingRegistry.registerEntityRenderingHandler(EntityDart.class, RenderDart::new);
        if (GlobalValues.CALTROP)
            ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCaltrop.class, new RenderTileEntityCaltrop());
        if (GlobalValues.DYNAMITE)
            RenderingRegistry.registerEntityRenderingHandler(EntityDynamite.class, RenderDynamite::new);

        /*if (GlobalValues.CANNON) {
            RenderingRegistry.registerEntityRenderingHandler(EntityCannon.class, RenderCannon::new);
            RenderingRegistry.registerEntityRenderingHandler(EntityCannonBall.class,
                    RenderCannonBall::new);
        }*/

        if (GlobalValues.DUMMY)
            RenderingRegistry.registerEntityRenderingHandler(EntityDummy.class, RenderDummy::new);
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        super.registerItemRenderer(item, meta, id);
        ModelLoader.setCustomModelResourceLocation(item, meta,
                new ModelResourceLocation(ReforgedMod.ID + ":" + id, "inventory"));
    }

    @Override
    protected void registerItemRenderers() {
        ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

        String inventory = "inventory";

        for (Item item : ReforgedRegistry.registrationList) {
            mesher.register(item, 0, new ModelResourceLocation(
                    ReforgedMod.ID + ":" + item.getUnlocalizedName().substring(5), inventory));
        }

        for (Block item : ReforgedRegistry.registrationListBlocks) {
            mesher.register(Item.getItemFromBlock(item), 0, new ModelResourceLocation(
                    ReforgedMod.ID + ":" + item.getUnlocalizedName().substring(5), inventory));
        }

    }

}
