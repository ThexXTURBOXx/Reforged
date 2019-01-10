package org.silvercatcher.reforged.items.others;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.ExtendedItem;

public class ItemArrowBundle extends ExtendedItem {

	public ItemArrowBundle() {
		super(new Item.Builder().maxStackSize(16));
		setRegistryName(new ResourceLocation(ReforgedMod.ID, "arrow_bundle"));
	}

	@Override
	public boolean isWeapon() {
		return false;
	}

}
