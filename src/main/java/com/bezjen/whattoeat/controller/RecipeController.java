package com.bezjen.whattoeat.controller;

import java.util.*;

import javax.validation.Valid;

import com.bezjen.whattoeat.item.MessageType;

import com.bezjen.whattoeat.service.localized.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bezjen.whattoeat.entity.*;
import com.bezjen.whattoeat.exception.LocalizedException;
import com.bezjen.whattoeat.model.AddRecipeModel;
import com.bezjen.whattoeat.model.filter.PaginatedRecipesFilterModel;
import com.bezjen.whattoeat.repository.localized.RecipeRepository;
import com.bezjen.whattoeat.repository.UserRepository;
import com.bezjen.whattoeat.service.FoodEnergyService;
import com.bezjen.whattoeat.service.RecipeService;

@Controller
@RequestMapping(value = "/recipes")
public class RecipeController extends BaseController {
	protected RecipeService recipeService;
	private FoodProductService foodProductService;
	private CookingMethodService cookingMethodService;
	private MeasureService measureService;
	private FoodEnergyService foodEnergyService;
	private RecipeCategoryService recipeCategoryService;
	private DietService dietService;
	protected RecipeRepository recipeRepository;
	private UserRepository userRepository;

	public RecipeController(
			MessageSource messageSource,
			RecipeService recipeService,
			FoodProductService foodProductService,
			CookingMethodService cookingMethodService,
			MeasureService measureService,
			FoodEnergyService foodEnergyService,
			RecipeCategoryService recipeCategoryService,
			DietService dietService,
			RecipeRepository recipeRepository,
			UserRepository userRepository
	) {
		super(messageSource);
		this.recipeService = recipeService;
		this.foodProductService = foodProductService;
		this.cookingMethodService = cookingMethodService;
		this.measureService = measureService;
		this.foodEnergyService = foodEnergyService;
		this.recipeCategoryService = recipeCategoryService;
		this.dietService = dietService;
		this.recipeRepository = recipeRepository;
		this.userRepository = userRepository;
	}

	@GetMapping("")
	public String showAll(Model model, Locale locale) {
		addMetadataToModel(model, getMessage("general.pages.recipes"), "", null);
		List<Recipe> recipes = recipeService.getRecipesList(locale, 0);
		model.addAttribute("recipes", recipes);
		model.addAttribute("pages", recipeService.getPagesCount());
		model.addAttribute("currentPage", 1);
		model.addAttribute("cookingMethods", cookingMethodService.findAll());
		model.addAttribute("diets", dietService.findAll());
		return "recipes/showRecipes";
	}

	@GetMapping("/{id}")
	public String showPage(@PathVariable("id") Integer id, Model model, Locale locale) {
		addMetadataToModel(model, getMessage("general.pages.recipes"), "", null);
		List<Recipe> recipes = recipeService.getRecipesList(locale, id - 1);
		model.addAttribute("recipes", recipes);
		model.addAttribute("pages", recipeService.getPagesCount());
		model.addAttribute("currentPage", id);
		model.addAttribute("cookingMethods", cookingMethodService.findAll());
		model.addAttribute("diets", dietService.findAll());
		return "recipes/showRecipes";
	}

	@PostMapping("/applyFilters")
	public String applyFilters(@Valid @ModelAttribute("model") PaginatedRecipesFilterModel recipesFilterModel,
							  BindingResult result, Model model, Locale locale)  {
		List<Recipe> recipes = recipeService.getFilteredRecipesList(locale, recipesFilterModel);
		model.addAttribute("recipes", recipes);
		model.addAttribute("pages", recipeService.getFilteredPagesCount(locale, recipesFilterModel));
		model.addAttribute("currentPage", recipesFilterModel.getPage());
		return "fragments/showRecipes :: recipesSection";
	}

