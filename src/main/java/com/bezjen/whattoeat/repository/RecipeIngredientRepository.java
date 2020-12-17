package com.bezjen.whattoeat.repository;

import org.springframework.data.repository.CrudRepository;

import com.bezjen.whattoeat.entity.RecipeIngredient;

public interface RecipeIngredientRepository extends CrudRepository<RecipeIngredient, Long> {
}
