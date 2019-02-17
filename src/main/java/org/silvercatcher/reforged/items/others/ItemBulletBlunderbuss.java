package org.silvercatcher.reforged.items.others;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.api.ExtendedItem;

public class ItemBulletBlunderbuss extends ExtendedItem {

	public ItemBulletBlunderbuss() {
		super(new Item.Properties());
		setRegistryName(new ResourceLocation(ReforgedMod.ID, "blunderbuss_bullet"));
	}

	@Override
	public boolean isWeapon() {
		return false;
	}

}