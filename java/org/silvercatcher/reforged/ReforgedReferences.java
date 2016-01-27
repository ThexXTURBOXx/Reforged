package org.silvercatcher.reforged;

import org.silvercatcher.reforged.proxy.CommonProxy;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ReforgedReferences
{
	@SideOnly(Side.CLIENT)
	public static class Textures
	{
		public static final ResourceLocation WOODEN_BOOMERANG = new ResourceLocation(ReforgedMod.ID + ":textures/entity/wooden_boomerang.png");
		public static final ResourceLocation STONE_BOOMERANG = new ResourceLocation(ReforgedMod.ID + ":textures/entity/stone_boomerang.png");
		public static final ResourceLocation IRON_BOOMERANG = new ResourceLocation(ReforgedMod.ID + ":textures/entity/iron_boomerang.png");
		public static final ResourceLocation GOLDEN_BOOMERANG = new ResourceLocation(ReforgedMod.ID + ":textures/entity/golden_boomerang.png");
		public static final ResourceLocation DIAMOND_BOOMERANG = new ResourceLocation(ReforgedMod.ID + ":textures/entity/diamond_boomerang.png");
		public static final ResourceLocation COPPER_BOOMERANG = new ResourceLocation(ReforgedMod.ID + ":textures/entity/copper_boomerang.png");
		
		public static final ResourceLocation BULLET_MUSKET = new ResourceLocation(ReforgedMod.ID + ":textures/entity/bullet_musket.png");
		
		public static final ResourceLocation JAVELIN = new ResourceLocation(ReforgedMod.ID + ":textures/entity/javelin.png");
	}
	
	public static class GlobalValues
	{
		//Items
		public static final boolean ARROW_BUNDLE = CommonProxy.arrow_bundle;
		public static final boolean BATTLEAXE = CommonProxy.battleaxe;
		public static final boolean BLOWGUN = CommonProxy.blowgun;
		public static final boolean BOOMERANG = CommonProxy.boomerang;
		public static final boolean FIREROD = CommonProxy.firerod;
		public static final boolean HOLY_CROSS = CommonProxy.holy_cross;
		public static final boolean JAVELIN = CommonProxy.javelin;
		public static final boolean KNIFE = CommonProxy.knife;
		public static final boolean MUSKET = CommonProxy.musket;
		public static final boolean NEST_OF_BEES = CommonProxy.nest_of_bees;
		public static final boolean SABRE = CommonProxy.sabre;
		
		//Others
		public static final int DISTANCE_BOOMERANG = CommonProxy.boomerang_distance;
	}
}