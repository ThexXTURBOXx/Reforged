package org.silvercatcher.reforged;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ReforgedTab extends CreativeTabs {

	public ReforgedTab() {
		
		super(ReforgedMod.ID);
	}
	
	protected ReforgedTab(String label) {
		super(label);
		
	}

	@Override
	public Item getTabIconItem() {

		return ReforgedRegistry.NEST_OF_BEES;
	}
}
