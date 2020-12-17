package com.bezjen.whattoeat.service;

import java.io.IOException;
import java.util.*;

import javax.validation.ValidationException;

import com.bezjen.whattoeat.entity.*;
import com.bezjen.whattoeat.item.ImageItemType;
import com.bezjen.whattoeat.repository.localized.*;
import com.bezjen.whattoeat.service.image.ImageService;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bezjen.whattoeat.exception.LocalizedException;
import com.bezjen.whattoeat.model.AddRecipeModel;
import com.bezjen.whattoeat.model.filter.PaginatedRecipesFilterModel;
import com.bezjen.whattoeat.repository.localized.RecipeRepository;

import org.springframework.web.multipart.MultipartFile;

@Service
public class RecipeService {
	private final static int PAGE_SIZE = 12;

	private LocalizationService localizationService;
	private RecipeRepository recipeRepository;
	private MeasureRepository measureRepository;
	private FoodProductRepository foodProductRepository;
	private CookingMethodRepository cookingMethodRepository;
	private DietRepository dietRepository;
	private ImageService imageService;
	private RecipeCategoryRepository recipeCategoryRepository;

	public RecipeService(
		LocalizationService localizationService,
		ImageService imageService,
		RecipeRepository recipeRepository,
		MeasureRepository measureRepository,
		FoodProductRepository foodProductRepository,
		CookingMethodRepository cookingMethodRepository,
		DietRepository dietRepository,
		RecipeCategoryRepository recipeCategoryRepository
	) {
		this.localizationService = localizationService;
		this.imageService = imageService;
		this.recipeRepository = recipeRepository;
		this.measureRepository = measureRepository;
		this.foodProductRepository = foodProductRepository;
		this.cookingMethodRepository = cookingMethodRepository;
		this.dietRepository = dietRepository;
		this.recipeCategoryRepository = recipeCategoryRepository;
	}

	public List<Recipe> getNewestRecipes(Locale locale) {
		if (Locale.ENGLISH.getLanguage().equals(locale.getLanguage())) {
			return recipeRepository.findTop10ByIsApprovedAndEnLocaleNotNullOrderByDateDesc(Boolean.TRUE);
		}
		return recipeRepository.findTop10ByIsApprovedAndRuLocaleNotNullOrderByDateDesc(Boolean.TRUE);
	}

	public List<Recipe> getBreakfastRecipes(Locale locale) {
		List<Recipe> result;
		if (Locale.ENGLISH.getLanguage().equals(locale.getLanguage())) {
			result = recipeRepository.findTop10ByIsApprovedAndIsBreakfastAndEnLocaleNotNull(Boolean.TRUE, Boolean.TRUE);
		} else {
			result = recipeRepository.findTop10ByIsApprovedAndIsBreakfastAndRuLocaleNotNull(Boolean.TRUE, Boolean.TRUE);
		}
		return result;
	}

	public int getPagesCount() {
		return getPagesCount(recipeRepository.countByIsApproved(Boolean.TRUE));
	}
	
	public List<Recipe> getRecipesList(Locale locale, Integer page) {
		if (Locale.ENGLISH.getLanguage().equals(locale.getLanguage())) {
			return recipeRepository.findByIsApprovedAndEnLocaleNotNullOrderByDateDesc(
					Boolean.TRUE,
					PageRequest.of(page, PAGE_SIZE)
			);
		}
		return recipeRepository.findByIsApprovedAndRuLocaleNotNullOrderByDateDesc(
				Boolean.TRUE,
				PageRequest.of(page, PAGE_SIZE)
		);
	}

	public Recipe getRandomRecipe(Locale locale) {
		List<Recipe> recipes;
		if (Locale.ENGLISH.getLanguage().equals(locale.getLanguage())) {
			recipes = recipeRepository.findByIsApprovedAndEnLocaleNotNullOrderByDateDesc(Boolean.TRUE);
		} else {
			recipes = recipeRepository.findByIsApprovedAndRuLocaleNotNullOrderByDateDesc(Boolean.TRUE);
		}
		Collections.shuffle(recipes);
		return recipes.get(0);
	}

