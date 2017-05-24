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
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityCaltropBlock extends BlockContainer implements BlockExtension {

	private EntityLivingBase owner;

	public TileEntityCaltropBlock() {
		super(Material.grass);
		setUnlocalizedName("caltrop");
		setCreativeTab(ReforgedMod.tabReforged);
		setBlockBounds(0.35F, 0.0F, 0.35F, 0.65F, 0.5F, 0.65F);
		setResistance(30);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCaltropEntity();
	}

	@Override
	public void registerRecipes() {
		GameRegistry.addShapedRecipe(new ItemStack(this, 4), " i ", " i ", "i i", 'i', new ItemStack(Blocks.iron_bars));
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		owner = placer;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		onEntityCollidedWithBlock(world, pos, entity);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, Entity entity) {
		if (entity instanceof EntityLivingBase) {
			EntityLivingBase e = (EntityLivingBase) entity;
			if (e != owner) {
				e.attackEntityFrom(new DamageSource("caltrop").setDamageBypassesArmor(), 8);
				if (!world.isRemote)
					world.setBlockToAir(pos);
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
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockAccess world, BlockPos pos) {
		return false;
	}
}