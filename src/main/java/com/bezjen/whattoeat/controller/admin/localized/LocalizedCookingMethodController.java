package com.bezjen.whattoeat.controller.admin.localized;

import java.util.HashMap;

import com.bezjen.whattoeat.service.localized.LocalizedEntityService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bezjen.whattoeat.entity.CookingMethod;
import com.bezjen.whattoeat.item.PageItem;
import com.bezjen.whattoeat.item.PageType;
import com.bezjen.whattoeat.model.localized.LocalizedEntityModel;

@Controller
@RequestMapping(value = "/admin/cookingmethods")
public class LocalizedCookingMethodController extends LocalizedEntityController<CookingMethod, LocalizedEntityModel> {
	public LocalizedCookingMethodController(
			MessageSource messageSource,
			LocalizedEntityService<CookingMethod, LocalizedEntityModel> entityService
	) {
		super(messageSource, entityService);
	}

	@Override
	protected void initPages() {
		pages = new HashMap<>(3);
		pages.put(PageType.GENERAL, new PageItem("general.pages.cookingMethods", 
				"admin/localizedentities/showSimpleLocalizedEntities", "/admin/cookingmethods"));
		pages.put(PageType.ADD, new PageItem("general.pages.addCookingMethod", 
				"admin/localizedentities/saveSimpleLocalizedEntity", "/admin/cookingmethods/add"));
		pages.put(PageType.EDIT, new PageItem("general.pages.editCookingMethod", 
				"admin/localizedentities/saveSimpleLocalizedEntity", "/admin/cookingmethods/edit/"));
		pages.put(PageType.DELETE, new PageItem("/admin/cookingmethods/delete/"));
	}
}