package com.codecool.cookbook.service;

import com.codecool.cookbook.model.Ingredient;
import com.codecool.cookbook.repository.IngredientRepository;
import com.codecool.cookbook.repository.RecipeRepository;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;

    public IngredientService(IngredientRepository ingredientRepository, RecipeRepository recipeRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
    }

    public Ingredient[] getAllIngredients() {
        return ingredientRepository.findAll().toArray(new Ingredient[0]);
    }

    public Ingredient getIngredientById(Long id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    public Ingredient addNewIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public void deleteIngredientById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id).orElse(null);
        boolean b = noRecipeContainsIngredient(ingredient);
        if (ingredient == null || !noRecipeContainsIngredient(ingredient)) {
            return;
        }
        ingredientRepository.deleteById(id);
    }

    private boolean noRecipeContainsIngredient(Ingredient ingredient) {
        return recipeRepository
                .findAll()
                .stream()
                .filter(recipe -> recipe.getIngredients().contains(ingredient))
                .count() == 0;
    }
}
