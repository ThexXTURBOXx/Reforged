package org.silvercatcher.reforged.weapons;

public abstract class MaterialItem extends ReforgedItem {

	protected final ToolMaterial material;
	
	public MaterialItem(String name, ToolMaterial material) {
	
		super(getNameWithMaterial(name, material));
		this.material = material;
	}

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
}
