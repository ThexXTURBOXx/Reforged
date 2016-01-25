package org.silvercatcher.reforged;

import org.silvercatcher.reforged.proxy.CommonProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.LanguageRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ReforgedResources
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
		public static final int DISTANCE_BOOMERANG = CommonProxy.boomerang_distance;
	}
}