package org.silvercatcher.reforged;

import org.silvercatcher.reforged.proxy.CommonProxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ReforgedMod.ID, version = ReforgedMod.VERSION, name = ReforgedMod.NAME)
public class ReforgedMod
{
	public static final String NAME = "Reforged";
    public static final String ID = "reforged";
    public static final String VERSION = "0.5.1;
    
    public static final CreativeTabs tabReforged = new ReforgedTab();
    
    @Instance(ID)
    public static ReforgedMod instance;
    
    @SidedProxy(modId = ID,
    		clientSide = "org.silvercatcher.reforged.proxy.ClientProxy",
    		serverSide = "org.silvercatcher.reforged.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	
    	proxy.preInit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }
}
