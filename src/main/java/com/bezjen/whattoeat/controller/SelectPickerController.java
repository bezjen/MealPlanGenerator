package com.bezjen.whattoeat.controller;

import com.bezjen.whattoeat.entity.EntityType;
import com.bezjen.whattoeat.service.LocalizationService;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping(value = "/selectpicker")
public class SelectPickerController extends BaseController {
	private LocalizationService localizationService;

	public SelectPickerController(
			MessageSource messageSource,
			LocalizationService localizationService
	) {
		super(messageSource);
		this.localizationService = localizationService;
	}

	//TODO: remove "starting" or add both
	@PostMapping("/searchCookingMethods")
	@ResponseBody
	public String searchCookingMethods(@RequestParam("name") String cookingMethod, Locale locale) {
		return getJSONString(cookingMethod, EntityType.COOKING_METHOD, locale);
	}

	@PostMapping("/searchProducts")
	@ResponseBody
	public String searchFoodProduct(@RequestParam("name") String productName, Locale locale) {
		return getJSONString(productName, EntityType.FOOD_PRODUCT, locale);
	}

	@PostMapping("/searchMeasures")
	@ResponseBody
	public String searchMeasure(@RequestParam("name") String measure, Locale locale) {
		return getJSONString(measure, EntityType.MEASURE, locale);
	}
	
	@PostMapping("/searchDiets")
	@ResponseBody
	public String searchDiets(@RequestParam("name") String diet, Locale locale) {
		return getJSONString(diet, EntityType.DIET, locale);
	}

	@PostMapping("/searchFoodProductCategoryGroups")
	@ResponseBody
	public String searchFoodProductCategoryGroups(@RequestParam("name") String categoryGroup, Locale locale) {
		return getJSONString(categoryGroup, EntityType.FOOD_PRODUCT_CATEGORY_GROUP, locale);
	}

	@PostMapping("/searchFoodProductCategories")
	@ResponseBody
	public String searchFoodProductCategories(@RequestParam("name") String category, Locale locale) {
		return getJSONString(category, EntityType.FOOD_PRODUCT_CATEGORY, locale);
	}
	
	private String getJSONString(String entityLocalization, EntityType entityType, Locale locale) {
		Map<Long, String> localizations;
		if (StringUtils.isBlank(entityLocalization) && !EntityType.FOOD_PRODUCT.equals(entityType)) {
			localizations = localizationService.findAll(locale.getLanguage(), entityType);
		} else {
			localizations = localizationService.findAllSimilar(locale.getLanguage(), entityType, entityLocalization);
		}
		JSONArray result = new JSONArray();
		for (Long key : localizations.keySet()) {
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("value", localizations.get(key));
			values.put("id", key);
			JSONObject item = new JSONObject(values);
			result.add(item);
		}
		return result.toJSONString();
	}
}

