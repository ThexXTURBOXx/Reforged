package org.silvercatcher.reforged.api;

import org.silvercatcher.reforged.items.weapons.ItemBattleAxe;
import org.silvercatcher.reforged.items.weapons.ItemBoomerang;
import org.silvercatcher.reforged.items.weapons.ItemKatana;
import org.silvercatcher.reforged.items.weapons.ItemKnife;
import org.silvercatcher.reforged.items.weapons.ItemMusketWithBayonet;
import org.silvercatcher.reforged.items.weapons.ItemSaber;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

public class ProjectE extends APIBase {
	
	/**ProjectE's DarkMatter as ToolMaterial*/
	public static ToolMaterial ProjectE_DarkMatter = EnumHelper.addToolMaterial("DARK_MATTER", 7, 9999, 30, 8, 0);
	/**ProjectE's RedMatter as ToolMaterial*/
	public static ToolMaterial ProjectE_RedMatter = EnumHelper.addToolMaterial("RED_MATTER", 7, 9999, 36, 12, 0);
	
	public ProjectE() {
		modName = "ProjectE";
	}
	
	@Override
	public void registerMatDefs() {
		MaterialManager.addMaterialDefinition(ProjectE_DarkMatter,
				new MaterialDefinition("dark_matter", ProjectE_DarkMatter, new ItemStack(moze_intel.projecte.gameObjs.ObjHandler.matter, 1, 0)));
		MaterialManager.addMaterialDefinition(ProjectE_RedMatter,
				new MaterialDefinition("red_matter", ProjectE_RedMatter, new ItemStack(moze_intel.projecte.gameObjs.ObjHandler.matter, 1, 1)));
	}
	
	@Override
	public void registerItems() {
		super.registerItems();
		simpleReg(ReforgedAdditions.DM_MUSKET = new ItemMusketWithBayonet(ProjectE_DarkMatter));
		simpleReg(ReforgedAdditions.DM_BATTLE_AXE = new ItemBattleAxe(ProjectE_DarkMatter));
		simpleReg(ReforgedAdditions.DM_BOOMERANG = new ItemBoomerang(ProjectE_DarkMatter));
		simpleReg(ReforgedAdditions.DM_SABER = new ItemSaber(ProjectE_DarkMatter));
		simpleReg(ReforgedAdditions.DM_KNIFE = new ItemKnife(ProjectE_DarkMatter));
		simpleReg(ReforgedAdditions.DM_KATANA = new ItemKatana(ProjectE_DarkMatter));
		simpleReg(ReforgedAdditions.RM_MUSKET = new ItemMusketWithBayonet(ProjectE_RedMatter));
		simpleReg(ReforgedAdditions.RM_BATTLE_AXE = new ItemBattleAxe(ProjectE_RedMatter));
		simpleReg(ReforgedAdditions.RM_BOOMERANG = new ItemBoomerang(ProjectE_RedMatter));
		simpleReg(ReforgedAdditions.RM_SABER = new ItemSaber(ProjectE_RedMatter));
		simpleReg(ReforgedAdditions.RM_KNIFE = new ItemKnife(ProjectE_RedMatter));
		simpleReg(ReforgedAdditions.RM_KATANA = new ItemKatana(ProjectE_RedMatter));
	}
}