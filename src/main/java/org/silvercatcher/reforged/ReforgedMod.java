package org.silvercatcher.reforged;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.javafmlmod.FMLModLoadingContext;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.props.DefaultStunImpl;
import org.silvercatcher.reforged.props.IStunProperty;
import org.silvercatcher.reforged.props.StorageStun;
import org.silvercatcher.reforged.proxy.ClientProxy;
import org.silvercatcher.reforged.proxy.CommonProxy;
import org.silvercatcher.reforged.proxy.ServerProxy;

@Mod(ReforgedMod.ID)
public class ReforgedMod {

	public static final String NAME = "Reforged";
	public static final String ID = "reforged";
	public static final String VERSION = "0.7.5";
	public static final Logger LOG = LogManager.getLogger(NAME);
	public static final ItemGroup tabReforged = new ItemGroup(ID) {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ReforgedAdditions.IRON_BATTLE_AXE);
		}
	};
	private static final Logger LOGGER = LogManager.getLogger();
	@CapabilityInject(IStunProperty.class)
	public static Capability<IStunProperty> STUN_PROP = null;
	public static boolean battlegearDetected;
	public static SimpleChannel network;
	public static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

	public ReforgedMod() {
		CapabilityManager.INSTANCE.register(IStunProperty.class, new StorageStun(), new DefaultStunImpl());
		FMLModLoadingContext.get().getModEventBus().addListener(this::preInit);
		FMLModLoadingContext.get().getModEventBus().addListener(this::init);
		FMLModLoadingContext.get().getModEventBus().addListener(this::postInit);
	}

	public void preInit(final FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	public void init(final FMLInitializationEvent event) {
		proxy.init(event);
	}

	public void postInit(final FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

}