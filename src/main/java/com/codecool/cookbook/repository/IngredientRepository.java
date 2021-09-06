package com.codecool.cookbook.repository;

import com.codecool.cookbook.model.Ingredient;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
