package org.silvercatcher.reforged;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;

public class ReforgedReferences {
	/**
	 * Other Values needed
	 */
	public static class GlobalValues {
		// IDs
		public static ForgeConfigSpec.IntValue
				GOALSEEKERID;

		// Items
		public static ForgeConfigSpec.BooleanValue
				BATTLEAXE,
				BLOWGUN,
				BOOMERANG,
				FIREROD,
				JAVELIN,
				KATANA,
				KNIFE,
				MUSKET,
				NEST_OF_BEES,
				SABRE,
				KERIS,
				CALTROP,
				DYNAMITE,
				CROSSBOW,
				PIKE,
				MACE,
				DIRK,
				CANNON;

		//Floats
		public static ForgeConfigSpec.DoubleValue
				MUSKET_DAMAGE,
				CALTROP_DAMAGE;
	}

	/**
	 * All needed {@link ResourceLocation}s
	 */
	@OnlyIn(Dist.CLIENT)
	public static class Textures {
		public static final ResourceLocation WOODEN_BOOMERANG = new ResourceLocation(ReforgedMod.ID,
				"textures/entity/wooden_boomerang.png");
		public static final ResourceLocation STONE_BOOMERANG = new ResourceLocation(ReforgedMod.ID,
				"textures/entity/stone_boomerang.png");
		public static final ResourceLocation IRON_BOOMERANG = new ResourceLocation(ReforgedMod.ID,
				"textures/entity/iron_boomerang.png");
		public static final ResourceLocation GOLDEN_BOOMERANG = new ResourceLocation(ReforgedMod.ID,
				"textures/entity/golden_boomerang.png");
		public static final ResourceLocation DIAMOND_BOOMERANG = new ResourceLocation(ReforgedMod.ID,
				"textures/entity/diamond_boomerang.png");

		public static final ResourceLocation NORMAL_DART = new ResourceLocation(ReforgedMod.ID,
				"textures/entity/dart_normal.png");
		public static final ResourceLocation HUNGER_DART = new ResourceLocation(ReforgedMod.ID,
				"textures/entity/dart_hunger.png");
		public static final ResourceLocation POISON_2_DART = new ResourceLocation(ReforgedMod.ID,
				"textures/entity/dart_poison_strong.png");
		public static final ResourceLocation POISON_DART = new ResourceLocation(ReforgedMod.ID,
				"textures/entity/dart_poison.png");
		public static final ResourceLocation SLOW_DART = new ResourceLocation(ReforgedMod.ID,
				"textures/entity/dart_slowness.png");
		public static final ResourceLocation WITHER_DART = new ResourceLocation(ReforgedMod.ID,
				"textures/entity/dart_wither.png");

		public static final ResourceLocation BULLET_MUSKET = new ResourceLocation(ReforgedMod.ID,
				"textures/entity/bullet_musket.png");

		public static final ResourceLocation JAVELIN = new ResourceLocation(ReforgedMod.ID,
				"textures/entity/javelin.png");

		public static final ResourceLocation CALTROP = new ResourceLocation(ReforgedMod.ID,
				"textures/entity/caltrop.png");

		public static final ResourceLocation DYNAMITE = new ResourceLocation(ReforgedMod.ID,
				"textures/entity/dynamite.png");

		public static final ResourceLocation CANNON = new ResourceLocation(ReforgedMod.ID,
				"textures/entity/cannon.png");

		public static final ResourceLocation CANNON_BALL = new ResourceLocation(ReforgedMod.ID,
				"textures/entity/cannon_ball.png");
	}

}