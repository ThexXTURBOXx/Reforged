package org.silvercatcher.reforged.items.others;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.silvercatcher.reforged.api.ExtendedItem;
import org.silvercatcher.reforged.entities.EntityDummy;

public class ItemDummy extends ExtendedItem {

    public ItemDummy() {
        super();
        setUnlocalizedName("dummy");
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer,
                                                    EnumHand hand) {
        if (world.isRemote) {
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
        final float f = 1.0f;
        final float f2 =
                entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * f;
        final float f3 = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f;
        final double d = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX);
        final double d2 =
                entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) + entityplayer.getEyeHeight();
        final double d3 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ);
        final Vec3d vec3d = new Vec3d(d, d2, d3);
        final float f4 = MathHelper.cos(-f3 * 0.01745329f - 3.141593f);
        final float f5 = MathHelper.sin(-f3 * 0.01745329f - 3.141593f);
        final float f6 = -MathHelper.cos(-f2 * 0.01745329f);
        final float f7 = MathHelper.sin(-f2 * 0.01745329f);
        final float f8 = f5 * f6;
        final float f10 = f4 * f6;
        final double d4 = 5.0;
        final Vec3d vec3d2 = vec3d.addVector(f8 * d4, f7 * d4, f10 * d4);
        final RayTraceResult raytraceresult = world.rayTraceBlocks(vec3d, vec3d2, true);
        if (raytraceresult == null || raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK) {
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
        final Block block = world.getBlockState(raytraceresult.getBlockPos()).getBlock();
        final BlockPos blockpos = raytraceresult.getBlockPos();
        final boolean flag = world.getBlockState(blockpos).getBlock().isReplaceable(world, blockpos);
        final BlockPos blockpos2 = flag ? blockpos : blockpos.offset(raytraceresult.sideHit);
        final boolean flag2 = block == Blocks.SNOW;
        final EntityDummy entitydummy = new EntityDummy(world, blockpos2.getX() + 0.5, flag2 ?
                (blockpos2.getY() - 0.12) : blockpos2.getY(), blockpos2.getZ() + 0.5);
        entitydummy.rotationYaw = entityplayer.rotationYaw;
        if (!world.getCollisionBoxes(entitydummy, entitydummy.getEntityBoundingBox().expandXyz(-0.1)).isEmpty()) {
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
        world.spawnEntity(entitydummy);
        if (!entityplayer.capabilities.isCreativeMode) {
            itemstack.stackSize--;
        }

        StatBase stat = StatList.getObjectUseStats(this);
        if (stat != null) {
            entityplayer.addStat(stat);
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }
}