	@RequestMapping(value = "/show/{id}", method = {RequestMethod.POST, RequestMethod.GET})
	public String showRecipe(@PathVariable("id") Long id, Model model, Locale locale) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(id);
		if (!recipeOptional.isPresent()) {
			return "redirect:/errors/404";
		}
		Recipe recipe = recipeOptional.get();
		if (!recipe.isApproved() || StringUtils.isBlank(recipe.localization(locale))) {
			return "redirect:/errors/404";
		}
		//TODO: removed sorting by locale (think about it, may be we have to sort by amount)
		//recipe.getRecipeIngredients().sort(Comparator.comparing(i -> i.getFoodProduct().localization(locale)));
		recipe.getRecipeIngredients().sort(
				Comparator.comparing(i -> "to taste".equals(i.getMeasure().localization(Locale.ENGLISH)))
		);
		addRecipeMetadataToModel(model, recipe, locale);
		model.addAttribute("recipe", recipe);
		model.addAttribute("foodEnergy", foodEnergyService.getFoodEnergy(recipe.getRecipeIngredients()));
		return "recipes/showRecipe";
	}

	@GetMapping("/add")
	public String addRecipe(Model model, Locale locale) {
		AddRecipeModel addRecipeModel = new AddRecipeModel();
		addRecipeModel.setServingsNumber(1);
		addMetadataToModel(model, getMessage("general.pages.addRecipe"), "", null);
		model.addAttribute("recipe", addRecipeModel);
		model.addAttribute("cookingMethods", cookingMethodService.findAll());
		model.addAttribute("measures", measureService.findAll());
		model.addAttribute("foodProducts", foodProductService.findAllGroupedByCategory(locale));
		model.addAttribute("dietaryOptions", dietService.findAllSorted(locale));
		Map<RecipeCategory, List<RecipeCategory>> recipeCategories =
				recipeCategoryService.findAllGroupedByCategory(locale);
		model.addAttribute("parentCategories", recipeCategories.get(null));
		recipeCategories.remove(null);
		model.addAttribute("categoryGroups", recipeCategories);
		return "recipes/addRecipe";
	}

	@PostMapping("/add")
	public String addRecipe(@Valid @ModelAttribute("recipe") AddRecipeModel addRecipeModel,
							BindingResult result, Model model, Locale locale) {
		try {
			addMetadataToModel(model, getMessage("general.pages.addRecipe"), "", null);
			recipeService.validateRecipe(addRecipeModel);
			if (result.hasErrors()) {
				model.addAttribute(result);
				addMessageToModel(model, MessageType.ERROR, "messages.error.checkfields");
				return "fragments/general :: messages";
			}

			addRecipeModel.setLocale(locale);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user;
			if (authentication.getPrincipal() instanceof User) {
				user = (User)authentication.getPrincipal();
			} else {
				user = userRepository.findByUsernameIgnoreCase("whattoeat");
			}
			 
			recipeService.saveRecipe(addRecipeModel, user);
		} catch (LocalizedException e) {
        	logger.error("", e);
			addMessageToModel(model, MessageType.ERROR, e.getKey());
        } catch (Throwable t) {
        	logger.error("", t);
			addMessageToModel(model, MessageType.ERROR, "messages.error.unexpected");
        }
		
		return "fragments/general :: messages";
	}

	private void addRecipeMetadataToModel(Model model, Recipe recipe, Locale locale) {
		String keywords = getRecipeMetaKeywords(recipe, locale);
		String description;
		if (recipe.getDescription() == null) {
			description = keywords;
		} else {
			description = recipe.getDescription().localization(locale);
		}
		addMetadataToModel(model, recipe.localization(locale), description, keywords);
	}

	private String getRecipeMetaKeywords(Recipe recipe, Locale locale) {
		StringBuilder keywords = new StringBuilder();
		fillMetaPart(keywords, recipe.localization(locale));
		fillMetaPart(keywords, getMessage("meta.recipe"));
		fillMetaPart(keywords, getMessage("meta.whattoeat"));
		recipe.getRecipeIngredients().forEach(
				recipeIngredient -> fillMetaPart(keywords, recipeIngredient.getFoodProduct().localization(locale))
		);
		fillMetaPart(keywords, getMessage("meta.recipe.all"));
		fillMetaPart(keywords,
				getMessage("recipe.prepTime") + " " +
						recipe.getPrepTime() + " " +
						getMessage("recipe.minutes")
		);
		fillMetaPart(keywords,
				getMessage("recipe.cookingTime") + " " +
						recipe.getCookingTime() + " " +
						getMessage("recipe.minutes")
		);
		return keywords.toString();
	}
}

