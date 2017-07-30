package org.silvercatcher.reforged.items.others;

import org.silvercatcher.reforged.api.ExtendedItem;

public class ItemCrossbowBolt extends ExtendedItem {

	public ItemCrossbowBolt() {
		super();
		setMaxStackSize(64);
		setUnlocalizedName("crossbow_bolt");
	}

	@Override
	public boolean isWeapon() {
		return false;
	}

}