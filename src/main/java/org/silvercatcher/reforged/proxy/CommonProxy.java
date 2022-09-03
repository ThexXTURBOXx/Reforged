package org.silvercatcher.reforged.proxy;

import java.io.File;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import org.silvercatcher.reforged.ReforgedEvents;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.ReforgedMonsterArmourer;
import org.silvercatcher.reforged.ReforgedReferences.GlobalValues;
import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.api.ReforgedAdditions;
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
import org.silvercatcher.reforged.props.DefaultStunImpl;
import org.silvercatcher.reforged.props.IStunProperty;
import org.silvercatcher.reforged.props.StorageStun;

public class CommonProxy {

    public static final String[] sounds = new String[]{"boomerang_break", "boomerang_hit", "boomerang_throw",
            "crossbow_reload", "crossbow_shoot", "musket_shoot", "shotgun_reload", "shotgun_shoot"};

    // Items for Config
    public static boolean battleaxe, blowgun, boomerang, firerod, javelin, katana, knife, musket, nest_of_bees, sabre,
            keris, caltrop, dynamite, crossbow, pike, mace, dirk, cannon;

    // Floats for Config
    public static float damage_musket, damage_caltrop;

    // IDs
    public static int goalseekerid;

    public static final String items = "Items", ids = "IDs", floats = "General";

    public static SoundEvent getSound(String name) {
        return SoundEvent.REGISTRY.getObject(new ResourceLocation(ReforgedMod.ID, name));
    }

    public void init(FMLInitializationEvent event) {
    }

    private void loadConfig(FMLPreInitializationEvent e) {
        File configdir = new File(e.getModConfigurationDirectory(), ReforgedMod.NAME);
        File configfile = new File(configdir, "reforged.cfg");
        if (!configfile.exists())
            configdir.mkdirs();
        // Get an instance of Config
        Configuration config = new Configuration(configfile);

        // Load Config
        config.load();

        // Items
        battleaxe = config.getBoolean("Battleaxe", items, true, "Enable the Battleaxe");
        blowgun = config.getBoolean("Blowgun", items, true, "Enable the Blowgun plus Darts");
        boomerang = config.getBoolean("Boomerang", items, true, "Enable the Boomerang");
        firerod = config.getBoolean("Firerod", items, true, "Enable the Firerod");
        javelin = config.getBoolean("Javelin", items, true, "Enable the Javelin");
        katana = config.getBoolean("Katana", items, true, "Enable the Katana");
        knife = config.getBoolean("Knife", items, true, "Enable the Knife");
        musket = config.getBoolean("Musket", items, true, "Enable the Musket and Blunderbuss");
        nest_of_bees = config.getBoolean("Nest Of Bees", items, true, "Enable the Nest Of Bees");
        sabre = config.getBoolean("Sabre", items, true, "Enable the Sabre");
        keris = config.getBoolean("Kris", items, true, "Enable the Kris");
        caltrop = config.getBoolean("Caltrop", items, true, "Enable the Caltrop");
        dynamite = config.getBoolean("Dynamite", items, true, "Enable the Dynamite");
        crossbow = config.getBoolean("Crossbow", items, true, "Enable the Crossbow plus Bolt");
        pike = config.getBoolean("Pike", items, true, "Enable the Pike");
        mace = config.getBoolean("Mace", items, true, "Enable the Mace");
        dirk = config.getBoolean("Dirk", items, true, "Enable the Dirk");
        cannon = config.getBoolean("Cannon", items, true, "Enable the Cannon");

        // Floats
        damage_musket = config.getFloat("Musket Damage", floats, 10, 1, 50, "Damage of the Musket");
        damage_caltrop = config.getFloat("Caltrop Damage", floats, 8, 1, 50, "Damage of the Caltrop");

        // IDs
        goalseekerid = config.getInt("Goalseeker", ids, 100, 0, 256,
                "This specifies the Enchantment ID of the Goalseeker-Enchantment");

        // Save config
        config.save();
    }

    public void postInit(FMLPostInitializationEvent event) {
        ReforgedMod.battlegearDetected = Loader.isModLoaded("battlegear2");
    }

    public void preInit(FMLPreInitializationEvent event) {
        loadConfig(event);
        ReforgedRegistry.registerEventHandler(this);
        ReforgedRegistry.registerEventHandler(new ReforgedRegistry());
        ReforgedRegistry.registerEventHandler(new ReforgedEvents());
        ReforgedRegistry.registerEventHandler(new ReforgedMonsterArmourer());
        ReforgedRegistry.createItems();
        ReforgedRegistry.registerPackets();
        CapabilityManager.INSTANCE.register(IStunProperty.class, new StorageStun(), DefaultStunImpl.class);
        registerEntities();
    }

    @SubscribeEvent
    public void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
        event.getRegistry().register(
                ReforgedAdditions.goalseeker.setRegistryName(new ResourceLocation(ReforgedMod.ID, "goalseeker")));
    }

    private void registerEntities() {

        if (GlobalValues.BOOMERANG)
            ReforgedRegistry.registerEntity(EntityBoomerang.class, "Boomerang");
        if (GlobalValues.JAVELIN)
            ReforgedRegistry.registerEntity(EntityJavelin.class, "Javelin");

        if (GlobalValues.MUSKET) {
            ReforgedRegistry.registerEntity(EntityBulletMusket.class, "BulletMusket");
            ReforgedRegistry.registerEntity(EntityBulletBlunderbuss.class, "BulletBlunderbuss");
        }

        if (GlobalValues.CROSSBOW)
            ReforgedRegistry.registerEntity(EntityCrossbowBolt.class, "CrossbowBolt");
        if (GlobalValues.BLOWGUN)
            ReforgedRegistry.registerEntity(EntityDart.class, "Dart");
        if (GlobalValues.CALTROP)
            GameRegistry.registerTileEntity(TileEntityCaltrop.class, "Caltrop");
        if (GlobalValues.DYNAMITE)
            ReforgedRegistry.registerEntity(EntityDynamite.class, "Dynamite");
        if (GlobalValues.DYNAMITE) {
            ReforgedRegistry.registerEntity(EntityCannon.class, "Cannon");
            ReforgedRegistry.registerEntity(EntityCannonBall.class, "CannonBall");
        }
    }

    protected void registerEntityRenderers() {
    }

    public void registerItemRenderer(Item item, int meta, String id) {
    }

    protected void registerItemRenderers(ModelRegistryEvent event) {
    }

    @SubscribeEvent
    public void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        IForgeRegistry<SoundEvent> reg = event.getRegistry();
        for (String s : sounds) {
            ResourceLocation loc = new ResourceLocation(ReforgedMod.ID, s);
            reg.register(new SoundEvent(loc).setRegistryName(loc));
        }
    }

}
