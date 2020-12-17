package com.bezjen.whattoeat.controller.admin;

import com.bezjen.whattoeat.controller.BaseController;
import com.bezjen.whattoeat.repository.UserRepository;
import com.bezjen.whattoeat.service.UserService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Controller
@RequestMapping(value = "/admin/users")
public class UsersController extends BaseController {
	private UserRepository userRepository;
	private UserService userService;

	public UsersController(
			MessageSource messageSource,
			UserService userService,
			UserRepository userRepository
	) {
		super(messageSource);
		this.userService = userService;
		this.userRepository = userRepository;
	}

	@GetMapping("")
	public String showAll(Model model, Locale locale) {
		addMetadataToModel(model, getMessage("general.pages.users"), "", null);
		model.addAttribute("users", userRepository.findAll());
		return "admin/users/showAll";
	}

	@GetMapping("/block/{id}")
	public String block(@PathVariable("id") Long userId, Model model) {
		addMetadataToModel(model, getMessage("general.pages.users"), "", null);
		model.addAttribute("users", userRepository.findAll());
		userService.setUserBlock(userId, true);
		return "admin/users/showAll";
	}

	@GetMapping("/unblock/{id}")
	public String unblock(@PathVariable("id") Long userId, Model model) {
		addMetadataToModel(model, getMessage("general.pages.users"), "", null);
		model.addAttribute("users", userRepository.findAll());
		userService.setUserBlock(userId, false);
		return "admin/users/showAll";
	}
}