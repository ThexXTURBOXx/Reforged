package org.silvercatcher.reforged.items.weapons;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.silvercatcher.reforged.api.ExtendedItem;
import org.silvercatcher.reforged.api.ItemExtension;
import org.silvercatcher.reforged.entities.EntityJavelin;
import org.silvercatcher.reforged.util.Helpers;

public class ItemJavelin extends ExtendedItem {

    public ItemJavelin() {
        super();
        setUnlocalizedName("javelin");
        setMaxStackSize(8);
        setMaxDamage(32);
    }

    @Override
    public float getHitDamage() {
        return 3f;
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        return ToolMaterial.STONE.getEnchantability();
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return ItemExtension.USE_DURATON;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn,
                                                    EnumHand hand) {
        if (hand == EnumHand.MAIN_HAND) {
            ArrowNockEvent event = new ArrowNockEvent(
                    playerIn, playerIn.getHeldItemMainhand(), EnumHand.MAIN_HAND, worldIn, true);
            if (MinecraftForge.EVENT_BUS.post(event))
                return event.getAction();

            playerIn.setActiveHand(EnumHand.MAIN_HAND);

            return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItemMainhand());
        }
        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItemOffhand());
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase playerIn) {
        return stack;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase playerInl, int timeLeft) {
        if (playerInl instanceof EntityPlayer) {

            EntityPlayer playerIn = (EntityPlayer) playerInl;

            ItemStack throwStack = stack.copy();

            if (timeLeft <= getMaxItemUseDuration(stack) - 7
                    && (playerIn.capabilities.isCreativeMode || Helpers.consumeInventoryItem(playerIn, this))) {

                worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_ARROW_SHOOT,
                        SoundCategory.MASTER, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
                if (!worldIn.isRemote) {
                    if (throwStack.stackSize > 1) {
                        throwStack = throwStack.splitStack(1);
                    }
                    worldIn.spawnEntityInWorld(
                            new EntityJavelin(worldIn, playerIn, throwStack, stack.getMaxItemUseDuration() - timeLeft));
                }
            }
        }
    }

    @Override
    public void registerRecipes() {

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this), "  f", " s ", "s  ", 'f',
                new ItemStack(Items.FLINT), 's', "stickWood"));
    }
}
