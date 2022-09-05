package org.silvercatcher.reforged;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.props.IStunProperty;
import org.silvercatcher.reforged.proxy.CommonProxy;

@Mod(modid = ReforgedMod.ID, version = ReforgedMod.VERSION, name = ReforgedMod.NAME, updateJSON =
        ReforgedMod.UPDATE_JSON, acceptedMinecraftVersions = "1.9.4")
public class ReforgedMod {

    public static final String NAME = "Reforged";
    public static final String ID = "reforged";
    public static final String VERSION = "0.7.8";
    public static final String UPDATE_JSON = "https://raw.githubusercontent"
            + ".com/ThexXTURBOXx/UpdateJSONs/master/reforged.json";

    @CapabilityInject(IStunProperty.class)
    public static final Capability<IStunProperty> STUN_PROP = null;

    public static boolean battlegearDetected;

    public static SimpleNetworkWrapper network;

    public static final Logger LOG = LogManager.getLogger(NAME);

    public static final CreativeTabs tabReforged = new CreativeTabs(ID) {
        @Override
        public Item getTabIconItem() {
            return ReforgedAdditions.CREATIVE_TAB_ICON;
        }
    };

    @Instance(ID)
    public static ReforgedMod instance;

    @SidedProxy(modId = ID, clientSide = "org.silvercatcher.reforged.proxy.ClientProxy",
            serverSide = "org.silvercatcher.reforged.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

}
