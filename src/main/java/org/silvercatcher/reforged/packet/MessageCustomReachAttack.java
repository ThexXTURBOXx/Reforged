package org.silvercatcher.reforged.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.silvercatcher.reforged.api.ICustomReach;

/**
 * Thanks to Jabelar!!!
 */
public class MessageCustomReachAttack implements IMessage {

    public static class Handler implements IMessageHandler<MessageCustomReachAttack, IMessage> {

        @Override
        public IMessage onMessage(final MessageCustomReachAttack message, MessageContext ctx) {
            final EntityPlayerMP player = ctx.getServerHandler().player;
            player.getServer().addScheduledTask(() -> {
                Entity theEntity = player.world.getEntityByID(message.entityId);
                if (player.inventory.getCurrentItem().isEmpty() || theEntity == null) {
                    return;
                }
                if (player.inventory.getCurrentItem().getItem() instanceof ICustomReach) {
                    ICustomReach theExtendedReachWeapon = (ICustomReach) player.inventory.getCurrentItem()
                            .getItem();
                    double distanceSq = player.getDistanceSqToEntity(theEntity);
                    double reachSq = theExtendedReachWeapon.reach() * theExtendedReachWeapon.reach();
                    if (reachSq >= distanceSq) {
                        player.attackTargetEntityWithCurrentItem(theEntity);
                    }
                }
            });
            return null;
        }
    }

    private int entityId;

    public MessageCustomReachAttack() {

    }

    public MessageCustomReachAttack(int entityId) {
        this.entityId = entityId;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        entityId = ByteBufUtils.readVarInt(buf, 4);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeVarInt(buf, entityId, 4);
    }
}
