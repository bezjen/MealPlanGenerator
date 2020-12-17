package com.bezjen.whattoeat.controller.admin.localized;

import java.util.HashMap;
import java.util.Locale;

import com.bezjen.whattoeat.service.localized.LocalizedEntityService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bezjen.whattoeat.entity.Measure;
import com.bezjen.whattoeat.entity.MeasureType;
import com.bezjen.whattoeat.item.PageItem;
import com.bezjen.whattoeat.item.PageType;
import com.bezjen.whattoeat.model.localized.MeasureModel;

@Controller
@RequestMapping(value = "/admin/measures")
public class LocalizedMeasureController extends LocalizedEntityController<Measure, MeasureModel> {
	public LocalizedMeasureController(
			MessageSource messageSource,
			LocalizedEntityService<Measure, MeasureModel> entityService
	) {
		super(messageSource, entityService);
	}

	@Override
	protected void fillSaveFormModelDefaultData(Model model, Locale locale) {
		model.addAttribute("measureTypes", MeasureType.values());
	}
	
	@Override
	protected void initPages() {
		pages = new HashMap<>(3);
		pages.put(PageType.GENERAL, new PageItem("general.pages.measures", 
				"admin/localizedentities/measures/showMeasures", "/admin/measures"));
		pages.put(PageType.ADD, new PageItem("general.pages.addMeasure", 
				"admin/localizedentities/measures/saveMeasure", "/admin/measures/add"));
		pages.put(PageType.EDIT, new PageItem("general.pages.editMeasure", 
				"admin/localizedentities/measures/saveMeasure", "/admin/measures/edit/"));
		pages.put(PageType.DELETE, new PageItem("/admin/measures/delete/"));
	}
}