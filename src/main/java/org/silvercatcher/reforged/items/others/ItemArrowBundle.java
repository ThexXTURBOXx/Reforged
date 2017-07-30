package org.silvercatcher.reforged.items.others;

import org.silvercatcher.reforged.api.ExtendedItem;

public class ItemArrowBundle extends ExtendedItem {

	public ItemArrowBundle() {
		super();
		setMaxStackSize(16);
		setUnlocalizedName("arrow_bundle");
	}

	@Override
	public boolean isWeapon() {
		return false;
	}

}
