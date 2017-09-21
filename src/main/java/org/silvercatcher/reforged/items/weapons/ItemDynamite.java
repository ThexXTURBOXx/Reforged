package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.api.ExtendedItem;
import org.silvercatcher.reforged.entities.EntityDynamite;
import org.silvercatcher.reforged.util.Helpers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

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
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {

		if (playerIn.capabilities.isCreativeMode || Helpers.consumeInventoryItem(playerIn, this)) {
			if (!worldIn.isRemote) {
				worldIn.spawnEntity(new EntityDynamite(worldIn, playerIn, playerIn.getHeldItem(hand)));
			}
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
	}

	@Override
	public void registerRecipes() {
		GameRegistry
				.addRecipe(new ShapedOreRecipe(new ItemStack(this, 2), "s", "g", "g", 's', "string", 'g', "gunpowder"));
	}

}
