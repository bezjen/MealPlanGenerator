package com.bezjen.whattoeat.model.filter;

public class BaseGeneratorFilterModel extends SimpleRecipeFilterModel {
	protected boolean isFirstMealBreakfast;

	public boolean isFirstMealBreakfast() {
		return isFirstMealBreakfast;
	}

	public void setFirstMealBreakfast(boolean firstMealBreakfast) {
		isFirstMealBreakfast = firstMealBreakfast;
	}
}
