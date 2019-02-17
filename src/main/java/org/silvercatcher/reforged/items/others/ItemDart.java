package org.silvercatcher.reforged.items.others;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.ExtendedItem;

public class ItemDart extends ExtendedItem {

	public ItemDart(String effect) {
		super(new Item.Properties());
		setRegistryName(new ResourceLocation(ReforgedMod.ID, "dart_" + effect));
	}

	@Override
	public float getHitDamage() {
		return 1f;
	}

}