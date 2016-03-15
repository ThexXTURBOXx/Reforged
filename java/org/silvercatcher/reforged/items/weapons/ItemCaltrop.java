package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.entities.EntityCaltrop;
import org.silvercatcher.reforged.items.ExtendedItem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemCaltrop extends ExtendedItem{
	
	public ItemCaltrop() {
		super();
		setUnlocalizedName("caltrop");
		setMaxStackSize(64);
	}
	
	@Override
	public void registerRecipes() {
		GameRegistry.addShapedRecipe(new ItemStack(this), " i ",
														  "iii",
														  "   ",
														  'i', new ItemStack(Blocks.iron_bars));
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		
		ItemStack throwStack = stack.copy();
		
		if(playerIn.capabilities.isCreativeMode || playerIn.inventory.consumeInventoryItem(this))
		{
			if(!worldIn.isRemote) {
				worldIn.spawnEntityInWorld(new EntityCaltrop(worldIn, playerIn, stack, pos, side));			
			}
		}
		return true;
	}
	
	@Override
	public float getHitDamage() {
		return 2f;
	}
	
}
