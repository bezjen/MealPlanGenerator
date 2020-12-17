package com.bezjen.whattoeat.controller;

import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.bezjen.whattoeat.entity.Recipe;
import com.bezjen.whattoeat.entity.User;
import com.bezjen.whattoeat.item.MessageType;
import com.bezjen.whattoeat.model.UpdateProfileSettingsModel;
import com.bezjen.whattoeat.repository.localized.RecipeRepository;
import com.bezjen.whattoeat.service.EmailConfirmationService;
import com.bezjen.whattoeat.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/account")
public class AccountController extends BaseController {
	protected UserService userService;
	protected EmailConfirmationService emailConfirmationService;
	protected RecipeRepository recipeRepository;

	public AccountController(
			MessageSource messageSource,
			UserService userService,
			EmailConfirmationService emailConfirmationService,
			RecipeRepository recipeRepository
	) {
		super(messageSource);
		this.userService = userService;
		this.emailConfirmationService = emailConfirmationService;
		this.recipeRepository = recipeRepository;
	}

	@GetMapping("")
	public String account() {
		return "redirect:/account/settings";
	}

	@GetMapping("/settings")
	public String settings(Model model) {
		addMetadataToModel(model, getMessage("general.pages.account.settings"), "", null);
		model.addAttribute("user", getUser());
		return "account/settings";
	}

	@GetMapping("/confirm")
	public String confirm(Model model, HttpServletRequest request) {
		try {
			emailConfirmationService.reSendConfirmationEmail(getUser(), request.getServerName(), request.getLocale());
		} catch (MessagingException e) {
			logger.error("", e);
		}
		return "redirect:/account/settings";
	}

	@PostMapping("/settings")
	public String updateSettings(@Valid @ModelAttribute("model") UpdateProfileSettingsModel updateProfileSettingsModel,
			BindingResult result, Model model) {
		User user = getUser();
		addMetadataToModel(model, getMessage("general.pages.account.settings"), "", null);
		try {
			if (StringUtils.isNotBlank(updateProfileSettingsModel.getPassword())){
				if (updateProfileSettingsModel.getPassword().length() < 5) {
					 addMessageToModel(model, MessageType.ERROR, "messages.error.account.settings.password.length");
				}
				if (!updateProfileSettingsModel.getPassword().equals(updateProfileSettingsModel.getPasswordConfirm())) {
					addMessageToModel(model, MessageType.ERROR, "messages.error.account.settings.password.match");
				}
				model.addAttribute("user", user);
	            return "account/settings";
	        }
			userService.updateUser(updateProfileSettingsModel);
			addMessageToModel(model, MessageType.INFO, "messages.success.account.settings");
		} catch (Exception e) {
			logger.error("", e);
			addMessageToModel(model, MessageType.ERROR, "messages.error.account.settings");
		}
		model.addAttribute("user", user);
		return "account/settings";
	}

	@GetMapping("/recipes")
	public String recipes(Model model) {
		addMetadataToModel(model, getMessage("general.pages.account.recipes"), "", null);
		User user = getUser();
		List<Recipe> recipes = recipeRepository.findByAuthorIdOrderByDateDesc(user.getId());
		model.addAttribute("user", user);
		model.addAttribute("recipes", recipes);
		return "account/recipes";
	}
}