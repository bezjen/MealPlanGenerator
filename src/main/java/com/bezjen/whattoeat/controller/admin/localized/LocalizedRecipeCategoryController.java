package com.bezjen.whattoeat.controller.admin.localized;

import com.bezjen.whattoeat.entity.RecipeCategory;
import com.bezjen.whattoeat.item.PageItem;
import com.bezjen.whattoeat.item.PageType;
import com.bezjen.whattoeat.model.localized.RecipeCategoryModel;
import com.bezjen.whattoeat.service.localized.LocalizedEntityService;
import com.bezjen.whattoeat.service.localized.RecipeCategoryService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin/recipes/categories")
public class LocalizedRecipeCategoryController extends LocalizedEntityController<RecipeCategory, RecipeCategoryModel> {
	private RecipeCategoryService recipeCategoryService;

	public LocalizedRecipeCategoryController(
			MessageSource messageSource,
			LocalizedEntityService<RecipeCategory, RecipeCategoryModel> entityService,
			RecipeCategoryService recipeCategoryService
	) {
		super(messageSource, entityService);
		this.recipeCategoryService = recipeCategoryService;
	}

	@Override
	protected void fillSaveFormModelDefaultData(Model model, Locale locale) {
		Map<RecipeCategory, List<RecipeCategory>> recipeCategories =
				recipeCategoryService.findAllGroupedByCategory(locale);
		model.addAttribute("parentCategories", recipeCategories.get(null));
		recipeCategories.remove(null);
		model.addAttribute("categoryGroups", recipeCategories);
	}
	
	@Override
	@PostConstruct
	protected void initPages() {
		pages = new HashMap<>(3);
		pages.put(PageType.GENERAL, new PageItem(
				"general.pages.recipes.categories",
				"admin/localizedentities/recipes/categories/showCategories",
				"/admin/recipes/categories")
		);
		pages.put(PageType.ADD, new PageItem(
				"general.pages.recipes.categories.add",
				"admin/localizedentities/recipes/categories/saveCategory",
				"/admin/recipes/categories/add")
		);
		pages.put(PageType.EDIT, new PageItem(
				"general.pages.recipes.categories.edit",
				"admin/localizedentities/recipes/categories/saveCategory",
				"/admin/recipes/categories/edit/")
		);
		pages.put(PageType.DELETE, new PageItem("/admin/recipes/categories/delete/"));
	}
}