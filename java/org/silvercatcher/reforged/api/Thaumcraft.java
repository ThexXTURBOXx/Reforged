package org.silvercatcher.reforged.api;

import org.silvercatcher.reforged.items.weapons.ItemBattleAxe;
import org.silvercatcher.reforged.items.weapons.ItemBoomerang;
import org.silvercatcher.reforged.items.weapons.ItemKatana;
import org.silvercatcher.reforged.items.weapons.ItemKnife;
import org.silvercatcher.reforged.items.weapons.ItemMusketWithBayonet;
import org.silvercatcher.reforged.items.weapons.ItemSaber;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;

import net.minecraft.item.ItemStack;

public class Thaumcraft extends APIBase {
	
	public Thaumcraft() {
		modName = "Thaumcraft";
	}
	
	@Override
	public void registerMatDefs() {
		MaterialManager.addMaterialDefinition(thaumcraft.api.ThaumcraftMaterials.TOOLMAT_THAUMIUM,
				new MaterialDefinition("thaumium", thaumcraft.api.ThaumcraftMaterials.TOOLMAT_THAUMIUM, new ItemStack(thaumcraft.api.items.ItemsTC.ingots, 1, 0)));
	}
	
	@Override
	public void registerItems() {
		super.registerItems();
		simpleReg(ReforgedAdditions.THAUMIUM_MUSKET = new ItemMusketWithBayonet(thaumcraft.api.ThaumcraftMaterials.TOOLMAT_THAUMIUM));
		simpleReg(ReforgedAdditions.THAUMIUM_BATTLE_AXE = new ItemBattleAxe(thaumcraft.api.ThaumcraftMaterials.TOOLMAT_THAUMIUM));
		simpleReg(ReforgedAdditions.THAUMIUM_BOOMERANG = new ItemBoomerang(thaumcraft.api.ThaumcraftMaterials.TOOLMAT_THAUMIUM));
		simpleReg(ReforgedAdditions.THAUMIUM_SABER = new ItemSaber(thaumcraft.api.ThaumcraftMaterials.TOOLMAT_THAUMIUM));
		simpleReg(ReforgedAdditions.THAUMIUM_KNIFE = new ItemKnife(thaumcraft.api.ThaumcraftMaterials.TOOLMAT_THAUMIUM));
		simpleReg(ReforgedAdditions.THAUMIUM_KATANA = new ItemKatana(thaumcraft.api.ThaumcraftMaterials.TOOLMAT_THAUMIUM));
	}
}