	public long getFilteredPagesCount(Locale locale, PaginatedRecipesFilterModel recipesFilterModel) {
		long count = recipeRepository.countFiltered(
				locale.getLanguage(),
				recipesFilterModel.getMinPrepTime(),
				recipesFilterModel.getMaxPrepTime(),
				recipesFilterModel.getMinCookingTime(),
				recipesFilterModel.getMaxCookingTime(),
				recipesFilterModel.getCookingMethodIds().isEmpty()
						? null : recipesFilterModel.getCookingMethodIds(),
				recipesFilterModel.getDietIds().isEmpty()
						? null : recipesFilterModel.getDietIds(),
				recipesFilterModel.getIncludeIngredientIds().isEmpty()
						? null : recipesFilterModel.getIncludeIngredientIds(),
				recipesFilterModel.getBreakfast(),
				recipesFilterModel.getSnack());
		return getPagesCount(count);
	}

	public List<Recipe> getFilteredRecipesList(Locale locale, PaginatedRecipesFilterModel recipesFilterModel) {
		return getFilteredRecipesList(locale, recipesFilterModel, PAGE_SIZE);
	}

	public List<Recipe> getFilteredRecipesList(
			Locale locale,
			PaginatedRecipesFilterModel recipesFilterModel,
			int pageSize
	) {
		return recipeRepository.findFiltered(
				locale.getLanguage(),
				recipesFilterModel.getMinPrepTime(),
				recipesFilterModel.getMaxPrepTime(),
				recipesFilterModel.getMinCookingTime(),
				recipesFilterModel.getMaxCookingTime(),
				recipesFilterModel.getCookingMethodIds().isEmpty()
						? null : recipesFilterModel.getCookingMethodIds(),
				recipesFilterModel.getDietIds().isEmpty()
						? null : recipesFilterModel.getDietIds(),
				recipesFilterModel.getIncludeIngredientIds().isEmpty()
						? null : recipesFilterModel.getIncludeIngredientIds(),
				recipesFilterModel.getBreakfast(),
				recipesFilterModel.getSnack(),
				PageRequest.of(recipesFilterModel.getPage() - 1, pageSize));
	}

	@Transactional
	public void saveRecipe(AddRecipeModel addRecipeModel, User user) throws LocalizedException {
		Recipe recipe = parseAddRecipeModel(addRecipeModel, user);
		recipeRepository.save(recipe);
		try {
			saveImages(addRecipeModel, recipe);
		} catch (Exception e) {
			recipeRepository.delete(recipe);
			throw e;
		}
	}
	
	//TODO: refactoring; check if image exists; delete images if save isn't success
	private void saveImages(AddRecipeModel addRecipeModel, Recipe recipe) throws LocalizedException {
		try {
			String recipeUrl = imageService.getImagesDirectory()
					+ ImageItemType.RECIPE.getImagesDirectory()
					+ recipe.getId() + "-";
			if (!addRecipeModel.getRecipeImage().isEmpty()) {
				String extension = FilenameUtils.getExtension(addRecipeModel.getRecipeImage().getOriginalFilename());
				recipe.setImageUrl(recipeUrl + "general" + "." + extension);	//TODO: custom image name
				imageService.saveImage(
						addRecipeModel.getRecipeImage(),
						recipe.getId() + "-general",
						extension,
						ImageItemType.RECIPE
				);
			}
			if (!addRecipeModel.getStepsImages().isEmpty()) {
				for (int i = 0; i < addRecipeModel.getStepsImages().size(); i++) {
					int stepIndex = i + 1;
					if (addRecipeModel.getStepsImages().get(i) != null
							&& !addRecipeModel.getStepsImages().get(i).isEmpty()
					) {
						String extension = FilenameUtils.getExtension(
								addRecipeModel.getStepsImages().get(i).getOriginalFilename()
						);
						recipe.getSteps().get(i).setImageUrl(recipeUrl + "step" + stepIndex + "." + extension);
						imageService.saveImage(
								addRecipeModel.getStepsImages().get(i),
								recipe.getId() + "-step" + stepIndex,
								extension,
								ImageItemType.RECIPE
						);
					}
				}
			}
		} catch (IOException e) {
			throw new LocalizedException("Error to save images", "messages.error.image.save");
		}
		recipeRepository.save(recipe);
	}
	
