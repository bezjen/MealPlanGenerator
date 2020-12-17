package com.bezjen.whattoeat.service;

import java.util.*;
import java.util.stream.Collectors;

import com.bezjen.whattoeat.model.filter.BaseGeneratorFilterModel;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bezjen.whattoeat.entity.Recipe;
import com.bezjen.whattoeat.entity.RecipeIngredient;
import com.bezjen.whattoeat.model.filter.GeneratorFilterModel;
import com.bezjen.whattoeat.model.filter.ReplaceMealModel;
import com.bezjen.whattoeat.model.ShoppingListModel;
import com.bezjen.whattoeat.repository.localized.RecipeRepository;

@Service
public class MealPlanGeneratorService {
	private RecipeRepository recipeRepository;

	public MealPlanGeneratorService(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	public List<List<Recipe>> generateMealPlan(Locale locale, GeneratorFilterModel generatorFilterModel) {
		if (generatorFilterModel.isFirstMealBreakfast()) {
			return generateMealPlanWithTraditionalBreakfasts(locale, generatorFilterModel);
		} else {
			return generateMealPlanWithoutTraditionalBreakfasts(locale, generatorFilterModel);
		}
	}

	private List<List<Recipe>> generateMealPlanWithTraditionalBreakfasts(
			Locale locale,
			GeneratorFilterModel generatorFilterModel
	) {
		generatorFilterModel.setBreakfast(Boolean.TRUE);
		List<Recipe> breakfastRecipes = getFilteredRecipesList(locale, generatorFilterModel);
		Collections.shuffle(breakfastRecipes);
		generatorFilterModel.setBreakfast(null);
		List<Recipe> allRecipes = getFilteredRecipesList(locale, generatorFilterModel);
		Collections.shuffle(allRecipes);
		Set<Long> breakfastRecipesSet = new HashSet<>();

		List<List<Recipe>> result = new ArrayList<>(generatorFilterModel.getDaysAmount());

		int breakfastRecipesIndex = 0;
		for (int i = 0; i < generatorFilterModel.getDaysAmount(); i++) {
			if (breakfastRecipesIndex == breakfastRecipes.size()) {
				breakfastRecipesIndex = 0;
				Collections.shuffle(breakfastRecipes);
			}
			result.add(new ArrayList<>(generatorFilterModel.getMealsNumber()));
			result.get(i).add(breakfastRecipes.get(breakfastRecipesIndex));
			breakfastRecipesSet.add(breakfastRecipes.get(breakfastRecipesIndex).getId());
			breakfastRecipesIndex++;
		}
		int allRecipesIndex = 0;
		for (int i = 0; i < generatorFilterModel.getDaysAmount(); i++) {
			for (int j = 1; j < generatorFilterModel.getMealsNumber(); j++) {
				if (allRecipesIndex == allRecipes.size()) {
					allRecipesIndex = 0;
					Collections.shuffle(allRecipes);
				}
				Recipe randomRecipe = allRecipes.get(allRecipesIndex);
				if (breakfastRecipesSet.contains(randomRecipe.getId())) {
					j--;
					allRecipesIndex++;
					continue;
				}
				result.get(i).add(randomRecipe);
				allRecipesIndex++;
			}
		}
		return result;
	}

	private List<List<Recipe>> generateMealPlanWithoutTraditionalBreakfasts(
			Locale locale,
			GeneratorFilterModel generatorFilterModel
	) {
		generatorFilterModel.setBreakfast(null);
		List<Recipe> allRecipes = getFilteredRecipesList(locale, generatorFilterModel);
		Collections.shuffle(allRecipes);

		List<List<Recipe>> result = new ArrayList<>(generatorFilterModel.getDaysAmount());

		int allRecipesIndex = 0;
		for (int i = 0; i < generatorFilterModel.getDaysAmount(); i++) {
			result.add(new ArrayList<>(generatorFilterModel.getMealsNumber()));
			for (int j = 0; j < generatorFilterModel.getMealsNumber(); j++) {
				if (allRecipesIndex == allRecipes.size()) {
					allRecipesIndex = 0;
					Collections.shuffle(allRecipes);
				}
				Recipe randomRecipe = allRecipes.get(allRecipesIndex);
				result.get(i).add(randomRecipe);
				allRecipesIndex++;
			}
		}
		return result;
	}

	public Recipe getRandomMeal(Locale locale, ReplaceMealModel replaceMealModel) {
		if (replaceMealModel.isFirstMealBreakfast() && replaceMealModel.getIndex().equals(0)) {
			replaceMealModel.setBreakfast(true);
		} else {
			replaceMealModel.setBreakfast(null);
		}
		List<Recipe> recipes = getFilteredRecipesList(locale, replaceMealModel);
		List<Recipe> suitableRecipes = recipes.stream().filter(
				r -> !replaceMealModel.getMealsIds().contains(r.getId())).collect(Collectors.toList());
		if (suitableRecipes.size() == 0) {
			suitableRecipes = recipes;
		}
		Collections.shuffle(suitableRecipes);
		return suitableRecipes.get(0);
	}
	
	private List<Recipe> getFilteredRecipesList(Locale locale, BaseGeneratorFilterModel filter) {
		List<Recipe> recipes = recipeRepository.findFiltered(
				locale.getLanguage(),
				filter.getMinPrepTime(),
				filter.getMaxPrepTime(),
				filter.getMinCookingTime(),
				filter.getMaxCookingTime(),
				filter.getCookingMethodIds().isEmpty() ? null : filter.getCookingMethodIds(),
				filter.getDietIds().isEmpty() ? null : filter.getDietIds(),
				filter.getIncludeIngredientIds().isEmpty() ? null : filter.getIncludeIngredientIds(),
				filter.getBreakfast(),
				filter.getSnack());
		if (!CollectionUtils.isEmpty(filter.getExcludeIngredientIds())) {
			recipes = recipes.stream()
					.filter(r -> r.getRecipeIngredients().stream()
							.filter(
									i -> filter.getExcludeIngredientIds().contains(i.getFoodProduct().getId())
							).count() == 0
					).collect(Collectors.toList());
		}
		return recipes;
	}
	
	//TODO: convert measures; only 2 different measures are available for one product
	public List<RecipeIngredient> getShoppingList(ShoppingListModel shoppingListModel) {
		Iterable<Recipe> recipes = recipeRepository.findAllById(shoppingListModel.getMealsIds());
		Map<Long, RecipeIngredient> shoppingList = new HashMap<>();
		for (Recipe recipe : recipes) {
			for (RecipeIngredient recipeIngredient : recipe.getRecipeIngredients()) {
				Long key = recipeIngredient.getFoodProduct().getId();
				if (shoppingList.containsKey(key)) {
					RecipeIngredient cRecipeIngredient = shoppingList.get(key);
					if (cRecipeIngredient.getMeasure().getId().equals(recipeIngredient.getMeasure().getId())) {
						if (cRecipeIngredient.getAmount() != null && recipeIngredient.getAmount() != null) {
							cRecipeIngredient.setAmount(cRecipeIngredient.getAmount() + recipeIngredient.getAmount());
						}
					} else {
						shoppingList.put(-key, recipeIngredient);
					}
				} else {
					shoppingList.put(key, recipeIngredient);
				}
			}
		}
		return new ArrayList<>(shoppingList.values());
	}
}
