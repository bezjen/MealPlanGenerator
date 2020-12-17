package com.bezjen.whattoeat.controller;

import com.bezjen.whattoeat.entity.Diet;
import com.bezjen.whattoeat.entity.Recipe;
import com.bezjen.whattoeat.item.diet.DietProductCategory;
import com.bezjen.whattoeat.item.diet.DietProductCategoryType;
import com.bezjen.whattoeat.item.diet.DietType;
import com.bezjen.whattoeat.model.filter.PaginatedRecipesFilterModel;
import com.bezjen.whattoeat.service.RecipeService;
import com.bezjen.whattoeat.service.localized.DietService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@Controller
@RequestMapping(value = "/diets")
public class DietController extends BaseController {
	private DietService dietService;
	private RecipeService recipeService;

	public DietController(
			MessageSource messageSource,
			DietService dietService,
			RecipeService recipeService
	) {
		super(messageSource);
		this.dietService = dietService;
		this.recipeService = recipeService;
	}

	@GetMapping("")
	public String showAll(Model model) {
		addMetadataToModel(
				model,
				getMessage("general.pages.diets"),
				getMessage("meta.diets"),
				getMessage("meta.diets")
		);
		model.addAttribute("diets", dietService.findAll());
		return "diets/showDiets";
	}

	@RequestMapping(value = "/show/{id}", method = {RequestMethod.POST, RequestMethod.GET})
	public String showDiet(@PathVariable("id") Long id, Model model, Locale locale) {
		Optional<Diet> dietOptional = dietService.findById(id);
		if (!dietOptional.isPresent()) {
			return "redirect:/errors/404";
		}
		Diet diet = dietOptional.get();
		addDietMetadataToModel(model, diet, locale);
		model.addAttribute("diet", diet);
		model.addAttribute("dietProductCategories", getDietProductCategories(diet));
		model.addAttribute("dietRecipes", getDietRecipes(locale, diet));
		return "diets/showDiet";
	}

	private List<Recipe> getDietRecipes(Locale locale, Diet diet) {
		PaginatedRecipesFilterModel filter = new PaginatedRecipesFilterModel(1);
		filter.setDietIds(Arrays.asList(diet.getId()));
		List<Recipe> recipes = recipeService.getFilteredRecipesList(locale, filter, 6);
		return recipes;
	}

	private List<DietProductCategory> getDietProductCategories(Diet diet) {
		DietType dietType = DietType.valueOfByEnLocale(diet.getEnLocale());
		List<DietProductCategory> dietProductCategories = new ArrayList<>(15);
		dietProductCategories.add(getDietProductCategory(dietType, DietProductCategoryType.ALCOHOL));
		dietProductCategories.add(getDietProductCategory(dietType, DietProductCategoryType.BREAD));
		dietProductCategories.add(getDietProductCategory(dietType, DietProductCategoryType.CEREAL));
		dietProductCategories.add(getDietProductCategory(dietType, DietProductCategoryType.EGGS));
		dietProductCategories.add(getDietProductCategory(dietType, DietProductCategoryType.FISH));
		dietProductCategories.add(getDietProductCategory(dietType, DietProductCategoryType.FRUIT));
		dietProductCategories.add(getDietProductCategory(dietType, DietProductCategoryType.MEAL_AND_POULTRY));
		dietProductCategories.add(getDietProductCategory(dietType, DietProductCategoryType.MILK));
		dietProductCategories.add(getDietProductCategory(dietType, DietProductCategoryType.MUSHROOMS));
		dietProductCategories.add(getDietProductCategory(dietType, DietProductCategoryType.NUTS));
		dietProductCategories.add(getDietProductCategory(dietType, DietProductCategoryType.OIL_AND_BUTTER));
		dietProductCategories.add(getDietProductCategory(dietType, DietProductCategoryType.SAUCES));
		dietProductCategories.add(getDietProductCategory(dietType, DietProductCategoryType.SOFT_DRINKS));
		dietProductCategories.add(getDietProductCategory(dietType, DietProductCategoryType.SWEETS));
		dietProductCategories.add(getDietProductCategory(dietType, DietProductCategoryType.VEGETABLES));
		return dietProductCategories;
	}

	private DietProductCategory getDietProductCategory(
			DietType dietType,
			DietProductCategoryType dietProductCategoryType
	) {
		return new DietProductCategory(
				getMessage("diets." + dietProductCategoryType.getLocalizationFolder()),
				getMessage("diets." + dietProductCategoryType.getLocalizationFolder()
						+ "."  + dietType.getLocalizationSuffix() + ".restriction"),
				getMessage("diets." + dietProductCategoryType.getLocalizationFolder()
						+ "."  + dietType.getLocalizationSuffix() + ".note")
		);
	}

	private void addDietMetadataToModel(Model model, Diet diet, Locale locale) {
		String keywords = getDietMetaKeywords(diet, locale);
		String description = diet.getDescription().localization(locale);
		addMetadataToModel(model, diet.localization(locale), description, keywords);
	}

	private String getDietMetaKeywords(Diet diet, Locale locale) {
		StringBuilder keywords = new StringBuilder();
		fillMetaPart(keywords, diet.localization(locale) + " " + getMessage("meta.diet"));
		fillMetaPart(keywords,
				diet.localization(locale) +
						" " + getMessage("meta.diet") +
						" " + getMessage("meta.diet.ration")
		);
		fillMetaPart(keywords,
				diet.localization(locale) +
						" " + getMessage("meta.diet") +
						" " + getMessage("meta.diet.recipes")
		);
		return keywords.toString();
	}
}