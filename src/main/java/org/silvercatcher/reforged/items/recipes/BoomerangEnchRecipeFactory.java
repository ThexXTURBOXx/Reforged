package org.silvercatcher.reforged.items.recipes;

import com.google.gson.JsonObject;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import org.silvercatcher.reforged.ReforgedMod;

public class BoomerangEnchRecipeFactory implements IRecipeFactory {

    @Override
    public IRecipe parse(JsonContext context, JsonObject json) {
        ShapelessRecipes recipe = ShapelessRecipes.deserialize(json);
        return new BoomerangEnchRecipe(ReforgedMod.ID + ":boomerang_ench", recipe.getRecipeOutput(),
                recipe.getIngredients());
    }

}
