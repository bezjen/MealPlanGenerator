package com.bezjen.whattoeat.controller.admin.localized;

import java.util.HashMap;

import com.bezjen.whattoeat.model.localized.DietModel;
import com.bezjen.whattoeat.service.localized.LocalizedEntityService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bezjen.whattoeat.entity.Diet;
import com.bezjen.whattoeat.item.PageItem;
import com.bezjen.whattoeat.item.PageType;
import com.bezjen.whattoeat.model.localized.LocalizedEntityModel;

@Controller
@RequestMapping(value = "/admin/diets")
public class LocalizedDietController extends LocalizedEntityController<Diet, DietModel> {
	public LocalizedDietController(
			MessageSource messageSource,
			LocalizedEntityService<Diet, DietModel> entityService
	) {
		super(messageSource, entityService);
	}

	@Override
	protected void initPages() {
		pages = new HashMap<>(3);
		pages.put(PageType.GENERAL, new PageItem("general.pages.diets", 
				"admin/localizedentities/diets/showDiets", "/admin/diets"));
		pages.put(PageType.ADD, new PageItem("general.pages.addDiet", 
				"admin/localizedentities/diets/saveDiet", "/admin/diets/add"));
		pages.put(PageType.EDIT, new PageItem("general.pages.editDiet", 
				"admin/localizedentities/diets/saveDiet", "/admin/diets/edit/"));
		pages.put(PageType.DELETE, new PageItem("/admin/diets/delete/"));
	}
}