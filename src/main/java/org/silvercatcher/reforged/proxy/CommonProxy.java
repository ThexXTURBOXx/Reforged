package org.silvercatcher.reforged.proxy;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.registries.IForgeRegistry;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.ReforgedReferences;
import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.api.ReforgedAdditions;

public class CommonProxy {

	public static final String[] sounds = new String[]{"boomerang_break", "boomerang_hit", "boomerang_throw",
			"crossbow_reload", "crossbow_shoot", "musket_shoot", "shotgun_reload", "shotgun_shoot"};
	public static final String items = "Items", ids = "IDs", floats = "General";

	public static SoundEvent getSound(String name) {
		return SoundEvent.REGISTRY.get(new ResourceLocation(ReforgedMod.ID, name));
	}

	public void loadConfig() {
		Path configFile = Paths.get("config").resolve(ReforgedMod.ID).resolve("reforged.toml");
		File cfgFile = configFile.toFile();
		try {
			if (!cfgFile.getParentFile().exists())
				cfgFile.getParentFile().mkdirs();
			if (!cfgFile.exists())
				cfgFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

		builder.comment("You can dis-/enable items here")
				.push(items);

		ReforgedReferences.GlobalValues.BATTLEAXE = builder
				.comment("Enable the Battleaxe")
				.translation("reforged.config.battleaxe")
				.define("Battleaxe", true);
		ReforgedReferences.GlobalValues.BLOWGUN = builder
				.comment("Enable the Blowgun plus Darts")
				.translation("reforged.config.blowgun")
				.define("Blowgun", true);
		ReforgedReferences.GlobalValues.BOOMERANG = builder
				.comment("Enable the Boomerang")
				.translation("reforged.config.boomerang")
				.define("Boomerang", true);
		ReforgedReferences.GlobalValues.FIREROD = builder
				.comment("Enable the Firerod")
				.translation("reforged.config.firerod")
				.define("Firerod", true);
		ReforgedReferences.GlobalValues.JAVELIN = builder
				.comment("Enable the Javelin")
				.translation("reforged.config.javelin")
				.define("Javelin", true);
		ReforgedReferences.GlobalValues.KATANA = builder
				.comment("Enable the Katana")
				.translation("reforged.config.katana")
				.define("Katana", true);
		ReforgedReferences.GlobalValues.KNIFE = builder
				.comment("Enable the Knife")
				.translation("reforged.config.knife")
				.define("Knife", true);
		ReforgedReferences.GlobalValues.MUSKET = builder
				.comment("Enable the Musket and Blunderbuss")
				.translation("reforged.config.musket")
				.define("Musket", true);
		ReforgedReferences.GlobalValues.NEST_OF_BEES = builder
				.comment("Enable the Nest Of Bees")
				.translation("reforged.config.nestofbees")
				.define("Nest Of Bees", true);
		ReforgedReferences.GlobalValues.SABRE = builder
				.comment("Enable the Sabre")
				.translation("reforged.config.sabre")
				.define("Sabre", true);
		ReforgedReferences.GlobalValues.KERIS = builder
				.comment("Enable the Kris")
				.translation("reforged.config.kris")
				.define("Kris", true);
		ReforgedReferences.GlobalValues.CALTROP = builder
				.comment("Enable the Caltrop")
				.translation("reforged.config.caltrop")
				.define("Caltrop", true);
		ReforgedReferences.GlobalValues.DYNAMITE = builder
				.comment("Enable the Dynamite")
				.translation("reforged.config.dynamite")
				.define("Dynamite", true);
		ReforgedReferences.GlobalValues.CROSSBOW = builder
				.comment("Enable the Crossbow plus Bolt")
				.translation("reforged.config.crossbow")
				.define("Crossbow", true);
		ReforgedReferences.GlobalValues.PIKE = builder
				.comment("Enable the Pike")
				.translation("reforged.config.pike")
				.define("Pike", true);
		ReforgedReferences.GlobalValues.MACE = builder
				.comment("Enable the Mace")
				.translation("reforged.config.mace")
				.define("Mace", true);
		ReforgedReferences.GlobalValues.DIRK = builder
				.comment("Enable the Dirk")
				.translation("reforged.config.dirk")
				.define("Dirk", true);
		ReforgedReferences.GlobalValues.CANNON = builder
				.comment("Enable the Cannon")
				.translation("reforged.config.cannon")
				.define("Cannon", true);

		builder.pop()
				.comment("You can change e.g. damages here")
				.push(floats);

		ReforgedReferences.GlobalValues.MUSKET_DAMAGE = builder.comment("Damage of the Musket")
				.translation("reforged.config.musket_damage")
				.defineInRange("Musket Damage", 10f, 1f, 50f);
		ReforgedReferences.GlobalValues.CALTROP_DAMAGE = builder.comment("Damage of the Caltrop")
				.translation("reforged.config.caltrop_damage")
				.defineInRange("Caltrop Damage", 8f, 1f, 50f);

		builder.pop()
				.comment("You can change IDs here")
				.push(ids);

		ReforgedReferences.GlobalValues.GOALSEEKERID = builder
				.comment("This specifies the Enchantment ID of the Goalseeker-Enchantment")
				.translation("reforged.config.goalseeker_id")
				.defineInRange("Goalseeker", 100, 0, 256);

		ForgeConfigSpec spec = builder.pop().build();

		spec.setConfig(CommentedFileConfig.of(cfgFile));
		ReforgedMod.LOG.debug("Loaded {} config from {}", ReforgedMod.NAME, configFile);
	}

	public void setup(FMLCommonSetupEvent event) {
		ReforgedRegistry.registerPackets();
		ReforgedMod.battlegearDetected = ModList.get().isLoaded("battlegear2");
	}

	public void setupClient(FMLClientSetupEvent event) {
	}

	public void setupServer(FMLDedicatedServerSetupEvent event) {
	}

	public void enqueueIMC(InterModEnqueueEvent event) {
	}

	public void processIMC(InterModProcessEvent event) {
	}

	@SubscribeEvent
	public void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
		event.getRegistry().register(
				ReforgedAdditions.goalseeker.setRegistryName(new ResourceLocation(ReforgedMod.ID, "goalseeker")));
	}

	public void registerEntityRenderers() {
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
