package com.bezjen.whattoeat.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListModel {
	private List<Long> mealsIds;
	
	public ShoppingListModel() {
		mealsIds = new ArrayList<>();
	}

	public List<Long> getMealsIds() {
		return mealsIds;
	}
	public void setMealsIds(List<Long> mealsIds) {
		this.mealsIds = mealsIds;
	}
}
