package org.silvercatcher.reforged.items.weapons.nob;

import java.util.List;

import org.silvercatcher.reforged.items.ReforgedItem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemNestOfBeesEmpty extends NestOfBeesBase {

	public ItemNestOfBeesEmpty() {
		super("empty");
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		
		tooltip.add("Empty");
	}

	@Override
	public void registerRecipes() {
		
		GameRegistry.addRecipe(new ItemStack(this),
				"lsl",
				"lsl",
				"lll",
				'l', Items.leather,
				's', Items.stick);
	}

	@Override
	public float getHitDamage() {
		return 1;
	}
}
