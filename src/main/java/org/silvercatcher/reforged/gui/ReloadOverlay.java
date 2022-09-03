package org.silvercatcher.reforged.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.silvercatcher.reforged.api.AReloadable;
import org.silvercatcher.reforged.util.Helpers;

public class ReloadOverlay extends Gui {

    private final Minecraft minecraft;

    public ReloadOverlay() {
        super();
        minecraft = Minecraft.getMinecraft();
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void renderReload(RenderGameOverlayEvent.Pre event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR) {
            return;
        }

        EntityPlayer player = minecraft.player;
        if (player != null && player.world != null) {
            ItemStack equipped = player.inventory.getCurrentItem();
            Item equippedItem = equipped.getItem();

            if (equippedItem instanceof AReloadable) {
                AReloadable reloadable = (AReloadable) equippedItem;

                int reloadTime = reloadable.getReloadTime(equipped);
                if (reloadTime <= 0 || reloadTime >= reloadable.getReloadTotal()) {
                    return;
                }

                float done = Math.min(reloadTime / (float) reloadable.getReloadTotal(), 1.0f);
                int x0 = event.getResolution().getScaledWidth() / 2 - 88 + player.inventory.currentItem * 20;
                int y0 = event.getResolution().getScaledHeight() - 3;
                int color = MathHelper.hsvToRGB(done / 3.0f, 1.0f, 1.0f);

                Helpers.drawRectangle(x0, y0 - (int) (done * 16), x0 + 16, y0, new float[]{
                        (color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f, 1.0f});

                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.enableBlend();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void modifyFov(FOVUpdateEvent event) {
        EntityPlayer player = event.getEntity();
        if (!player.isHandActive())
            return;

        ItemStack activeStack = player.getActiveItemStack();
        Item activeItem = activeStack.getItem();
        if (!(activeItem instanceof AReloadable))
            return;

        AReloadable reloadable = (AReloadable) activeItem;
        if (reloadable.getReloadTime(activeStack) < reloadable.getReloadTotal())
            return;

        int useTicks = player.getItemInUseMaxCount();
        if (useTicks >= 20) {
            event.setNewfov(event.getNewfov() * 0.8f);
        } else {
            float mod = useTicks / 20.0f;
            event.setNewfov(event.getNewfov() * (1.0f - mod * mod * 0.2f));
        }
    }
}
