package org.silvercatcher.reforged.items.others;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.ExtendedItem;

public class ItemCrossbowBolt extends ExtendedItem {

	public ItemCrossbowBolt() {
		super(new Item.Properties());
		setRegistryName(new ResourceLocation(ReforgedMod.ID, "crossbow_bolt"));
	}

	@Override
	public boolean isWeapon() {
		return false;
	}

}