	public int getPagesCount(long recipesCount) {
		return (int) Math.ceil((double)recipesCount / PAGE_SIZE);
	}
	
	//TODO: check name? (locale); remove duplicated validation (javax) 
	public void validateRecipe(AddRecipeModel recipe) throws LocalizedException {
		if (StringUtils.isBlank(recipe.getRecipeName())) {
			throw new LocalizedException("Empty recipe name", "messages.error.addRecipe.recipeName");
		}
		if (recipe.getRecipeCategory() == null) {
			throw new LocalizedException("Empty recipe category", "messages.error.addRecipe.recipeCategory");
		}

		if (recipe.getServingsNumber() == null) {
			throw new LocalizedException("Empty servings number", "messages.error.addRecipe.servingsNumber");
		}
		if (recipe.getPrepTime() == null) {
			throw new LocalizedException("Empty prep time", "messages.error.addRecipe.prepTime");
		}
		if (recipe.getCookingTime() == null) {
			throw new LocalizedException("Empty cooking time", "messages.error.addRecipe.cookingTime");
		}
		if (recipe.getPrimaryCookingMethod() == null) {
			throw new LocalizedException(
					"Empty primary cooking method", "messages.error.addRecipe.primaryCookingMethod"
			);
		}
		
		if (localizationService.isLocalizedEntityExistsByLocalizationAndEntityType(
				recipe.getRecipeName(), EntityType.RECIPE)
		) {
			throw new LocalizedException("Recipe exists", "messages.error.recipe.exists");
		}
		if (!cookingMethodRepository.existsById(recipe.getPrimaryCookingMethod())) {
			throw new LocalizedException(
					"Primary cooking method with id = " + recipe.getPrimaryCookingMethod() + "doesn't exist",
					"messages.error.unexpected"
			);
		}
		for (Long cookingMethodId : recipe.getSecondaryCookingMethods()) {
			if (!cookingMethodRepository.existsById(cookingMethodId)) {
				throw new LocalizedException(
						"Secondary cooking method with Id = " + cookingMethodId + " doesn't exist",
						"messages.error.unexpected"
				);
			}
		}
		validateDataLength(recipe.getFoodProducts().size(), recipe.getMeasures().size());
		for (int i = 0; i < recipe.getFoodProducts().size(); i++) {
			if (!foodProductRepository.existsById(recipe.getFoodProducts().get(i))) {
				throw new LocalizedException(
						"Food product with id = " + recipe.getFoodProducts().get(i) + " doesn't exist",
						"messages.error.unexpected"
				);
			} 
			if (!measureRepository.existsById(recipe.getMeasures().get(i))) {
				throw new LocalizedException(
						"Measure with id = " + recipe.getMeasures().get(i) + " doesn't exist",
						"messages.error.unexpected"
				);
			}	
		}
		checkFileExtensions(recipe);
	}
	
	private void validateDataLength(int... values) {
		if (values.length != 0) {
			if (!Arrays.stream(values).allMatch(v -> v == values[0])) {
				throw new ValidationException("Incorrect recipe ingredients data!");
			}
		}
	}
	
	private Recipe parseAddRecipeModel(AddRecipeModel addRecipeModel, User user) {
		Recipe recipe = new Recipe();

		fillGeneralData(addRecipeModel, recipe, user);
		fillIngredients(addRecipeModel, recipe);
		fillDirections(addRecipeModel, recipe);
		fillAdditionalParameters(addRecipeModel, recipe);

		return recipe;
	}

