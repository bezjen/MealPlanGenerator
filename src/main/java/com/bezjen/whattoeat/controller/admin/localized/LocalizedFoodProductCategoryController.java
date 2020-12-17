package com.bezjen.whattoeat.controller.admin.localized;

import com.bezjen.whattoeat.entity.FoodProductCategory;
import com.bezjen.whattoeat.item.PageItem;
import com.bezjen.whattoeat.item.PageType;
import com.bezjen.whattoeat.model.localized.FoodProductCategoryModel;
import com.bezjen.whattoeat.repository.localized.FoodProductCategoryGroupRepository;

import com.bezjen.whattoeat.service.localized.LocalizedEntityService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Locale;

@Controller
@RequestMapping(value = "/admin/foodproducts/categories")
public class LocalizedFoodProductCategoryController
		extends LocalizedEntityController<FoodProductCategory, FoodProductCategoryModel> {
	private FoodProductCategoryGroupRepository foodProductCategoryGroupRepository;

	public LocalizedFoodProductCategoryController(
			MessageSource messageSource,
			LocalizedEntityService<FoodProductCategory, FoodProductCategoryModel> entityService,
			FoodProductCategoryGroupRepository foodProductCategoryGroupRepository
	) {
		super(messageSource, entityService);
		this.foodProductCategoryGroupRepository = foodProductCategoryGroupRepository;
	}

	@Override
	protected void fillSaveFormModelDefaultData(Model model, Locale locale) {
		model.addAttribute("categoryGroups", foodProductCategoryGroupRepository.findAll());
	}
	
	@Override
	@PostConstruct
	protected void initPages() {
		pages = new HashMap<>(3);
		pages.put(PageType.GENERAL, new PageItem(
						"general.pages.foodProducts.categories",
						"admin/localizedentities/foodproducts/categories/showCategories",
						"/admin/foodproducts/categories")
		);
		pages.put(PageType.ADD, new PageItem(
				"general.pages.foodProducts.categories.add",
				"admin/localizedentities/foodproducts/categories/saveCategory",
				"/admin/foodproducts/categories/add")
		);
		pages.put(PageType.EDIT, new PageItem(
				"general.pages.foodProducts.categories.edit",
				"admin/localizedentities/foodproducts/categories/saveCategory",
				"/admin/foodproducts/categories/edit/")
		);
		pages.put(PageType.DELETE, new PageItem("/admin/foodproducts/categories/delete/"));
	}
}