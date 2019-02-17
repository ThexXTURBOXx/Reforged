package org.silvercatcher.reforged;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.gui.ReloadOverlay;
import org.silvercatcher.reforged.props.IStunProperty;
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
			return new ItemStack(ReforgedAdditions.TAB_ICON);
		}
	};
	private static final Logger LOGGER = LogManager.getLogger();
	@CapabilityInject(IStunProperty.class)
	public static Capability<IStunProperty> STUN_PROP = null;
	public static boolean battlegearDetected;
	public static SimpleChannel network;
	public static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

	public ReforgedMod() {
		//CapabilityManager.INSTANCE.register(IStunProperty.class, new StorageStun(), new DefaultStunImpl());
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupServer);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		ReforgedRegistry reg = new ReforgedRegistry();
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, reg::registerItems);
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, reg::registerBlocks);
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(EntityType.class, reg::registerEntities);
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(TileEntityType.class, reg::registerTileEntities);
		proxy.loadConfig();
		ReforgedRegistry.registerEventHandler(this);
		ReforgedRegistry.registerEventHandler(proxy);
		ReforgedRegistry.registerEventHandler(reg);
		ReforgedRegistry.registerEventHandler(new ReforgedEvents());
		ReforgedRegistry.registerEventHandler(new ReforgedMonsterArmourer());
		if (FMLEnvironment.dist == Dist.CLIENT) {
			ReforgedRegistry.registerEventHandler(new ReloadOverlay());
		}
	}

	private void setup(final FMLCommonSetupEvent event) {
		proxy.setup(event);
	}

	private void setupClient(final FMLClientSetupEvent event) {
		proxy.setupClient(event);
	}

	private void setupServer(final FMLDedicatedServerSetupEvent event) {
		proxy.setupServer(event);
	}

	private void enqueueIMC(final InterModEnqueueEvent event) {
		proxy.enqueueIMC(event);
	}

	private void processIMC(final InterModProcessEvent event) {
		proxy.processIMC(event);
	}

}