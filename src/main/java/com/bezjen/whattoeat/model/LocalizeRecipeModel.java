package com.bezjen.whattoeat.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class LocalizeRecipeModel {
	@NotBlank
	private String recipeName;
	private String recipeDescription;
	private List<String> steps;
	
	public LocalizeRecipeModel() {
		steps = new ArrayList<>();
	}
	
	public String getRecipeName() {
		return recipeName;
	}
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}
	public String getRecipeDescription() {
		return recipeDescription;
	}
	public void setRecipeDescription(String recipeDescription) {
		this.recipeDescription = recipeDescription;
	}
	public List<@NotEmpty String> getSteps() {
		return steps;
	}
	public void setSteps(List<@NotEmpty String> steps) {
		this.steps = steps;
	}
}
