package com.bezjen.whattoeat.controller.admin.localized;

import java.util.HashMap;

import com.bezjen.whattoeat.service.localized.LocalizedEntityService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bezjen.whattoeat.entity.FoodProductCategoryGroup;
import com.bezjen.whattoeat.item.PageItem;
import com.bezjen.whattoeat.item.PageType;
import com.bezjen.whattoeat.model.localized.LocalizedEntityModel;

@Controller
@RequestMapping(value = "/admin/foodproducts/categorygroups")
public class LocalizedFoodProductCategoryGroupController
		extends LocalizedEntityController<FoodProductCategoryGroup, LocalizedEntityModel> {
	public LocalizedFoodProductCategoryGroupController(
			MessageSource messageSource,
			LocalizedEntityService<FoodProductCategoryGroup, LocalizedEntityModel> entityService
	) {
		super(messageSource, entityService);
	}

	@Override
	protected void initPages() {
		pages = new HashMap<>(3);
		pages.put(PageType.GENERAL, new PageItem(
				"general.pages.foodProducts.categoryGroups",
				"admin/localizedentities/showSimpleLocalizedEntities",
				"/admin/foodproducts/categorygroups")
		);
		pages.put(PageType.ADD, new PageItem(
				"general.pages.foodProducts.categoryGroups.add",
				"admin/localizedentities/saveSimpleLocalizedEntity",
				"/admin/foodproducts/categorygroups/add")
		);
		pages.put(PageType.EDIT, new PageItem(
				"general.pages.foodProducts.categoryGroups.edit",
				"admin/localizedentities/saveSimpleLocalizedEntity",
				"/admin/foodproducts/categorygroups/edit/")
		);
		pages.put(PageType.DELETE, new PageItem("/admin/foodproducts/categorygroups/delete/"));
	}
}