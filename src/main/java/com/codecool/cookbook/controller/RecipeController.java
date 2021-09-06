package com.codecool.cookbook.controller;

import com.codecool.cookbook.model.Recipe;
import com.codecool.cookbook.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService service) {
        this.recipeService = service;
    }

    @GetMapping
    public Recipe[] getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable("id") Long id) {
        Recipe recipe = recipeService.getRecipeById(id);
        if (recipe == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.of(Optional.of(recipe));
    }

    @PostMapping
    public ResponseEntity<Recipe> addNewRecipe(@RequestBody Recipe recipe) {
        if (recipe.getId() != null) {
            return ResponseEntity.internalServerError().build();
        }
        Recipe response = recipeService.addNewRecipe(recipe);
        if (response == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.of(Optional.of(response));
    }

    @PutMapping
    public ResponseEntity<Recipe> updateRecipe(@RequestBody Recipe recipe) {
        if (recipe.getId() == null) {
            return ResponseEntity.internalServerError().build();
        }
        Long id = recipe.getId();
        Recipe existingRecipe = recipeService.getRecipeById(id);
        if (existingRecipe == null) {
            return ResponseEntity.internalServerError().build();
        }
        existingRecipe.setName(recipe.getName());
        existingRecipe.setIngredients(recipe.getIngredients());
        return ResponseEntity.of(Optional.of(recipeService.addNewRecipe(existingRecipe)));
    }

    @DeleteMapping("/{id}")
    public void deleteRecipeById(@PathVariable("id") Long id) {
        recipeService.deleteRecipeById(id);
    }

    @GetMapping("/vegetarian")
    public Recipe[] getVegetarianRecipes() {
        return recipeService.getVegetarianRecipes();
    }

    @GetMapping("/non-dairy")
    public Recipe[] getNonDairyRecipes() {
        return recipeService.getNonDairyRecipes();
    }

}
