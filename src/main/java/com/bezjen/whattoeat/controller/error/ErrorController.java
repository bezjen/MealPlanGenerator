package com.bezjen.whattoeat.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ErrorController {
	@GetMapping("/errors/404")
	public String error404(Model model, HttpServletRequest request, HttpServletResponse response) {
		return "errors/404";
	}

	@GetMapping("/errors/unexpected")
	public String error500(Model model, HttpServletRequest request, HttpServletResponse response) {
		return "errors/unexpected";
	}
}