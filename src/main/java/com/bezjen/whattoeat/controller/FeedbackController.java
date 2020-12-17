package com.bezjen.whattoeat.controller;

import java.net.URI;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.bezjen.whattoeat.item.MessageType;
import com.bezjen.whattoeat.service.EmailSenderService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bezjen.whattoeat.exception.AjaxValidationException;
import com.bezjen.whattoeat.model.FeedbackModel;

@Controller
public class FeedbackController extends BaseController {
    private EmailSenderService emailSenderService;

	public FeedbackController(
			MessageSource messageSource,
			EmailSenderService emailSenderService
	) {
		super(messageSource);
		this.emailSenderService = emailSenderService;
	}

	//	@GetMapping("/feedback")
	public String feedback(Model model) {
		model.addAttribute("feedback", new FeedbackModel());
		addMetadataToModel(model, getMessage("general.pages.feedback"), "", null);
		return "feedback/feedback";
	}
	
//	@PostMapping("/feedback")
	public String feedback(
			@Valid @ModelAttribute("feedback") FeedbackModel feedbackModel,
			BindingResult result,
			Model model
	) {
		try {
			addMetadataToModel(model, getMessage("general.pages.feedback"), "", null);
			Date sentDate = new Date();
			int feedBackNumber = ThreadLocalRandom.current().nextInt(1000, 9000 + 1);
			String subject = "WhatToEat [" + feedBackNumber + "]";
			String userMessage = "Subject: \n"
					+ feedbackModel.getSubject()
					+ "\n\nMessage: \n"
					+ feedbackModel.getMessage();

			emailSenderService.sendSimpleMailMessage(
					emailSenderService.getFrom(),
					subject,
					"Author: \n"
							+ feedbackModel.getName()
							+ "(" + feedbackModel.getEmail() + ")\n\n"
							+ userMessage,
					sentDate
			);
			emailSenderService.sendSimpleMailMessage(
					feedbackModel.getEmail(),
					subject,
					"Your feedback: \n" + userMessage + "\n\nThis is automatic message. Do not reply.",
					sentDate
			);

			addMessageToModel(model, MessageType.INFO, "messages.success.feedback.sent");
		} catch (Throwable t) {
			logger.error("", t);
			addMessageToModel(model, MessageType.ERROR, "messages.error.feedback");
		}

	    
		return "feedback/feedback";
	}
	
	@PostMapping("/feedback/validateCaptcha")
	@ResponseBody
	public void validateUsername(
			@RequestParam("token") String token, HttpServletRequest request) {
	}
}