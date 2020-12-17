package com.bezjen.whattoeat.service.localized;

import com.bezjen.whattoeat.entity.EntityType;
import com.bezjen.whattoeat.entity.Recipe;
import com.bezjen.whattoeat.model.localized.RecipeModel;
import com.bezjen.whattoeat.repository.localized.LocalizedEntityRepository;
import com.bezjen.whattoeat.repository.localized.RecipeRepository;
import com.bezjen.whattoeat.service.LocalizationService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LocalizedRecipeService extends LocalizedEntityService<Recipe, RecipeModel> {
	public LocalizedRecipeService(
			LocalizationService localizationService,
			LocalizedEntityRepository localizedEntityRepository,
			RecipeRepository entityRepository
	) {
		super(
				Recipe::new,
				RecipeModel::new,
				localizationService,
				localizedEntityRepository,
				entityRepository,
				EntityType.RECIPE
		);
	}

	@Override
	public void delete(Long id) {
		Recipe recipe = findById(id).get();	//TODO: make delete cascade
		recipe.setSecondaryCookingMethods(new HashSet<>());
		recipe.setDiets(new HashSet<>());
		entityRepository.save(recipe);
		super.delete(id);
	}
}
