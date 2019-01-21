package org.silvercatcher.reforged.blocks;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ShapeUtils;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.ReforgedReferences;
import org.silvercatcher.reforged.api.BlockExtension;
import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.entities.TileEntityCaltrop;

public class BlockCaltrop extends BlockContainer implements BlockExtension {

	public BlockCaltrop() {
		super(Block.Builder.create(Material.GRASS, MapColor.GRAY).hardnessAndResistance(0, 30));
		setRegistryName(new ResourceLocation(ReforgedMod.ID, "caltrop"));
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new TileEntityCaltrop();
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
		return createNewTileEntity(world);
	}

	@Override
	public VoxelShape getRenderShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
		return ShapeUtils.create(0.35F, 0.0F, 0.35F, 0.65F, 0.5F, 0.65F);
	}

	@Override
	public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
		return ShapeUtils.create(0.35F, 0.0F, 0.35F, 0.65F, 0.5F, 0.65F);
	}

	@Override
	public VoxelShape getCollisionShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
		return ShapeUtils.empty();
	}

	@Override
	public void getDrops(IBlockState state, NonNullList<ItemStack> drops, World world, BlockPos pos, int fortune) {
		drops.add(new ItemStack(ReforgedAdditions.CALTROP));
	}

	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return isSolid(state);
	}

	@Override
	public boolean isNormalCube(IBlockState state) {
		return isSolid(state);
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockReader world, BlockPos pos) {
		return isSolid(state);
	}

	@Override
	public boolean isSolid(IBlockState state) {
		return false;
	}

	@Override
	public void onEntityCollision(IBlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if (entityIn instanceof EntityLivingBase) {
			EntityLivingBase e = (EntityLivingBase) entityIn;
			e.attackEntityFrom(new DamageSource("caltrop").setDamageBypassesArmor(),
					ReforgedReferences.GlobalValues.CALTROP_DAMAGE.get().floatValue());
			if (!worldIn.isRemote)
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	}

}