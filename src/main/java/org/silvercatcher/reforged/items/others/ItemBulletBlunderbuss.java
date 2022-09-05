package org.silvercatcher.reforged.items.others;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.silvercatcher.reforged.api.ExtendedItem;

public class ItemBulletBlunderbuss extends ExtendedItem {

    public ItemBulletBlunderbuss() {
        super();
        setMaxStackSize(64);
        setUnlocalizedName("blunderbuss_bullet");
    }

    @Override
    public boolean isWeapon() {
        return false;
    }

    @Override
    public void registerRecipes() {

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 8), "h", "g", "p", 'h', "gravel", 'g',
                "gunpowder", 'p', "paper"));
    }
}
