package com.codecool.cookbook.controller;

import com.codecool.cookbook.model.Ingredient;
import com.codecool.cookbook.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService service) {
        this.ingredientService = service;
    }

    @GetMapping
    public Ingredient[] getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable("id") Long id) {
        Ingredient ingredient = ingredientService.getIngredientById(id);
        if (ingredient == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.of(Optional.of(ingredient));
    }

    @PostMapping
    public ResponseEntity<Ingredient> addNewIngredient(@RequestBody Ingredient ingredient) {
        if (ingredient.getId() != null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.of(Optional.of(ingredientService.addNewIngredient(ingredient)));
    }

    @PutMapping
    public ResponseEntity<Ingredient> updateIngredient(@RequestBody Ingredient ingredient) {
        if (ingredient.getId() == null) {
            return ResponseEntity.internalServerError().build();
        }
        Long id = ingredient.getId();
        Ingredient existingIngredient = ingredientService.getIngredientById(id);
        if (existingIngredient == null) {
            return ResponseEntity.internalServerError().build();
        }
        existingIngredient.setName(ingredient.getName());
        existingIngredient.setIngredientType(ingredient.getIngredientType());
        return ResponseEntity.of(Optional.of(ingredientService.addNewIngredient(existingIngredient)));
    }

    @DeleteMapping("/{id}")
    public void deleteIngredientById(@PathVariable("id") Long id) {
        ingredientService.deleteIngredientById(id);
    }
}
