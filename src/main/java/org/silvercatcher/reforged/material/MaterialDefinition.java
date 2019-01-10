package org.silvercatcher.reforged.material;

import java.util.LinkedList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.crafting.Ingredient;

/**
 * Beware, this class is a tribute to the genius who thought he should do an
 * enum and then use a hard-coded switch, so no one else can use it properly,
 * even with Forge's bytecode manipulation.
 * <p>
 * A big 'Thank you!' to whoever wrote {@link ItemTier#getRepairMaterial()}...
 */
public class MaterialDefinition {

	private final String prefix;
	private final Ingredient repairMaterial;
	private final ItemTier material;
	private final List<Item> materialBasedItems;

	public MaterialDefinition(String prefix, ItemTier material) {
		this(prefix, material, material.getRepairMaterial());
	}

	public MaterialDefinition(String prefix, ItemTier material, Ingredient repairMaterial) {
		this.prefix = prefix;
		this.material = material;
		this.repairMaterial = repairMaterial;
		this.materialBasedItems = new LinkedList<>();
	}

	public void addItem(Item item) {
		materialBasedItems.add(item);
	}

	public float getDamageVsEntity() {
		return material.getAttackDamage();
	}

	public float getEfficiencyOnProperMaterial() {
		return material.getEfficiency();
	}

	public int getEnchantability() {
		return material.getEnchantability();
	}

	public ItemTier getMaterial() {
		return material;
	}

	public final List<Item> getMaterialBasedItems() {
		return materialBasedItems;
	}

	public int getMaxUses() {
		return material.getMaxUses();
	}

	public final String getPrefix() {
		return prefix;
	}

	public String getPrefixedName(String baseName) {
		return prefix + "_" + baseName;
	}

	public Ingredient getRepairMaterial() {
		return repairMaterial;
	}

	public boolean matchesRepairMaterial(ItemStack stack) {
		return repairMaterial.test(stack);
	}

	/**
	 * convenience method, e.g. to make silver stronger against undead
	 *
	 * @param target The hit entity
	 */
	public void onEntityHit(Entity target) {
	}
}
