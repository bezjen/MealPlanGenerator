package com.bezjen.whattoeat.model.filter;

import java.util.ArrayList;
import java.util.List;

public class SimpleRecipeFilterModel {
	protected List<Long> includeIngredientIds;
	protected List<Long> excludeIngredientIds;
	protected Integer minPrepTime;
	protected Integer maxPrepTime;
	protected Integer minCookingTime;
	protected Integer maxCookingTime;
	protected List<Long> cookingMethodIds;
	protected List<Long> dietIds;
	protected Boolean isBreakfast;
	protected Boolean isSnack;

	public SimpleRecipeFilterModel() {
		includeIngredientIds = new ArrayList<>();
		excludeIngredientIds = new ArrayList<>();
		cookingMethodIds = new ArrayList<>();
		dietIds = new ArrayList<>();
	}

	public List<Long> getIncludeIngredientIds() {
		return includeIngredientIds;
	}

	public void setIncludeIngredientIds(List<Long> includeIngredientIds) {
		this.includeIngredientIds = includeIngredientIds;
	}

	public List<Long> getExcludeIngredientIds() {
		return excludeIngredientIds;
	}

	public void setExcludeIngredientIds(List<Long> excludeIngredientIds) {
		this.excludeIngredientIds = excludeIngredientIds;
	}

	public Integer getMinPrepTime() {
		return minPrepTime;
	}

	public void setMinPrepTime(Integer minPrepTime) {
		this.minPrepTime = minPrepTime;
	}

	public Integer getMaxPrepTime() {
		return maxPrepTime;
	}

	public void setMaxPrepTime(Integer maxPrepTime) {
		this.maxPrepTime = maxPrepTime;
	}

	public Integer getMinCookingTime() {
		return minCookingTime;
	}

	public void setMinCookingTime(Integer minCookingTime) {
		this.minCookingTime = minCookingTime;
	}

	public Integer getMaxCookingTime() {
		return maxCookingTime;
	}

	public void setMaxCookingTime(Integer maxCookingTime) {
		this.maxCookingTime = maxCookingTime;
	}

	public List<Long> getCookingMethodIds() {
		return cookingMethodIds;
	}

	public void setCookingMethodIds(List<Long> cookingMethodIds) {
		this.cookingMethodIds = cookingMethodIds;
	}

	public List<Long> getDietIds() {
		return dietIds;
	}

	public void setDietIds(List<Long> dietIds) {
		this.dietIds = dietIds;
	}

	public Boolean getBreakfast() {
		return isBreakfast;
	}

	public void setBreakfast(Boolean breakfast) {
		isBreakfast = breakfast;
	}

	public Boolean getSnack() {
		return isSnack;
	}

	public void setSnack(Boolean snack) {
		isSnack = snack;
	}
}
