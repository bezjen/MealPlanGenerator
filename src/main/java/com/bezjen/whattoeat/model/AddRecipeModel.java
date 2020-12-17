package com.bezjen.whattoeat.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

public class AddRecipeModel {
	@NotBlank
	private String recipeName;
	private Long recipeCategory;
	@Nullable
	private MultipartFile recipeImage;
	private String recipeDescription;
	@NotNull
	private Integer servingsNumber;
	@NotNull
	private Integer prepTime;
	@NotNull
	private Integer cookingTime;
	@NotNull
	private Long primaryCookingMethod;
	private List<Long> secondaryCookingMethods;
	private List<@NotNull Long> foodProducts;
	private List<@NotNull Long> measures;
	private List<Double> amounts;
	private List<String> steps;
	private List<MultipartFile> stepsImages;
	private Locale locale;
	private List<Long> dietaryOptions;
	private boolean isSnack;
	private boolean isBreakfastOrBrunch;
	
	public AddRecipeModel() {
		secondaryCookingMethods = new ArrayList<>();
		foodProducts = new ArrayList<>();
		measures = new ArrayList<>();
		amounts = new ArrayList<>();
		steps = new ArrayList<>();
		stepsImages = new ArrayList<>();
		dietaryOptions = new ArrayList<>();
	}
	
	public String getRecipeName() {
		return recipeName;
	}
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public Long getRecipeCategory() {
		return recipeCategory;
	}

	public void setRecipeCategory(Long recipeCategory) {
		this.recipeCategory = recipeCategory;
	}

	public String getRecipeDescription() {
		return recipeDescription;
	}
	public void setRecipeDescription(String recipeDescription) {
		this.recipeDescription = recipeDescription;
	}
	public Integer getPrepTime() {
		return prepTime;
	}
	public void setPrepTime(Integer prepTime) {
		this.prepTime = prepTime;
	}
	public Integer getCookingTime() {
		return cookingTime;
	}
	public void setCookingTime(Integer cookingTime) {
		this.cookingTime = cookingTime;
	}
	public Long getPrimaryCookingMethod() {
		return primaryCookingMethod;
	}
	public void setPrimaryCookingMethod(Long primaryCookingMethod) {
		this.primaryCookingMethod = primaryCookingMethod;
	}
	public List<Long> getSecondaryCookingMethods() {
		return secondaryCookingMethods;
	}
	public void setSecondaryCookingMethods(List<Long> secondaryCookingMethods) {
		this.secondaryCookingMethods = secondaryCookingMethods;
	}
	public List<Long> getFoodProducts() {
		return foodProducts;
	}
	public void setFoodProducts(List<Long> foodProducts) {
		this.foodProducts = foodProducts;
	}
	public List<Long> getMeasures() {
		return measures;
	}
	public void setMeasures(List<Long> measures) {
		this.measures = measures;
	}
	public List<Double> getAmounts() {
		return amounts;
	}
	public void setAmounts(List<Double> amounts) {
		this.amounts = amounts;
	}
	public boolean isSnack() {
		return isSnack;
	}
	public void setSnack(boolean isSnack) {
		this.isSnack = isSnack;
	}
	public List<@NotEmpty String> getSteps() {
		return steps;
	}
	public void setSteps(List<@NotEmpty String> steps) {
		this.steps = steps;
	}
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Integer getServingsNumber() {
		return servingsNumber;
	}

	public void setServingsNumber(Integer servingsNumber) {
		this.servingsNumber = servingsNumber;
	}

	public MultipartFile getRecipeImage() {
		return recipeImage;
	}

	public void setRecipeImage(MultipartFile recipeImage) {
		this.recipeImage = recipeImage;
	}

	public List<MultipartFile> getStepsImages() {
		return stepsImages;
	}

	public void setStepsImages(List<MultipartFile> stepsImages) {
		this.stepsImages = stepsImages;
	}

	public List<Long> getDietaryOptions() {
		return dietaryOptions;
	}

	public void setDietaryOptions(List<Long> dietaryOptions) {
		this.dietaryOptions = dietaryOptions;
	}

	public boolean isBreakfastOrBrunch() {
		return isBreakfastOrBrunch;
	}

	public void setBreakfastOrBrunch(boolean breakfastOrBrunch) {
		isBreakfastOrBrunch = breakfastOrBrunch;
	}
}
