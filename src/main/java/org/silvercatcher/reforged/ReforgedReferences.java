package org.silvercatcher.reforged;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.silvercatcher.reforged.proxy.CommonProxy;

public class ReforgedReferences {
    /**
     * Other Values needed
     */
    public static class GlobalValues {
        // Logger
        public static final Logger LOG = LogManager.getLogger(ReforgedMod.NAME);

        // Items
        public static final boolean BATTLEAXE = CommonProxy.battleaxe;
        public static final boolean BLOWGUN = CommonProxy.blowgun;
        public static final boolean BOOMERANG = CommonProxy.boomerang;
        public static final boolean FIREROD = CommonProxy.firerod;
        public static final boolean JAVELIN = CommonProxy.javelin;
        public static final boolean KATANA = CommonProxy.katana;
        public static final boolean KNIFE = CommonProxy.knife;
        public static final boolean MUSKET = CommonProxy.musket;
        public static final boolean NEST_OF_BEES = CommonProxy.nestOfBees;
        public static final boolean SABRE = CommonProxy.sabre;
        public static final boolean KERIS = CommonProxy.keris;
        public static final boolean CALTROP = CommonProxy.caltrop;
        public static final boolean DYNAMITE = CommonProxy.dynamite;
        public static final boolean CROSSBOW = CommonProxy.crossbow;
        public static final boolean PIKE = CommonProxy.pike;
        public static final boolean MACE = CommonProxy.mace;
        public static final boolean DIRK = CommonProxy.dirk;
        //public static final boolean CANNON = CommonProxy.cannon;
        public static final boolean DUMMY = CommonProxy.dummy;
    }

    /**
     * All needed {@link ResourceLocation}s
     */
    @SideOnly(Side.CLIENT)
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

        public static final ResourceLocation DUMMY = new ResourceLocation(ReforgedMod.ID,
                "textures/entity/dummy.png");
    }

}
