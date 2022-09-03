package org.silvercatcher.reforged.material;

import java.util.LinkedList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;

/**
 * Beware, this class is a tribute to the genius who thought he should do an
 * enum and then use a hard-coded switch, so no one else can use it properly,
 * even with Forge's bytecode manipulation.
 * <p>
 * A big 'Thank you!' to whoever wrote {@link ToolMaterial#getRepairItem()}
 */
public class MaterialDefinition {

    private final String prefix;
    private final ItemStack repairMaterial;
    private final ToolMaterial material;
    private final List<Item> materialBasedItems;

    public MaterialDefinition(String prefix, ToolMaterial material) {

        this(prefix, material, material.getRepairItemStack());
    }

    public MaterialDefinition(String prefix, ToolMaterial material, ItemStack repairMaterial) {

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

    public ToolMaterial getMaterial() {
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

    public ItemStack getRepairMaterial() {
        return repairMaterial;
    }

    /**
     * convenience method, for example to make silver stronger against undead
     *
     * @param target
     * @return
     */
    public void onEntityHit(Entity target) {
    }
}
