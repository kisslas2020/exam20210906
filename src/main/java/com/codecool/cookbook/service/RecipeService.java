package com.codecool.cookbook.service;

import com.codecool.cookbook.model.Ingredient;
import com.codecool.cookbook.model.IngredientType;
import com.codecool.cookbook.model.Recipe;
import com.codecool.cookbook.repository.IngredientRepository;
import com.codecool.cookbook.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    public RecipeService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public Recipe[] getAllRecipes() {
        return recipeRepository.findAll().toArray(new Recipe[0]);
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    public Recipe addNewRecipe(Recipe recipe) {
        List<Ingredient> ingredients = recipe.getIngredients();
        for (Ingredient ingredient : ingredients) {
            if (ingredientRepository.findById(ingredient.getId()).orElse(null) == null) {
                return null;
            }
        }
        return recipeRepository.save(recipe);
    }

    public void deleteRecipeById(Long id) {
        recipeRepository.deleteById(id);
    }

    public Recipe[] getVegetarianRecipes() {
        return recipeRepository.findAll()
                .stream()
                .filter(recipe -> recipe.getIngredients().stream()
                        .filter(i -> i.getIngredientType().equals(IngredientType.MEAT))
                        .count() == 0)
                .collect(Collectors.toList())
                .toArray(new Recipe[0]);
    }

    public Recipe[] getNonDairyRecipes() {
        return recipeRepository.findAll()
                .stream()
                .filter(recipe -> recipe.getIngredients().stream()
                        .filter(i -> i.getIngredientType().equals(IngredientType.DAIRY))
                        .count() == 0)
                .collect(Collectors.toList())
                .toArray(new Recipe[0]);
    }
}
