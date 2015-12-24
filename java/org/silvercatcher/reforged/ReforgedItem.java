package org.silvercatcher.reforged;

import net.minecraft.item.Item;

public abstract class ReforgedItem extends Item {

	protected final String name;
	
	public ReforgedItem(String name) {

		this.name = name;
		setCreativeTab(ReforgedMod.tabReforged);
		setUnlocalizedName(name);
		registerRecipes();
	}
	
	public final String getName() { return name; }
	
	public abstract void registerRecipes();
}
