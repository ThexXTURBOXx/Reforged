package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.items.MaterialItem;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemSaber extends MaterialItem {

	public ItemSaber(ToolMaterial material) {
		
		super("saber", material);
		
		setMaxStackSize(1);
		setMaxDamage(getMaxDamageForMaterial(material));
	}

	@Override
	protected int getMaxDamageForMaterial(ToolMaterial material) {
		
		return material.getMaxUses();
	}

	@Override
	public void registerRecipes() {
		
		GameRegistry.addRecipe(new ItemStack(this),
				" b ",
				"b  ",
				"s  ",
				'b', material.getRepairItemStack(),
				's', Items.stick);
	}

	@Override
	public float getHitDamage() {
		
		return material.getDamageVsEntity() + 3.5f;
	}
}
