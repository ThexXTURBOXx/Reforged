package org.silvercatcher.reforged.items;

import net.minecraft.item.ItemStack;

public abstract class MaterialItem extends ReforgedItem {

	protected final ToolMaterial material;
	
	public MaterialItem(String name, ToolMaterial material) {
	
		super(getNameWithMaterial(name, material));
		this.material = material;
	}

	public final ToolMaterial getMaterial() {
		
		return material;
	}
	
	protected abstract int getMaxDamageForMaterial(ToolMaterial material);
		
	public static String getNameWithMaterial(String base, ToolMaterial material) {
		
		String materialPrefix = "";
		
		switch (material) {

		case EMERALD:
			materialPrefix = "diamond";
			break;
		case GOLD:
			materialPrefix = "golden";
			break;
		case IRON:
			materialPrefix = "iron";
			break;
		case STONE:
			materialPrefix = "stone";
			break;
		case WOOD:
			materialPrefix = "wooden";
			break;
		default:
			break;
		}
		return materialPrefix + "_" + base;
	}
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		
		return (repair.getItem() != null && repair.getItem()
				== material.getRepairItemStack().getItem());
	}
	
	@Override
	public int getItemEnchantability() {
		
		return material.getEnchantability();
	}
}
