package com.bezjen.whattoeat.service;

import com.bezjen.whattoeat.entity.EntityType;
import com.bezjen.whattoeat.entity.LocalizedEntity;
import com.bezjen.whattoeat.repository.localized.*;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LocalizationService {
	private CookingMethodRepository cookingMethodRepository;
	private DietRepository dietRepository;
	private FoodProductCategoryGroupRepository foodProductCategoryGroupRepository;
	private FoodProductCategoryRepository foodProductCategoryRepository;
	private FoodProductRepository foodProductRepository;
	private MeasureRepository measureRepository;
	private RecipeCategoryRepository recipeCategoryRepository;
	private RecipeRepository recipeRepository;
	private RecipeDescriptionRepository recipeDescriptionRepository;
	private RecipeStepRepository recipeStepRepository;

	public LocalizationService(
			CookingMethodRepository cookingMethodRepository,
			DietRepository dietRepository,
			FoodProductCategoryGroupRepository foodProductCategoryGroupRepository,
			FoodProductCategoryRepository foodProductCategoryRepository,
			FoodProductRepository foodProductRepository,
			MeasureRepository measureRepository,
			RecipeCategoryRepository recipeCategoryRepository,
			RecipeRepository recipeRepository,
			RecipeDescriptionRepository recipeDescriptionRepository,
			RecipeStepRepository recipeStepRepository
	) {
		this.cookingMethodRepository = cookingMethodRepository;
		this.dietRepository = dietRepository;
		this.foodProductCategoryGroupRepository = foodProductCategoryGroupRepository;
		this.foodProductCategoryRepository = foodProductCategoryRepository;
		this.foodProductRepository = foodProductRepository;
		this.measureRepository = measureRepository;
		this.recipeCategoryRepository = recipeCategoryRepository;
		this.recipeRepository = recipeRepository;
		this.recipeDescriptionRepository = recipeDescriptionRepository;
		this.recipeStepRepository = recipeStepRepository;
	}

	public boolean isLocalizedEntityExistsByLocalizationAndEntityType(String localization, EntityType entityType) {
		boolean result = false;
		switch (entityType) {
			case RECIPE:
				result = recipeRepository.existsByEnLocale(localization)
						|| recipeRepository.existsByRuLocale(localization);
				break;
			case FOOD_PRODUCT:
				result = foodProductRepository.existsByEnLocale(localization)
						|| foodProductRepository.existsByRuLocale(localization);
				break;
			case DIET:
				result = dietRepository.existsByEnLocale(localization)
						|| dietRepository.existsByRuLocale(localization);
				break;
			case MEASURE:
				result = measureRepository.existsByEnLocale(localization)
						|| measureRepository.existsByRuLocale(localization);
				break;
			case RECIPE_STEP:
				result = recipeStepRepository.existsByEnLocale(localization)
						|| recipeStepRepository.existsByRuLocale(localization);
				break;
			case COOKING_METHOD:
				result = cookingMethodRepository.existsByEnLocale(localization)
						|| cookingMethodRepository.existsByRuLocale(localization);
				break;
			case RECIPE_DESCRIPTION:
				result = recipeDescriptionRepository.existsByEnLocale(localization)
						|| recipeDescriptionRepository.existsByRuLocale(localization);
				break;
			case FOOD_PRODUCT_CATEGORY:
				result = foodProductCategoryRepository.existsByEnLocale(localization)
						|| foodProductCategoryRepository.existsByRuLocale(localization);
				break;
			case FOOD_PRODUCT_CATEGORY_GROUP:
				result = foodProductCategoryGroupRepository.existsByEnLocale(localization)
						|| foodProductCategoryGroupRepository.existsByRuLocale(localization);
				break;
			case RECIPE_CATEGORY:
				result = recipeCategoryRepository.existsByEnLocale(localization)
						|| recipeCategoryRepository.existsByRuLocale(localization);
				break;
		}
		return result;
	}

	//TODO: think about this: synonyms in one language don't have multiple translation in another
	public boolean isLocalizedEntityExists(LocalizedEntity entity, EntityType entityType) {
		return isLocalizedEntityExistsByLocalizationAndEntityType(entity.getEnLocale(), entityType)
				|| isLocalizedEntityExistsByLocalizationAndEntityType(entity.getRuLocale(), entityType);
	}

	public Map<Long, String> findAll(String locale, EntityType entityType) {
		Map<Long, String> result = new HashMap<>();
		switch (entityType) {
			case RECIPE:
				if ("en".equals(locale)) {
					result = IterableUtils.toList(recipeRepository.findAll())
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getEnLocale()));
				} else {
					result = IterableUtils.toList(recipeRepository.findAll())
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getRuLocale()));
				}
				break;
			case FOOD_PRODUCT:
				if ("en".equals(locale)) {
					result = IterableUtils.toList(foodProductRepository.findAll())
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getEnLocale()));
				} else {
					result = IterableUtils.toList(foodProductRepository.findAll())
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getRuLocale()));
				}
				break;
			case DIET:
				if ("en".equals(locale)) {
					result = IterableUtils.toList(dietRepository.findAll())
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getEnLocale()));
				} else {
					result = IterableUtils.toList(dietRepository.findAll())
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getRuLocale()));
				}
				break;
			case MEASURE:
				if ("en".equals(locale)) {
					result = IterableUtils.toList(measureRepository.findAll())
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getEnLocale()));
				} else {
					result = IterableUtils.toList(measureRepository.findAll())
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getRuLocale()));
				}
				break;
			case RECIPE_STEP:
				if ("en".equals(locale)) {
					result = IterableUtils.toList(recipeStepRepository.findAll())
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getEnLocale()));
				} else {
					result = IterableUtils.toList(recipeStepRepository.findAll())
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getRuLocale()));
				}
				break;
			case COOKING_METHOD:
				if ("en".equals(locale)) {
					result = IterableUtils.toList(cookingMethodRepository.findAll())
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getEnLocale()));
				} else {
					result = IterableUtils.toList(cookingMethodRepository.findAll())
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getRuLocale()));
				}
				break;
			case RECIPE_DESCRIPTION:
				if ("en".equals(locale)) {
					result = IterableUtils.toList(recipeDescriptionRepository.findAll())
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getEnLocale()));
				} else {
					result = IterableUtils.toList(recipeDescriptionRepository.findAll())
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getRuLocale()));
				}
				break;
			case FOOD_PRODUCT_CATEGORY:
				if ("en".equals(locale)) {
					result = IterableUtils.toList(foodProductCategoryRepository.findAll())
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getEnLocale()));
				} else {
					result = IterableUtils.toList(foodProductCategoryRepository.findAll())
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getRuLocale()));
				}
				break;
			case FOOD_PRODUCT_CATEGORY_GROUP:
				if ("en".equals(locale)) {
					result = IterableUtils.toList(foodProductCategoryGroupRepository.findAll())
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getEnLocale()));
				} else {
					result = IterableUtils.toList(foodProductCategoryGroupRepository.findAll())
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getRuLocale()));
				}
				break;
			case RECIPE_CATEGORY:
				if ("en".equals(locale)) {
					result = IterableUtils.toList(recipeCategoryRepository.findAll())
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getEnLocale()));
				} else {
					result = IterableUtils.toList(recipeCategoryRepository.findAll())
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getRuLocale()));
				}
				break;
		}
		return result;
	}

	public Map<Long, String> findAllSimilar(String locale, EntityType entityType, String localization) {
		Map<Long, String> result = new HashMap<>();
		switch (entityType) {
			case RECIPE:
				if ("en".equals(locale)) {
					result = recipeRepository
								.findByEnLocaleStartingWithIgnoreCase(localization)
								.stream()
								.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getEnLocale()));
				} else {
					result = recipeRepository
							.findByRuLocaleStartingWithIgnoreCase(localization)
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getRuLocale()));
				}
				break;
			case FOOD_PRODUCT:
				if ("en".equals(locale)) {
					result = foodProductRepository
							.findByEnLocaleStartingWithIgnoreCase(localization)
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getEnLocale()));
				} else {
					result = foodProductRepository
							.findByRuLocaleStartingWithIgnoreCase(localization)
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getRuLocale()));
				}
				break;
			case DIET:
				if ("en".equals(locale)) {
					result = dietRepository
							.findByEnLocaleStartingWithIgnoreCase(localization)
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getEnLocale()));
				} else {
					result = dietRepository
							.findByRuLocaleStartingWithIgnoreCase(localization)
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getRuLocale()));
				}
				break;
			case MEASURE:
				if ("en".equals(locale)) {
					result = measureRepository
							.findByEnLocaleStartingWithIgnoreCase(localization)
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getEnLocale()));
				} else {
					result = measureRepository
							.findByRuLocaleStartingWithIgnoreCase(localization)
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getRuLocale()));
				}
				break;
			case RECIPE_STEP:
				if ("en".equals(locale)) {
					result = recipeStepRepository
							.findByEnLocaleStartingWithIgnoreCase(localization)
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getEnLocale()));
				} else {
					result = recipeStepRepository
							.findByRuLocaleStartingWithIgnoreCase(localization)
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getRuLocale()));
				}
				break;
			case COOKING_METHOD:
				if ("en".equals(locale)) {
					result = cookingMethodRepository
							.findByEnLocaleStartingWithIgnoreCase(localization)
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getEnLocale()));
				} else {
					result = cookingMethodRepository
							.findByRuLocaleStartingWithIgnoreCase(localization)
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getRuLocale()));
				}
				break;
			case RECIPE_DESCRIPTION:
				if ("en".equals(locale)) {
					result = recipeDescriptionRepository
							.findByEnLocaleStartingWithIgnoreCase(localization)
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getEnLocale()));
				} else {
					result = recipeDescriptionRepository
							.findByRuLocaleStartingWithIgnoreCase(localization)
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getRuLocale()));
				}
				break;
			case FOOD_PRODUCT_CATEGORY:
				if ("en".equals(locale)) {
					result = foodProductCategoryRepository
							.findByEnLocaleStartingWithIgnoreCase(localization)
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getEnLocale()));
				} else {
					result = foodProductCategoryRepository
							.findByRuLocaleStartingWithIgnoreCase(localization)
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getRuLocale()));
				}
				break;
			case FOOD_PRODUCT_CATEGORY_GROUP:
				if ("en".equals(locale)) {
					result = foodProductCategoryGroupRepository
							.findByEnLocaleStartingWithIgnoreCase(localization)
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getEnLocale()));
				} else {
					result = foodProductCategoryGroupRepository
							.findByRuLocaleStartingWithIgnoreCase(localization)
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getRuLocale()));
				}
				break;
			case RECIPE_CATEGORY:
				if ("en".equals(locale)) {
					result = recipeCategoryRepository
							.findByEnLocaleStartingWithIgnoreCase(localization)
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getEnLocale()));
				} else {
					result = recipeCategoryRepository
							.findByRuLocaleStartingWithIgnoreCase(localization)
							.stream()
							.collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getRuLocale()));
				}
				break;
		}
		return result;
	}

//TODO: refactoring
//	Localization findByValue(String value);
//	List<Localization> findByValueAndEntityType(String value, EntityType entityType);
//	List<Localization> findByValueStartingWithIgnoreCaseAndEntityTypeAndLocale(String value, EntityType entityType, String locale);
//	List<Localization> findByLocaleAndEntityType(String locale, EntityType entityType);
}
