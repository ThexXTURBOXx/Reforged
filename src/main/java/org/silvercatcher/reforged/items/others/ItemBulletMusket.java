package org.silvercatcher.reforged.items.others;

import org.silvercatcher.reforged.api.ExtendedItem;

public class ItemBulletMusket extends ExtendedItem {

	public ItemBulletMusket() {
		super();
		setMaxStackSize(64);
		setTranslationKey("musket_bullet");
	}

	@Override
	public boolean isWeapon() {
		return false;
	}

}
