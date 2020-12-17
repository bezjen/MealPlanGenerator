package com.bezjen.whattoeat.controller.admin.localized;

import java.util.HashMap;
import java.util.Locale;

import javax.annotation.PostConstruct;

import com.bezjen.whattoeat.service.localized.LocalizedEntityService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bezjen.whattoeat.entity.FoodProduct;
import com.bezjen.whattoeat.item.PageItem;
import com.bezjen.whattoeat.item.PageType;
import com.bezjen.whattoeat.model.localized.FoodProductModel;
import com.bezjen.whattoeat.service.localized.FoodProductCategoryService;

@Controller
@RequestMapping(value = "/admin/foodproducts")
public class LocalizedFoodProductController extends LocalizedEntityController<FoodProduct, FoodProductModel> {
	private FoodProductCategoryService foodProductCategoryService;

	public LocalizedFoodProductController(
			MessageSource messageSource,
			LocalizedEntityService<FoodProduct, FoodProductModel> entityService,
			FoodProductCategoryService foodProductCategoryService) {
		super(messageSource, entityService);
		this.foodProductCategoryService = foodProductCategoryService;
	}

	@Override
	protected void fillSaveFormModelDefaultData(Model model, Locale locale) {
		model.addAttribute("categories", foodProductCategoryService.findAllGroupedByCategory(locale));
	}
	
	@Override
	@PostConstruct
	protected void initPages() {
		pages = new HashMap<>(3);
		pages.put(PageType.GENERAL, new PageItem("general.pages.foodProducts", 
				"admin/localizedentities/foodproducts/showFoodProducts", "/admin/foodproducts"));
		pages.put(PageType.ADD, new PageItem("general.pages.addFoodProduct", 
				"admin/localizedentities/foodproducts/saveFoodProduct", "/admin/foodproducts/add"));
		pages.put(PageType.EDIT, new PageItem("general.pages.editFoodProduct", 
				"admin/localizedentities/foodproducts/saveFoodProduct", "/admin/foodproducts/edit/"));
		pages.put(PageType.DELETE, new PageItem("/admin/foodproducts/delete/"));
	}
}