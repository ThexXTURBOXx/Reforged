package org.silvercatcher.reforged.items.weapons.nob;

import org.silvercatcher.reforged.items.ReforgedItem;

public abstract class NestOfBeesBase extends ReforgedItem {

	public NestOfBeesBase(String variant) {
		super("nest_of_bees" + variant);
		setMaxStackSize(1);
		setMaxDamage(100);
	}

	@Override
	public float getHitDamage() {
		return 1;
	}
}
