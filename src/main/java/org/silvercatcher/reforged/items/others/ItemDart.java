package org.silvercatcher.reforged.items.others;

import org.silvercatcher.reforged.api.ExtendedItem;

public class ItemDart extends ExtendedItem {

	public ItemDart(String effect) {
		super();
		setTranslationKey("dart_" + effect);
		setMaxStackSize(64);
	}

	@Override
	public float getHitDamage() {
		return 1f;
	}

}
