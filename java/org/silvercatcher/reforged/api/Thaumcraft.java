package org.silvercatcher.reforged.api;

import org.silvercatcher.reforged.api.items.ThaumcraftBoomerang;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Thaumcraft extends APIBase {
	
	public Thaumcraft() {
		modName = "Thaumcraft";
	}
	
	@Override
	public void registerItems() {
		Item item = new ThaumcraftBoomerang(thaumcraft.api.ThaumcraftMaterials.TOOLMAT_THAUMIUM);
		GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
	}
	
	@Override
	public void registerMatDefs() {
		MaterialManager.addMaterialDefinition(thaumcraft.api.ThaumcraftMaterials.TOOLMAT_THAUMIUM,
				new MaterialDefinition("thaumium", thaumcraft.api.ThaumcraftMaterials.TOOLMAT_THAUMIUM, new ItemStack(thaumcraft.api.items.ItemsTC.ingots, 1, 0)));
	}
}