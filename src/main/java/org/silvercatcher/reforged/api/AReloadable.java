package org.silvercatcher.reforged.api;

import com.google.common.collect.Multimap;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.util.Helpers;

public abstract class AReloadable extends Item implements ItemExtension {

    private final String shootSound, reloadSound;

    public AReloadable(String name, String shootSound, String reloadSound) {
        setMaxStackSize(1);
        setMaxDamage(100);
        setTranslationKey(name);
        setCreativeTab(ReforgedMod.tabReforged);

        this.shootSound = shootSound;
        this.reloadSound = reloadSound;

        addPropertyOverride(new ResourceLocation("loading"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return entityIn != null && entityIn.isHandActive()
                        && entityIn.getActiveItemStack() == stack && stack.hasTagCompound()
                        && stack.getTagCompound().getInteger(CompoundTags.TIME) < getReloadTotal()
                        ? 1.0F : 0.0F;
            }
        });
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag advanced) {
        int reloadTime = getReloadTime(stack);
        tooltip.add(I18n.format("item.musket.loadstate") + ": "
                + (reloadTime <= 0 ? I18n.format("item.musket.loadstate.empty")
                : (reloadTime >= getReloadTotal() ? I18n.format("item.musket.loadstate.loaded")
                : I18n.format("item.musket.loadstate.loading"))));
    }

    public abstract Item getAmmo();

    @Override
    public Multimap getAttributeModifiers(ItemStack stack) {
        return ItemExtension.super.getAttributeModifiers(stack);
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
                    new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", getHitDamage(), 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
                    new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", getAttackSpeed(null), 0));
        }

        return multimap;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        int reloadTime = getReloadTime(stack);
        if (reloadTime <= 0)
            return EnumAction.NONE;
        if (reloadTime >= getReloadTotal())
            return EnumAction.BOW;
        return ReforgedMod.battlegearDetected ? EnumAction.BOW : EnumAction.BLOCK; // still reloading
    }

    public int getReloadTime(ItemStack stack) {
        return giveCompound(stack).getInteger(CompoundTags.TIME);
    }

    public abstract int getReloadTotal();

    public NBTTagCompound giveCompound(ItemStack stack) {
        return CompoundTags.giveCompound(stack);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        ItemStack heldStack = playerIn.getHeldItem(hand);
        if (hand != EnumHand.MAIN_HAND)
            return new ActionResult<>(EnumActionResult.FAIL, heldStack);

        NBTTagCompound compound = giveCompound(heldStack);

        if (compound.getInteger(CompoundTags.TIME) <= 0) {
            if (playerIn.capabilities.isCreativeMode || Helpers.consumeInventoryItem(playerIn, getAmmo())) {
                Helpers.playSound(worldIn, playerIn, reloadSound, 1.0f, 1.5f);
                compound.setInteger(CompoundTags.TIME, 1);
            } else {
                Helpers.playSound(worldIn, playerIn, reloadSound, 1.0f, 0.7f);
                return new ActionResult<>(EnumActionResult.FAIL, heldStack);
            }
        }

        playerIn.setActiveHand(hand);
        return new ActionResult<>(EnumActionResult.PASS, heldStack);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        if (player.world.isRemote)
            return;

        NBTTagCompound compound = giveCompound(stack);
        int reloadTime = compound.getInteger(CompoundTags.TIME);

        if (reloadTime < getReloadTotal()) {
            if (reloadTime == getReloadTotal() - 1) {
                if (!player.world.isRemote) {
                    compound.setInteger(CompoundTags.TIME, getReloadTotal());
                    player.resetActiveHand();
                    Helpers.playSound(player.world, player, reloadSound, 1.0f, 1.0f);

                    // prevent players from accidentally shooting immediately after reloading
                    if (player instanceof EntityPlayer)
                        ((EntityPlayer) player).getCooldownTracker().setCooldown(this, 10);
                }
            } else {
                compound.setInteger(CompoundTags.TIME, reloadTime + 1);
            }
        }
    }

    // this method isn't called if the player switches items before releasing right click
    // see ReforgedEvents#onLivingTick for the code that handles that case
    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase playerInl, int timeLeft) {
        if (!(playerInl instanceof EntityPlayer))
            return;

        EntityPlayer playerIn = (EntityPlayer) playerInl;
        NBTTagCompound compound = giveCompound(stack);

        if (compound.getInteger(CompoundTags.TIME) >= getReloadTotal()) {
            if (!worldIn.isRemote) {
                shoot(worldIn, playerIn, stack);

                if (!playerIn.capabilities.isCreativeMode && stack.getItem().isDamageable()
                        && stack.attemptDamageItem(5, itemRand,
                        playerIn instanceof EntityPlayerMP ? (EntityPlayerMP) playerIn : null)) {
                    playerIn.renderBrokenItemStack(stack);
                    Helpers.destroyCurrentEquippedItem(playerIn);
                }
            }

            compound.setInteger(CompoundTags.TIME, 0);
            Helpers.playSound(worldIn, playerIn, shootSound, 1f, 1f);
        }
    }

    public abstract void shoot(World worldIn, EntityLivingBase playerIn, ItemStack stack);

    @Override
    public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
        if (super.canContinueUsing(oldStack, newStack))
            return true;

        if (oldStack.getItem() != newStack.getItem() || !oldStack.hasTagCompound() || !newStack.hasTagCompound()) {
            return false;
        }

        int oldReloadTime = oldStack.getTagCompound().getInteger(CompoundTags.TIME);
        int newReloadTime = newStack.getTagCompound().getInteger(CompoundTags.TIME);
        if (oldReloadTime == 0 || newReloadTime == 0) // if either stack is unloaded, abort
            return false;
        if (oldReloadTime < getReloadTotal()) // if both stacks are reloading, we can continue
            return newReloadTime < getReloadTotal();
        return newReloadTime >= getReloadTotal(); // otherwise, if both stacks are loaded, we can continue
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged;
    }

}
