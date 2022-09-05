package org.silvercatcher.reforged.items.others;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.silvercatcher.reforged.api.ExtendedItem;

public class ItemCrossbowBolt extends ExtendedItem {

    public ItemCrossbowBolt() {
        super();
        setMaxStackSize(64);
        setUnlocalizedName("crossbow_bolt");
    }

    @Override
    public boolean isWeapon() {
        return false;
    }

    @Override
    public void registerRecipes() {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 4), "i", "f", 'i', "ingotIron", 'f', "feather"));
    }

}
