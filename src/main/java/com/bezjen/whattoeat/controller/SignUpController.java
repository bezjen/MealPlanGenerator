package com.bezjen.whattoeat.controller;

import javax.servlet.http.HttpServletRequest;

import com.bezjen.whattoeat.entity.VerificationToken;
import com.bezjen.whattoeat.event.OnRegistrationCompleteEvent;
import com.bezjen.whattoeat.item.MessageType;
import com.bezjen.whattoeat.model.SignUpEmailModel;
import com.bezjen.whattoeat.model.SignUpUsernameModel;
import com.bezjen.whattoeat.service.VerificationTokenService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bezjen.whattoeat.entity.User;
import com.bezjen.whattoeat.exception.AjaxValidationException;
import com.bezjen.whattoeat.model.SignupModel;
import com.bezjen.whattoeat.repository.UserRepository;
import com.bezjen.whattoeat.service.UserService;

@Controller
@RequestMapping(value = "/signup")
public class SignUpController extends BaseController {
	private UserService userService;
	private VerificationTokenService verificationTokenService;
	private UserRepository userRepository;
	private ApplicationEventPublisher applicationEventPublisher;
	private SessionRegistry sessionRegistry;

	public SignUpController(
			MessageSource messageSource,
			UserService userService,
			VerificationTokenService verificationTokenService,
			UserRepository userRepository,
			ApplicationEventPublisher applicationEventPublisher,
			SessionRegistry sessionRegistry
	) {
		super(messageSource);
		this.userService = userService;
		this.verificationTokenService = verificationTokenService;
		this.userRepository = userRepository;
		this.applicationEventPublisher = applicationEventPublisher;
		this.sessionRegistry = sessionRegistry;
	}

	@GetMapping("")
    public String signup(Model model) {
		addMetadataToModel(model, getMessage("general.pages.authorization"), "", null);
		model.addAttribute("activeTab", "#signup-tab-general");
        return "home/login";
    }
	
	@PostMapping("")
    public String signup(@Validated @ModelAttribute("model") SignupModel signupModel,
			BindingResult result, Model model, HttpServletRequest request) {
		addMetadataToModel(model, getMessage("general.pages.authorization"), "", null);
		try {
			model.addAttribute("activeTab", "#signup-tab-general");
	        if (result.hasErrors()) {
	        	logger.error("BindingResult has validation errors");
				addMessageToModel(model, MessageType.ERROR, "messages.error.unexpected");
	            return "home/login";
	        }
	        if (!signupModel.getPassword().equals(signupModel.getPasswordConfirm())) {
				addMessageToModel(model, MessageType.ERROR, "messages.error.signup.passwords.match");
	            return "home/login";
	        }
	        User user = new User(signupModel.getUsername(), signupModel.getEmail(), signupModel.getPassword());
	        if (!userService.saveUser(user)){
				addMessageToModel(model, MessageType.ERROR, "messages.error.signup.username.exists");
	            return "home/login";
	        }
            request.login(signupModel.getUsername(), signupModel.getPassword());
			applicationEventPublisher.publishEvent(
					new OnRegistrationCompleteEvent(user, request.getLocale(), request.getServerName())
			);
        } catch (Throwable t) {
        	logger.error("", t);
			addMessageToModel(model, MessageType.ERROR, "messages.error.unexpected");
            return "home/login";
        }

        return "redirect:/";
    }

	@GetMapping("/confirm")
	public String signupConfirm(Model model, @RequestParam("token") String token) {
		addMetadataToModel(model, getMessage("general.pages.signup.confirm"), "", null);
		VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
		if (verificationToken == null) {
			addMessageToModel(model, MessageType.ERROR, "messages.signup.confirm.token.invalid");
			return "home/signupConfirm";
		}
		User user = verificationToken.getUser();
		if (user.isEmailConfirmed()) {
			addMessageToModel(model, MessageType.INFO, "messages.signup.confirm.already");
			return "home/signupConfirm";
		}
		if (verificationToken.getExpiryDate().getTime() < System.currentTimeMillis()) {
			addMessageToModel(model, MessageType.ERROR, "messages.signup.confirm.token.expired");
			return "home/signupConfirm";
		}

		user.setEmailConfirmed(true);
		getUser().setEmailConfirmed(true);
		userService.updateUser(user);
		for (Object principal : sessionRegistry.getAllPrincipals()) {
			if (principal instanceof User) {
				User currentUser = (User) principal;
				if (user.getId().equals(currentUser.getId())) {
					currentUser.setEmailConfirmed(true);
				}
			}
		}
		addMessageToModel(model, MessageType.INFO, "messages.signup.confirm.success");
		return "home/signupConfirm";
	}
	
	@PostMapping("/validateUsername")
	@ResponseBody
	public void validateUsername(
			@Validated SignUpUsernameModel model,
			BindingResult result) {
		if (result.hasErrors()) {
			ObjectError objectError = result.getAllErrors().get(0);
			throw new AjaxValidationException(objectError.getDefaultMessage());
		}
		if (userRepository.existsByUsernameIgnoreCase(model.getInputValue())) {
			throw new AjaxValidationException(getMessage("messages.error.signup.username.exists"));
		}
	}
	
	@PostMapping("/validateEmail")
	@ResponseBody
	public void validateEmail(
			@Validated SignUpEmailModel model,
			BindingResult result) {
		if (result.hasErrors()) {
			ObjectError objectError = result.getAllErrors().get(0);
			throw new AjaxValidationException(objectError.getDefaultMessage());
		}
		if (userRepository.existsByEmailIgnoreCase(model.getInputValue())) {
			throw new AjaxValidationException(getMessage("messages.error.signup.email.exists"));
		}
	}
}