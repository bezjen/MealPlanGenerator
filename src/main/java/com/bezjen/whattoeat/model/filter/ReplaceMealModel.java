package com.bezjen.whattoeat.model.filter;

import java.util.ArrayList;
import java.util.List;

public class ReplaceMealModel extends BaseGeneratorFilterModel {
	private Integer index;
	private List<Long> mealsIds;
	
	public ReplaceMealModel() {
		super();
		mealsIds = new ArrayList<>();
	}

	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public List<Long> getMealsIds() {
		return mealsIds;
	}
	public void setMealsIds(List<Long> mealsIds) {
		this.mealsIds = mealsIds;
	}
}