	private void fillGeneralData(AddRecipeModel addRecipeModel, Recipe recipe, User user) {
		recipe.setRecipeCategory(recipeCategoryRepository.findById(addRecipeModel.getRecipeCategory()).get());
		recipe.setAuthor(user);
		recipe.setDate(new Date());
		recipe.setImageUrl("/img/recipeThumbnail.png");
		recipe.setServingsNumber(addRecipeModel.getServingsNumber());

		List<FoodProduct> foodProducts =
				IterableUtils.toList(foodProductRepository.findAllById(addRecipeModel.getFoodProducts()));
		Integer minAge =
				foodProducts.stream().max(Comparator.comparing(f -> f.getMinAgeMonth())).get().getMinAgeMonth();
		recipe.setMinAgeMonth(minAge);
		recipe.setLocalization(addRecipeModel.getLocale(), addRecipeModel.getRecipeName());

		if (StringUtils.isNotBlank(addRecipeModel.getRecipeDescription())) {
			RecipeDescription description = new RecipeDescription();
			description.setLocalization(addRecipeModel.getLocale(), addRecipeModel.getRecipeDescription());
			description.setRecipe(recipe);
			recipe.setDescription(description);
		}

		recipe.setPrepTime(addRecipeModel.getPrepTime());
		recipe.setCookingTime(addRecipeModel.getCookingTime());
		recipe.setPrimaryCookingMethod(
				cookingMethodRepository.findById(addRecipeModel.getPrimaryCookingMethod()).get()
		);
		for (Long cookingMethodId : addRecipeModel.getSecondaryCookingMethods()) {
			recipe.getSecondaryCookingMethods().add(cookingMethodRepository.findById(cookingMethodId).get());
		}
	}

	private void fillIngredients(AddRecipeModel addRecipeModel, Recipe recipe) {
		for (int i = 0; i < addRecipeModel.getFoodProducts().size(); i++) {
			RecipeIngredient recipeIngredient = new RecipeIngredient();
			recipeIngredient.setRecipe(recipe);
			recipeIngredient.setAmount(addRecipeModel.getAmounts().get(i));
			recipeIngredient.setFoodProduct(
					foodProductRepository.findById(addRecipeModel.getFoodProducts().get(i)).get()
			);
			recipeIngredient.setMeasure(measureRepository.findById(addRecipeModel.getMeasures().get(i)).get());
			recipe.getRecipeIngredients().add(recipeIngredient);
		}
	}

	private void fillDirections(AddRecipeModel addRecipeModel, Recipe recipe) {
		int stepIndex = 1;
		for (String stepItem : addRecipeModel.getSteps()) {
			if (StringUtils.isNotBlank(stepItem)) {
				RecipeStep step = new RecipeStep();
				step.setLocalization(addRecipeModel.getLocale(), stepItem);
				step.setRecipe(recipe);
				step.setStepIndex(stepIndex);
				recipe.getSteps().add(step);
				stepIndex++;
			}
		}
	}

	private void fillAdditionalParameters(AddRecipeModel addRecipeModel, Recipe recipe) {
		recipe.setSnack(addRecipeModel.isSnack());
		recipe.setBreakfast(addRecipeModel.isBreakfastOrBrunch());
		for (Long dietId : addRecipeModel.getDietaryOptions()) {
			recipe.getDiets().add(dietRepository.findById(dietId).get());
		}
	}
	
	private void checkFileExtensions(AddRecipeModel addRecipeModel) {
		checkFileExtension(addRecipeModel.getRecipeImage());
		addRecipeModel.getStepsImages().forEach(this::checkFileExtension);
	}

	private void checkFileExtension(MultipartFile file) {
		if (file != null && !file.isEmpty()) {
			imageService.checkFileExtension(file.getOriginalFilename());
		}
	}
}
