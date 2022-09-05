package org.silvercatcher.reforged.items.weapons;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.silvercatcher.reforged.api.ExtendedItem;

public class ItemFireRod extends ExtendedItem {

    public static final int FIRE_DURATION = 10;

    public ItemFireRod() {
        super();
        setUnlocalizedName("firerod");
        setMaxStackSize(32);
    }

    @Override
    public float getHitDamage() {
        return 1.5f;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (!target.isImmuneToFire()) {
            if (target instanceof EntityCreeper) {
                ((EntityCreeper) target).ignite();
            } else {
                target.setFire(FIRE_DURATION);
            }
        }
        if (attacker instanceof EntityPlayer) {
            if (!((EntityPlayer) attacker).capabilities.isCreativeMode) {
                stack.shrink(1);
            }
        } else {
            stack.shrink(1);
        }
        return true;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side,
                                      float hitX, float hitY, float hitZ) {
        if (hand == EnumHand.MAIN_HAND) {
            if (worldIn.getBlockState(pos).getBlock().isFlammable(worldIn, pos, side)) {

                BlockPos target = pos.offset(side);

                if (!(worldIn.canBlockSeeSky(pos) && worldIn.isRaining()) && worldIn.isAirBlock(target)) {
                    worldIn.setBlockState(target, Blocks.FIRE.getDefaultState());
                    if (!player.capabilities.isCreativeMode)
                        player.getHeldItemMainhand().shrink(1);
                }
            }
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public void registerRecipes() {

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this), "  c", " s ", "s  ", 'c',
                new ItemStack(Items.COAL, 1, 0), 's', "stickWood"));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this), "  c", " s ", "s  ", 'c',
                new ItemStack(Items.COAL, 1, 1), 's', "stickWood"));
    }
}
