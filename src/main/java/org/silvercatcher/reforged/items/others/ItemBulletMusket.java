package org.silvercatcher.reforged.items.others;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.ExtendedItem;

public class ItemBulletMusket extends ExtendedItem {

	public ItemBulletMusket() {
		super(new Item.Builder());
		setRegistryName(new ResourceLocation(ReforgedMod.ID, "musket_bullet"));
	}

	@Override
	public boolean isWeapon() {
		return false;
	}

}