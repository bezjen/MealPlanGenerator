package com.bezjen.whattoeat.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bezjen.whattoeat.entity.Diet;
import com.bezjen.whattoeat.entity.Recipe;
import com.bezjen.whattoeat.item.MessageType;
import com.bezjen.whattoeat.repository.localized.DietRepository;
import com.bezjen.whattoeat.repository.localized.RecipeRepository;
import com.bezjen.whattoeat.service.RecipeService;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController extends BaseController {
	private static final String SITEMAP_HOME_URL = "https://whattoeat.info/";
	private final RecipeService recipeService;
	private final RecipeRepository recipeRepository;
	private final DietRepository dietRepository;

	public HomeController(
			MessageSource messageSource,
			RecipeService recipeService,
			RecipeRepository recipeRepository,
			DietRepository dietRepository
	) {
		super(messageSource);
		this.recipeService = recipeService;
		this.recipeRepository = recipeRepository;
		this.dietRepository = dietRepository;
	}

	@GetMapping(value = {"/", "/index"})
	public String home(Model model, Locale locale) {
		addMetadataToModel(
				model,
				getMessage("general.pages.home"),
				getMessage("home.generator") + getMessage("home.aboutUs"),
				null
		);
		model.addAttribute("newestRecipes", recipeService.getNewestRecipes(locale));
		model.addAttribute("breakfastRecipes", recipeService.getBreakfastRecipes(locale));
		model.addAttribute("randomRecipe", recipeService.getRandomRecipe(locale));
		return "home/home";
	}

	@PostMapping("/randomRecipe")
	public String randomRecipe(Model model, Locale locale)  {
		model.addAttribute("randomRecipe", recipeService.getRandomRecipe(locale));
		return "fragments/home :: randomRecipe";
	}
	
	@RequestMapping("/signin")
    public String signin(Model model, HttpServletRequest request) {
		addMetadataToModel(model, getMessage("general.pages.authorization"), "", null);
		Object exception = request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		if (exception != null) {
			AuthenticationException authenticationException = (AuthenticationException) exception;
			if (authenticationException.getCause() != null
					&& authenticationException.getCause() instanceof LockedException) {
				addMessageToModel(model, MessageType.ERROR, "messages.error.signin.user.blocked");
			} else {
				addMessageToModel(model, MessageType.ERROR, "messages.error.signin.emailOrUsername");
			}
		}
        return "home/login";
    }

	//TODO: move to jaxb; refactoring (approved can be with only one lang version now)
	@RequestMapping("/sitemap.xml")
    public void sitemap(HttpServletResponse response) throws IOException {
		StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		fillSitemapRow(sb, "<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\"");
		fillSitemapRow(sb, "    xmlns:xhtml=\"http://www.w3.org/1999/xhtml\">");
		fillSitemapUrlWithoutAlternatives(sb, "");
		fillSitemapUrlWithAlternatives(sb, "");
		fillSitemapUrlWithoutAlternatives(sb, "generator");
		fillSitemapUrlWithAlternatives(sb, "generator");
		fillSitemapUrlWithoutAlternatives(sb, "recipes");
		fillSitemapUrlWithAlternatives(sb, "recipes");
		fillSitemapUrlWithoutAlternatives(sb, "foodproducts");
		fillSitemapUrlWithAlternatives(sb, "foodproducts");
		fillSitemapUrlWithoutAlternatives(sb, "diets");
		fillSitemapUrlWithAlternatives(sb, "diets");
		fillSitemapUrlWithoutAlternatives(sb, "recipes/add");
		fillSitemapUrlWithAlternatives(sb, "recipes/add");
		for(Diet diet : dietRepository.findAll()) {
			fillSitemapUrlWithoutAlternatives(sb, "diets/show/" + diet.getId());
			fillSitemapUrlWithAlternatives(sb, "diets/show/" + diet.getId());
		}
		for (Recipe recipe : recipeRepository.findByIsApprovedAndRuLocaleNotNullAndEnLocaleNotNullOrderByDateDesc(Boolean.TRUE)) {
			fillSitemapUrlWithoutAlternatives(sb, "recipes/show/" + recipe.getId());
			fillSitemapUrlWithAlternatives(sb, "recipes/show/" + recipe.getId());
		}
		for (Recipe recipe : recipeRepository.findByIsApprovedAndRuLocaleIsNullOrderByDateDesc(Boolean.TRUE)) {
			fillSitemapUrlWithoutAlternatives(sb, "en/recipes/show/" + recipe.getId());
		}
		for (Recipe r : recipeRepository.findByIsApprovedAndEnLocaleIsNullOrderByDateDesc(Boolean.TRUE)) {
			fillSitemapUrlWithoutAlternatives(sb, "ru/recipes/show/" + r.getId());
		}
		fillSitemapRow(sb, "</urlset>");
		InputStream in = new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));
		IOUtils.copy(in, response.getOutputStream());
	}
	
	private void fillSitemapRow(StringBuilder sb, String row) {
		sb.append(row).append("\n");
	}
	
	private void fillSitemapUrlWithAlternatives(StringBuilder sb, String url) {
		fillSitemapUrlWithAlternatives(sb, url, "en");
		fillSitemapUrlWithAlternatives(sb, url, "ru");
	}
	
	private void fillSitemapUrlWithoutAlternatives(StringBuilder sb, String url) {
		fillSitemapRow(sb, "    <url>");
		fillSitemapLoc(sb, url);
		fillSitemapRow(sb, "    </url>");
	}
	
	private void fillSitemapUrlWithAlternatives(StringBuilder sb, String url, String langCode) {
		fillSitemapRow(sb, "    <url>");
		fillSitemapLoc(sb, langCode + "/" + url);
		fillSitemapLocAlternatives(sb, url);
		fillSitemapRow(sb, "    </url>");
	}
	
	private void fillSitemapLoc(StringBuilder sb, String url) {
		sb.append("        <loc>").append(SITEMAP_HOME_URL).append(url).append("</loc>").append("\n");
	}
	
	private void fillSitemapLocAlternatives(StringBuilder sb, String url) {
		fillSitemapLocAlternative(sb, url, "en");
		fillSitemapLocAlternative(sb, url, "ru");
	}
	
	private void fillSitemapLocAlternative(StringBuilder sb, String url, String langCode) {
		fillSitemapRow(sb, "        <xhtml:link ");
		fillSitemapRow(sb, "            rel=\"alternate\" ");
		sb.append("            hreflang=\"").append(langCode).append("\"").append("\n");
		sb.append("            href=\"")
				.append(SITEMAP_HOME_URL).append(langCode).append("/").append(url).append("\"/>").append("\n");
	}
}