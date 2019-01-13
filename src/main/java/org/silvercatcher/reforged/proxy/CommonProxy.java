package org.silvercatcher.reforged.proxy;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.registries.IForgeRegistry;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.api.ReforgedAdditions;

public class CommonProxy {

	public static final String[] sounds = new String[]{"boomerang_break", "boomerang_hit", "boomerang_throw",
			"crossbow_reload", "crossbow_shoot", "musket_shoot", "shotgun_reload", "shotgun_shoot"};
	public static final String items = "Items", ids = "IDs", floats = "General";
	// Items for Config
	public static boolean battleaxe, blowgun, boomerang, firerod, javelin, katana, knife, musket, nest_of_bees, sabre,
			keris, caltrop, dynamite, crossbow, pike, mace, dirk, cannon;
	// Floats for Config
	public static float damage_musket, damage_caltrop;
	// IDs
	public static int goalseekerid;

	public static SoundEvent getSound(String name) {
		return SoundEvent.REGISTRY.get(new ResourceLocation(ReforgedMod.ID, name));
	}

	public void init(FMLInitializationEvent event) {
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
		System.out.println(configFile);
		CommentedFileConfig config = CommentedFileConfig.builder(configFile).sync()
				.autosave()
				.writingMode(WritingMode.REPLACE)
				.build();
		config.load();

		ForgeConfigSpec spec = new ForgeConfigSpec.Builder()
				.push(items)
				.comment("Enable the Battleaxe")
				.translation("reforged.config.battleaxe")
				.define("Battleaxe", true)
				.comment("Enable the Blowgun plus Darts")
				.translation("reforged.config.blowgun")
				.define("Blowgun", true)
				.comment("Enable the Boomerang")
				.translation("reforged.config.boomerang")
				.define("Boomerang", true)
				.comment("Enable the Firerod")
				.translation("reforged.config.firerod")
				.define("Firerod", true)
				.comment("Enable the Javelin")
				.translation("reforged.config.javelin")
				.define("Javelin", true)
				.comment("Enable the Katana")
				.translation("reforged.config.katana")
				.define("Katana", true)
				.comment("Enable the Knife")
				.translation("reforged.config.knife")
				.define("Knife", true)
				.comment("Enable the Musket and Blunderbuss")
				.translation("reforged.config.musket")
				.define("Musket", true)
				.comment("Enable the Nest Of Bees")
				.translation("reforged.config.nestofbees")
				.define("Nest Of Bees", true)
				.comment("Enable the Sabre")
				.translation("reforged.config.sabre")
				.define("Sabre", true)
				.comment("Enable the Kris")
				.translation("reforged.config.kris")
				.define("Kris", true)
				.comment("Enable the Caltrop")
				.translation("reforged.config.caltrop")
				.define("Caltrop", true)
				.comment("Enable the Dynamite")
				.translation("reforged.config.dynamite")
				.define("Dynamite", true)
				.comment("Enable the Crossbow plus Bolt")
				.translation("reforged.config.crossbow")
				.define("Crossbow", true)
				.comment("Enable the Pike")
				.translation("reforged.config.pike")
				.define("Pike", true)
				.comment("Enable the Mace")
				.translation("reforged.config.mace")
				.define("Mace", true)
				.comment("Enable the Dirk")
				.translation("reforged.config.dirk")
				.define("Dirk", true)
				.comment("Enable the Cannon")
				.translation("reforged.config.cannon")
				.define("Cannon", true)
				.pop()
				.push(floats)
				.comment("Damage of the Musket")
				.translation("reforged.config.musket_damage")
				.defineInRange("Musket Damage", 10f, 1f, 50f)
				.comment("Damage of the Caltrop")
				.translation("reforged.config.caltrop_damage")
				.defineInRange("Caltrop Damage", 8f, 1f, 50f)
				.pop()
				.push(ids)
				.comment("This specifies the Enchantment ID of the Goalseeker-Enchantment")
				.translation("reforged.config.goalseeker_id")
				.defineInRange("Goalseeker", 100, 0, 256)
				.pop()
				.build();

		if (!spec.isCorrect(config)) {
			ReforgedMod.LOG.warn("Configuration file {} is not correct. Correcting", configFile);
			spec.correct(config, (action, path, incorrectValue, correctedValue) ->
					ReforgedMod.LOG.warn("Incorrect key {} was corrected from {} to {}", path, incorrectValue, correctedValue));
			config.save();
		}
		ReforgedMod.LOG.debug("Loaded Forge config from {}", configFile);

		// Items
		battleaxe = config.<Boolean>getOrElse(Arrays.asList(items, "Battleaxe"), true);
		blowgun = config.<Boolean>getOrElse(Arrays.asList(items, "Blowgun"), true);
		boomerang = config.<Boolean>getOrElse(Arrays.asList(items, "Boomerang"), true);
		firerod = config.<Boolean>getOrElse(Arrays.asList(items, "Firerod"), true);
		javelin = config.<Boolean>getOrElse(Arrays.asList(items, "Javelin"), true);
		katana = config.<Boolean>getOrElse(Arrays.asList(items, "Katana"), true);
		knife = config.<Boolean>getOrElse(Arrays.asList(items, "Knife"), true);
		musket = config.<Boolean>getOrElse(Arrays.asList(items, "Musket"), true);
		nest_of_bees = config.<Boolean>getOrElse(Arrays.asList(items, "Nest Of Bees"), true);
		sabre = config.<Boolean>getOrElse(Arrays.asList(items, "Sabre"), true);
		keris = config.<Boolean>getOrElse(Arrays.asList(items, "Kris"), true);
		caltrop = config.<Boolean>getOrElse(Arrays.asList(items, "Caltrop"), true);
		dynamite = config.<Boolean>getOrElse(Arrays.asList(items, "Dynamite"), true);
		crossbow = config.<Boolean>getOrElse(Arrays.asList(items, "Crossbow"), true);
		pike = config.<Boolean>getOrElse(Arrays.asList(items, "Pike"), true);
		mace = config.<Boolean>getOrElse(Arrays.asList(items, "Mace"), true);
		dirk = config.<Boolean>getOrElse(Arrays.asList(items, "Dirk"), true);
		cannon = config.<Boolean>getOrElse(Arrays.asList(items, "Cannon"), true);

		// Floats
		damage_musket = (float) (double) config.<Double>getOrElse(Arrays.asList(floats, "Musket Damage"), 10d);
		damage_caltrop = (float) (double) config.<Double>getOrElse(Arrays.asList(floats, "Caltrop Damage"), 8d);

		// IDs
		goalseekerid = config.getIntOrElse(Arrays.asList(ids, "Goalseeker"), 100);
	}

	public void postInit(FMLPostInitializationEvent event) {
		ReforgedMod.battlegearDetected = ModList.get().isLoaded("battlegear2");
	}

	public void preInit(FMLPreInitializationEvent event) {
		ReforgedRegistry.registerPackets();
	}

	@SubscribeEvent
	public void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
		event.getRegistry().register(
				ReforgedAdditions.goalseeker.setRegistryName(new ResourceLocation(ReforgedMod.ID, "goalseeker")));
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
