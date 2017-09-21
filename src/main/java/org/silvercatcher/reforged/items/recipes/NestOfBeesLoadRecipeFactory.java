package org.silvercatcher.reforged.items.recipes;

import org.silvercatcher.reforged.ReforgedMod;

import com.google.gson.JsonObject;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class NestOfBeesLoadRecipeFactory implements IRecipeFactory {

	@Override
	public IRecipe parse(JsonContext context, JsonObject json) {
		ShapelessRecipes recipe = ShapelessRecipes.deserialize(json);
		return new NestOfBeesLoadRecipe(ReforgedMod.ID + ":nob_load", recipe.getRecipeOutput(),
				recipe.getIngredients());
	}

}