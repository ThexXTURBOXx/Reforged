package org.silvercatcher.reforged;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ReforgedTab extends CreativeTabs {

	public ReforgedTab() {
		
		super(ReforgedMod.ID);
	}
	

	@Override
	public Item getTabIconItem() {

		return ReforgedRegistry.IRON_BATTLE_AXE;
	}
}
