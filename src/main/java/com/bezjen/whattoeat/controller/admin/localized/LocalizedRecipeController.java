package com.bezjen.whattoeat.controller.admin.localized;

import com.bezjen.whattoeat.entity.Recipe;
import com.bezjen.whattoeat.item.MessageType;
import com.bezjen.whattoeat.item.PageItem;
import com.bezjen.whattoeat.item.PageType;
import com.bezjen.whattoeat.model.localized.RecipeModel;
import com.bezjen.whattoeat.repository.localized.RecipeRepository;
import com.bezjen.whattoeat.service.localized.LocalizedEntityService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Locale;

@Controller
@RequestMapping(value = "/admin/recipes")
public class LocalizedRecipeController extends LocalizedEntityController<Recipe, RecipeModel> {
	private RecipeRepository recipeRepository;

	public LocalizedRecipeController(
			MessageSource messageSource,
			LocalizedEntityService<Recipe, RecipeModel> entityService,
			RecipeRepository recipeRepository
	) {
		super(messageSource, entityService);
		this.recipeRepository = recipeRepository;
	}

	@GetMapping("/approve/{id}")
	public String approve(@PathVariable("id") Long id, Model model, Locale locale) {
		try {
			Recipe recipe = entityService.findById(id).get();		//TODO: move to service
			recipe.setApproved(!recipe.isApproved());
			recipeRepository.save(recipe);
		} catch (Throwable t) {
			logger.error("", t);
			addMessageToModel(model, MessageType.ERROR, "messages.error.unexpected");
			return show(model, locale);
		}
		return "redirect:" + getGeneralPage().getLink();
	}

	@Override
	protected void initPages() {
		pages = new HashMap<>(4);
		pages.put(PageType.GENERAL, new PageItem("general.pages.recipes.categories",
				"admin/localizedentities/recipes/showRecipes", "/admin/recipes"));
		pages.put(PageType.ADD, new PageItem("general.pages.recipes.categories.add",
				"recipes/addRecipe", "/recipes/add"));
		pages.put(PageType.EDIT, new PageItem("general.pages.recipes.categories.edit",
				"recipes/addRecipe", "/recipes/add"));	//TODO: change fake path
		pages.put(PageType.DELETE, new PageItem("/admin/recipes/delete/"));
	}
}