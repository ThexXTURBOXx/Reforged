package org.silvercatcher.reforged.items.others;

import org.silvercatcher.reforged.api.ExtendedItem;

public class ItemBulletBlunderbuss extends ExtendedItem {

	public ItemBulletBlunderbuss() {
		super();
		setMaxStackSize(64);
		setTranslationKey("blunderbuss_bullet");
	}

	@Override
	public boolean isWeapon() {
		return false;
	}

}
