package com.bezjen.whattoeat.controller;

import com.bezjen.whattoeat.service.localized.FoodProductService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

@Controller
@RequestMapping(value = "/foodproducts")
public class FoodProductController extends BaseController {
	private FoodProductService foodProductService;

	public FoodProductController(
			MessageSource messageSource,
			FoodProductService foodProductService
	) {
		super(messageSource);
		this.foodProductService = foodProductService;
	}

	@GetMapping("")
	public String showAll(Model model, Locale locale) {
		addMetadataToModel(model, getMessage("general.pages.foodProducts"), "", null);
		model.addAttribute(
				"foodProducts", foodProductService.findAllGroupedByCategoryThenGroupedByCategoryGroup(locale));
		return "foodproducts/showFoodProducts";
	}

}

