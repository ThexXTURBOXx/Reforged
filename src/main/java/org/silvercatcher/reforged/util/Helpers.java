package org.silvercatcher.reforged.util;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.silvercatcher.reforged.proxy.CommonProxy;

public class Helpers {

    /**
     * Checks if the 2 given BlockPositions are equal
     */
    public static boolean blockPosEqual(BlockPos pos1, BlockPos pos2) {
        int x1 = pos1.getX();
        int x2 = pos2.getX();
        int y1 = pos1.getY();
        int y2 = pos2.getY();
        int z1 = pos1.getZ();
        int z2 = pos2.getZ();
        return (x1 == x2 && y1 == y2 && z1 == z2);
    }

    public static boolean consumeInventoryItem(EntityPlayer player, Item itemIn) {
        if (player.getHeldItemMainhand().isItemEqualIgnoreDurability(new ItemStack(itemIn))) {
            int i = player.inventory.currentItem;
            shrinkInventoryItem(player.inventory.mainInventory, i);
            return true;
        }

        boolean consumed = consumeInventoryItem(player.inventory.offHandInventory, itemIn);
        if (!consumed) {
            consumed = consumeInventoryItem(player.inventory.mainInventory, itemIn);
        }

        return consumed;
    }

    public static boolean consumeInventoryItem(NonNullList<ItemStack> inv, Item itemIn) {
        int i = getInventorySlotContainItem(inv, itemIn);
        if (i < 0) {
            return false;
        }
        shrinkInventoryItem(inv, i);
        return true;
    }

    public static void shrinkInventoryItem(NonNullList<ItemStack> inv, int i) {
        ItemStack item = inv.get(i);
        item.shrink(1);
        if (item.getCount() <= 0) {
            inv.set(i, ItemStack.EMPTY);
        }
    }

    public static void destroyCurrentEquippedItem(EntityPlayer player) {
        ItemStack orig = player.inventory.getCurrentItem();
        player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
        ForgeEventFactory.onPlayerDestroyItem(player, orig, EnumHand.MAIN_HAND);
    }

    /**
     * Helper method for creating a Rectangle
     */
    public static void drawRectangle(int left, int top, int right, int bottom, float[] color) {

        if (left < right) {
            int j1 = left;
            left = right;
            right = j1;
        }

        if (top < bottom) {
            int j1 = top;
            top = bottom;
            bottom = j1;
        }

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder wr = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(color[0], color[1], color[2], color[3]);

        wr.begin(7, DefaultVertexFormats.POSITION);
        wr.pos(left, bottom, 0.0D).endVertex();
        wr.pos(right, bottom, 0.0D).endVertex();
        wr.pos(right, top, 0.0D).endVertex();
        wr.pos(left, top, 0.0D).endVertex();

        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static boolean hasItem(EntityPlayer p, Item itemIn) {
        int i = getInventorySlotContainItem(p.inventory.offHandInventory, itemIn);
        if (i < 0) {
            i = getInventorySlotContainItem(p.inventory.mainInventory, itemIn);
        }
        return i >= 0;
    }

    public static int getInventorySlotContainItem(NonNullList<ItemStack> inv, Item itemIn) {
        ItemStack stack = new ItemStack(itemIn);
        for (int i = 0; i < inv.size(); ++i) {
            if (inv.get(i).isItemEqualIgnoreDurability(stack)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Thanks to Jabelar!!!
     */
    public static RayTraceResult getMouseOverExtended(float distance) {
        Minecraft mc = FMLClientHandler.instance().getClient();
        Entity e = mc.getRenderViewEntity();
        AxisAlignedBB theViewBoundingBox = new AxisAlignedBB(
                e.posX - 0.5D, e.posY - 0.0D, e.posZ - 0.5D,
                e.posX + 0.5D, e.posY + 1.5D, e.posZ + 0.5D);
        RayTraceResult returnMOP = null;
        if (mc.world != null) {
            double var2 = distance;
            returnMOP = e.rayTrace(var2, 0);
            double calcdist = var2;
            Vec3d pos = e.getPositionEyes(0);
            var2 = calcdist;
            if (returnMOP != null) {
                calcdist = returnMOP.hitVec.distanceTo(pos);
            }
            Vec3d lookVec = e.getLook(0);
            Vec3d var8 = pos.add(lookVec.x * var2, lookVec.y * var2, lookVec.z * var2);
            Entity pointedEntity = null;
            float var9 = 1.0F;
            List<Entity> list = mc.world.getEntitiesWithinAABBExcludingEntity(e, theViewBoundingBox
                    .expand(lookVec.x * var2, lookVec.y * var2, lookVec.z * var2).expand(var9, var9, var9));
            double d = calcdist;
            for (Entity entity : list) {
                if (entity.canBeCollidedWith()) {
                    float borderSize = entity.getCollisionBorderSize();
                    AxisAlignedBB aabb = new AxisAlignedBB(entity.posX - entity.width / 2, entity.posY,
                            entity.posZ - entity.width / 2, entity.posX + entity.width / 2, entity.posY + entity.height,
                            entity.posZ + entity.width / 2);
                    aabb.expand(borderSize, borderSize, borderSize);
                    RayTraceResult mop0 = aabb.calculateIntercept(pos, var8);
                    if (aabb.contains(pos)) {
                        if (0.0D < d || d == 0.0D) {
                            pointedEntity = entity;
                            d = 0.0D;
                        }
                    } else if (mop0 != null) {
                        double d1 = pos.distanceTo(mop0.hitVec);
                        if (d1 < d || d == 0.0D) {
                            pointedEntity = entity;
                            d = d1;
                        }
                    }
                }
            }
            if (pointedEntity != null && (d < calcdist || returnMOP == null)) {
                returnMOP = new RayTraceResult(pointedEntity);
            }
        }
        return returnMOP;
    }

    public static void playSound(World w, Entity e, String name, double volume, double pitch) {
        playSound(w, e, name, (float) volume, (float) pitch);
    }

    public static void playSound(World w, Entity e, String name, float volume, float pitch) {
        w.playSound(null, e.posX, e.posY, e.posZ, CommonProxy.getSound(name), SoundCategory.MASTER, volume, pitch);
    }

}
