package org.silvercatcher.reforged;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.silvercatcher.reforged.api.IZombieEquippable;
import org.silvercatcher.reforged.proxy.CommonProxy;
import org.silvercatcher.reforged.util.Helpers;

public class ReforgedMonsterArmourer {

    private static final UUID itemModifierUUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    /**
     * DON'T CHANGE IT HERE! NULLPOINTERS WOULD OCCUR!!!
     */
    private static Item[] zombieWeapons;

    private void equipZombie(EntityZombie zombie) {
        Random random = zombie.worldObj.rand;
        int chance = CommonProxy.zombieSpawn;
        if (chance != 0 && zombie.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND) == null
                && random.nextInt(chance) == 0) {
            Item item = Helpers.randomFrom(random, zombieWeapons);
            zombie.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(item));

            zombie.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE)
                    .applyModifier(new AttributeModifier(itemModifierUUID, "Weapon Damage", 99f, 0));

        }
    }

    @SubscribeEvent
    public void onSpawn(EntityJoinWorldEvent event) {
        if (event.isCanceled() || event.getEntity() == null || event.getWorld().isRemote
                || !(event.getEntity() instanceof EntityZombie) || zombieWeapons == null)
            return;
        equipZombie((EntityZombie) event.getEntity());
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load e) {
        if (e.isCanceled() || e.getWorld().isRemote)
            return;
        List<Item> list = new ArrayList<>();
        for (Item i : ReforgedRegistry.registrationList) {
            if (i instanceof IZombieEquippable) {
                for (int c = 0; c < ((IZombieEquippable) i).zombieSpawnChance(); c++)
                    list.add(i);
            }
        }
        if (list.isEmpty())
            zombieWeapons = null;
        else
            zombieWeapons = list.toArray(new Item[0]);
    }

}
