package org.silvercatcher.reforged.material;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;

/**
 * Beware, this class is a tribute to the genius who thought he should do an enum
 * and then use a hard-coded switch, so no one else can use it properly,
 * even with Forge's bytecode manipulation.
 * 
 * A big 'Thank you!' to whoever wrote {@link ToolMaterial#getRepairItem()}
 *
 */
public class MaterialDefinition {

	private final String prefix;
	private final ItemStack repairMaterial;
	private final int maxUses;
	private final float damageVsEntity;
	private float efficiency;
	private final List<Item> materalBasedItems;
	
	public MaterialDefinition(String prefix, ToolMaterial material) {
		
		this(prefix, material,material.getRepairItemStack());
	}
	
	public MaterialDefinition(String prefix, ToolMaterial material, ItemStack repairMaterial) {
	
		this.prefix = prefix;
		this.repairMaterial = repairMaterial;
		this.maxUses = material.getMaxUses();
		this.damageVsEntity = material.getDamageVsEntity();
		this.efficiency = material.getEfficiencyOnProperMaterial();
		this.materalBasedItems = new LinkedList<>();
	}
	
	public final String getPrefix() {
		return prefix;
	}
	
	public String getPrefixedName(String baseName) {
		
		return prefix + "_" + baseName;
	}
	
	public ItemStack getRepairMaterial() {
		return repairMaterial;
	}

	public int getMaxUses() {
		return maxUses;
	}

	public float getDamageVsEntity() {
		return damageVsEntity;
	}

	public float getEfficiencyOnProperMaterial() {
		return efficiency;
	}
	
	public final List<Item> getMateralBasedItems() {
		return materalBasedItems;
	}
	
	public void addItem(Item item) {
		
		materalBasedItems.add(item);
	}
}
