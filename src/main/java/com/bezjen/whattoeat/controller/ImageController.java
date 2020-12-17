package com.bezjen.whattoeat.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import com.bezjen.whattoeat.item.ImageItemType;
import org.apache.commons.io.IOUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bezjen.whattoeat.service.image.ImageService;

@Controller
public class ImageController extends BaseController {
	private ImageService imageService;

	public ImageController(
			MessageSource messageSource,
			ImageService imageService
	) {
		super(messageSource);
		this.imageService = imageService;
	}

	//TODO: content headers
	@GetMapping("/images/foodProducts/{fileName}")
	@ResponseBody
	public void getFoodProductIcon(
			@PathVariable("fileName") String fileName,
			HttpServletResponse response
	) throws IOException {
		writeImageToResponse(fileName, response, ImageItemType.FOOD_PRODUCT);
	}

	@GetMapping("/images/diets/{fileName}")
	@ResponseBody
	public void getDietImage(
			@PathVariable("fileName") String fileName,
			HttpServletResponse response
	) throws IOException {
		writeImageToResponse(fileName, response, ImageItemType.DIET);
	}
	
	@GetMapping("/images/avatar/{fileName}")
	@ResponseBody
	public void getAvatar(
			@PathVariable("fileName") String fileName,
			HttpServletResponse response
	) throws IOException {
		writeImageToResponse(fileName, response, ImageItemType.AVATAR);
	}
	
	@GetMapping("/images/recipe/{fileName}")
	@ResponseBody
	public void getRecipeImage(
			@PathVariable("fileName") String fileName,
			HttpServletResponse response
	) throws IOException {
		writeImageToResponse(fileName, response, ImageItemType.RECIPE);
	}

	private void writeImageToResponse(
			String fileName,
			HttpServletResponse response,
			ImageItemType imageItemType
	) throws IOException {
		InputStream in = new ByteArrayInputStream(imageService.getImage(fileName, imageItemType));
		response.setContentType(imageService.getMediaType(fileName));
		IOUtils.copy(in, response.getOutputStream());
	}
}