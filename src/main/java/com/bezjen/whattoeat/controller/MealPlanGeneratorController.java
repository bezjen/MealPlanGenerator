package com.bezjen.whattoeat.controller;

import java.util.ArrayList;
import java.util.Locale;

import javax.validation.Valid;

import com.bezjen.whattoeat.item.MessageType;
import com.bezjen.whattoeat.service.localized.DietService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bezjen.whattoeat.model.filter.GeneratorFilterModel;
import com.bezjen.whattoeat.model.filter.ReplaceMealModel;
import com.bezjen.whattoeat.model.ShoppingListModel;
import com.bezjen.whattoeat.service.MealPlanGeneratorService;
import com.bezjen.whattoeat.service.localized.CookingMethodService;

@Controller
@RequestMapping(value = "/generator")
public class MealPlanGeneratorController extends BaseController {
	private MealPlanGeneratorService mealPlanGeneratorService;
	private CookingMethodService cookingMethodService;
	private DietService dietService;

	private final int MEALS_NUMBER_DEFAULT = 3;
	private final int DAYS_AMOUNT_DEFAULT = 1;
	private final int FOOD_DIVERSITY_DEFAULT = 7;

	public MealPlanGeneratorController(
			MessageSource messageSource,
			MealPlanGeneratorService mealPlanGeneratorService,
			CookingMethodService cookingMethodService,
			DietService dietService
	) {
		super(messageSource);
		this.mealPlanGeneratorService = mealPlanGeneratorService;
		this.cookingMethodService = cookingMethodService;
		this.dietService = dietService;
	}

	@GetMapping("")
	public String generate(Model model)  {
		addMetadataToModel(
				model,
				getMessage("general.pages.mealplangenerator"),
				getMessage("home.generator") + getMessage("home.generator.shoppingList"),
				getMessage("meta.recipe") + ", "
						+ getMessage("meta.whattoeat") + ", "
						+ getMessage("general.pages.mealplangenerator")
		);
		GeneratorFilterModel generatorFilterModel = new GeneratorFilterModel();
		generatorFilterModel.setMealsNumber(MEALS_NUMBER_DEFAULT);
		generatorFilterModel.setDaysAmount(DAYS_AMOUNT_DEFAULT);
		generatorFilterModel.setFoodDiversity(FOOD_DIVERSITY_DEFAULT);
		model.addAttribute("filter", generatorFilterModel);
		model.addAttribute("generatedMeal", new ArrayList<>());
		model.addAttribute("cookingMethods", cookingMethodService.findAll());
		model.addAttribute("diets", dietService.findAll());
		return "generator/generator";
	}
	
	@PostMapping("/generate")
	public String generate(@Valid @ModelAttribute("filter") GeneratorFilterModel generatorFilterModel,
            BindingResult result, Model model, Locale locale)  {
		addMetadataToModel(model, getMessage("general.pages.mealplangenerator"), "", null);
		if (result.hasErrors()) {
			model.addAttribute(result);
			return "fragments/generator :: generatorFields";
		}
		try {
			model.addAttribute(
					"generatedMeals", mealPlanGeneratorService.generateMealPlan(locale, generatorFilterModel));
		} catch (Exception e) {
			logger.error("", e);
			addMessageToModel(model, MessageType.WARNING, "messages.warning.generator.generate");
		}
		return "fragments/generator :: generatorResult";
	}
	
	@PostMapping("/replace")
	public String replaceMeal(@Valid @ModelAttribute("filter") ReplaceMealModel replaceMealModel,
            BindingResult result, Model model, Locale locale)  {
		model.addAttribute("index", replaceMealModel.getIndex());
		model.addAttribute("recipe", mealPlanGeneratorService.getRandomMeal(locale, replaceMealModel));
		return "fragments/generator :: generatedMeal";
	}
	
	@PostMapping("/shoppingList")
	public String shoppingList(@Valid @ModelAttribute("model") ShoppingListModel shoppingListModel,
            BindingResult result, Model model)  {
		model.addAttribute("shoppingList", mealPlanGeneratorService.getShoppingList(shoppingListModel));
		return "fragments/generator :: shoppingList";
	}
	
}