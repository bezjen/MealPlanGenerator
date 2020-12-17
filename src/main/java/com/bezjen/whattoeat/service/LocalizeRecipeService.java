package com.bezjen.whattoeat.service;

import com.bezjen.whattoeat.entity.Recipe;
import com.bezjen.whattoeat.repository.localized.RecipeRepository;
import org.springframework.stereotype.Service;

import com.bezjen.whattoeat.model.LocalizeRecipeModel;

@Service
public class LocalizeRecipeService {
	private RecipeRepository recipeRepository;

	public LocalizeRecipeService(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	public void localizeRecipeRu(Long id, LocalizeRecipeModel localizeRecipeModel) {
		localizeRecipe(id, localizeRecipeModel, "en");
	}

	public void localizeRecipe(Long id, LocalizeRecipeModel localizeRecipeModel, String langCode) {
		Recipe recipe = recipeRepository.findById(id).get();
		recipe.setLocalization(langCode, localizeRecipeModel.getRecipeName());
		if (recipe.getDescription() != null) {
			recipe.getDescription().setLocalization(langCode, localizeRecipeModel.getRecipeDescription());
		}
		for (int i = 0; i < recipe.getSteps().size(); i++) {
			recipe.getSteps().get(i).setLocalization(langCode, localizeRecipeModel.getSteps().get(i));
		}
		recipeRepository.save(recipe);
	}
}
