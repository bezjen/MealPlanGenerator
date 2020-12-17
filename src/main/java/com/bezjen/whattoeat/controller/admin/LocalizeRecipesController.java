package com.bezjen.whattoeat.controller.admin;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bezjen.whattoeat.controller.BaseController;
import com.bezjen.whattoeat.entity.Recipe;
import com.bezjen.whattoeat.item.MessageType;
import com.bezjen.whattoeat.model.LocalizeRecipeModel;
import com.bezjen.whattoeat.repository.localized.RecipeRepository;
import com.bezjen.whattoeat.service.LocalizeRecipeService;

@Controller
@RequestMapping(value = "/admin/recipes")
public class LocalizeRecipesController extends BaseController {
	private RecipeRepository recipeRepository;
	private LocalizeRecipeService localizeRecipeService;

	public LocalizeRecipesController(
			MessageSource messageSource,
			LocalizeRecipeService localizeRecipeService,
			RecipeRepository recipeRepository
	) {
		super(messageSource);
		this.localizeRecipeService = localizeRecipeService;
		this.recipeRepository = recipeRepository;
	}

	@GetMapping("/localization")
	public String recipes(Model model, Locale locale) {
		addMetadataToModel(model, getMessage("general.pages.recipes.localizeRecipes"), "", null);
		model.addAttribute("recipes", recipeRepository.findByEnLocaleIsNullOrderByDateDesc());
		return "admin/localization/showAll";
	}
	
	@GetMapping("/localization/{id}")
	public String localizeRu(@PathVariable("id") Long id, Model model, Locale locale) {	//TODO: metadata
		addMetadataToModel(model, getMessage("general.pages.recipes.localizeRecipe"), "", null);
		Recipe recipe = recipeRepository.findById(Long.valueOf(id)).get();
		model.addAttribute("recipe", recipe);
		return "admin/localization/setRu";
	}
	
	@PostMapping("/localization/{id}")
	public String localizeRu(@PathVariable("id") Long id ,
							 @Valid @ModelAttribute("recipe") LocalizeRecipeModel localizeRecipeModel,
							 BindingResult result,
							 Model model,
							 Locale locale) {	//TODO: metadata
		try {
			addMetadataToModel(model, getMessage("general.pages.addRecipe"), "", null);
			if (result.hasErrors()) {	// && (!result.hasFieldErrors("recipeImage") || result.getErrorCount() > 1)
				model.addAttribute(result);
				addMessageToModel(model, MessageType.ERROR, "messages.error.checkfields");
				return "admin/localization/setRu";
			}
			
			localizeRecipeService.localizeRecipeRu(id, localizeRecipeModel);
        } catch (Throwable t) {
        	logger.error("", t);
			addMessageToModel(model, MessageType.ERROR, "messages.error.unexpected");
            return "admin/localization/setRu";
        }
		
		return "redirect:/admin/recipes/localization";
	}
}