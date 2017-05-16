package org.silvercatcher.reforged.blocks;

import java.util.ArrayList;
import java.util.List;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.BlockExtension;
import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.entities.TileEntityCaltropEntity;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityCaltropBlock extends BlockContainer implements BlockExtension {
	
	private EntityLivingBase owner;
	
	public TileEntityCaltropBlock() {
		super(Material.GRASS);
		setUnlocalizedName("caltrop");
		setCreativeTab(ReforgedMod.tabReforged);
		setResistance(30);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCaltropEntity();
	}
	
	@Override
	public void registerRecipes() {
		GameRegistry.addShapedRecipe(new ItemStack(this, 4), " i ",
														     " i ",
													         "i i",
														     'i', new ItemStack(Blocks.IRON_BARS));
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(0.35F, 0.0F, 0.35F, 0.65F, 0.5F, 0.65F);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return getBoundingBox(blockState, null, pos);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		owner = placer;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if(entityIn instanceof EntityLivingBase) {
			EntityLivingBase e = (EntityLivingBase) entityIn;
			if(e != owner) {
				e.attackEntityFrom(new DamageSource("caltrop").setDamageBypassesArmor(), 8);
				if(!worldIn.isRemote) worldIn.setBlockToAir(pos);
			}
		}
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		List<ItemStack> items = new ArrayList<>();
		items.add(new ItemStack(ReforgedAdditions.CALTROP));
		return items;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return isOpaqueCube(state);
	}
	
	@Override
	public boolean isNormalCube(IBlockState state) {
		return isOpaqueCube(state);
	}
	
	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return isOpaqueCube(state);
	}
}