package org.silvercatcher.reforged.items.weapons;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.silvercatcher.reforged.api.AReloadable;
import org.silvercatcher.reforged.api.CompoundTags;
import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.entities.EntityCrossbowBolt;

public class ItemCrossbow extends AReloadable {

    public ItemCrossbow() {
        super("crossbow", "crossbow_shoot", "crossbow_reload");
        addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                int reloadTime = stack.getTagCompound().getInteger(CompoundTags.TIME);
                if (reloadTime <= 0)
                    return 0f;

                int reloadTimeLeft = getReloadTotal() - reloadTime;
                if (reloadTimeLeft > 14)
                    return 1f;
                if (reloadTimeLeft > 9)
                    return 2f;
                if (reloadTimeLeft > 4)
                    return 4f;
                if (reloadTimeLeft > 0)
                    return 5f;
                return 3f;
            }
        });
    }

    @Override
    public float getHitDamage() {
        return 2f;
    }

    @Override
    public int getReloadTotal() {
        return 20;
    }

    @Override
    public Item getAmmo() {
        return ReforgedAdditions.CROSSBOW_BOLT;
    }

    public void shoot(World worldIn, EntityLivingBase playerIn, ItemStack stack) {
        EntityCrossbowBolt a = new EntityCrossbowBolt(worldIn, playerIn);
        a.setAim(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F,
                ItemBow.getArrowVelocity(40) * 3.0F, 1.0F);
        a.pickupStatus = PickupStatus.getByOrdinal(new Random().nextInt(2));
        a.setDamage(8.0D);
        worldIn.spawnEntity(a);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !slotChanged;
    }

}
