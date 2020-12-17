package com.bezjen.whattoeat.model.localized;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class DietModel extends LocalizedEntityModel {
	private MultipartFile dietImage;
	private String dietImageUrl;
	private String ruDescription;
	private String enDescription;

	public MultipartFile getDietImage() {
		return dietImage;
	}

	public void setDietImage(MultipartFile dietImage) {
		this.dietImage = dietImage;
	}

	public String getDietImageUrl() {
		return dietImageUrl;
	}

	public void setDietImageUrl(String dietImageUrl) {
		this.dietImageUrl = dietImageUrl;
	}

	public String getRuDescription() {
		return ruDescription;
	}

	public void setRuDescription(String ruDescription) {
		this.ruDescription = ruDescription;
	}

	public String getEnDescription() {
		return enDescription;
	}

	public void setEnDescription(String enDescription) {
		this.enDescription = enDescription;
	}
}
