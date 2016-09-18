package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.api.ExtendedItem;
import org.silvercatcher.reforged.entities.EntityDynamite;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemDynamite extends ExtendedItem{
	
	public ItemDynamite() {
		super();
		setUnlocalizedName("dynamite");
		setMaxStackSize(64);
	}
	
	@Override
	public void registerRecipes() {
		GameRegistry.addShapedRecipe(new ItemStack(this, 2), " s ",
														     " g ",
														     " g ",
														     's', new ItemStack(Items.string),
														     'g', new ItemStack(Items.gunpowder));
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn) {		
		
		if(playerIn.capabilities.isCreativeMode || playerIn.inventory.consumeInventoryItem(this)) {
			if(!worldIn.isRemote) {
				worldIn.spawnEntityInWorld(new EntityDynamite(worldIn, playerIn, stack));			
			}
		}
		return stack;
	}
	
}
