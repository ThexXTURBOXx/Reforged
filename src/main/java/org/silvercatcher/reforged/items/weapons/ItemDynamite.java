package org.silvercatcher.reforged.items.weapons;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.silvercatcher.reforged.api.ExtendedItem;
import org.silvercatcher.reforged.entities.EntityDynamite;
import org.silvercatcher.reforged.util.Helpers;

public class ItemDynamite extends ExtendedItem {

    public ItemDynamite() {
        super();
        setUnlocalizedName("dynamite");
        setMaxStackSize(64);
    }

    @Override
    public boolean isWeapon() {
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn,
                                                    EnumHand hand) {

        if (playerIn.capabilities.isCreativeMode || Helpers.consumeInventoryItem(playerIn, this)) {
            if (!worldIn.isRemote) {
                worldIn.spawnEntityInWorld(new EntityDynamite(worldIn, playerIn));
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
    }

    @Override
    public void registerRecipes() {
        GameRegistry
                .addRecipe(new ShapedOreRecipe(new ItemStack(this, 2), "s", "g", "g", 's', "string", 'g', "gunpowder"));
    }

}
