package com.bezjen.whattoeat.model.localized;

public class RecipeCategoryModel extends LocalizedEntityModel {
	private Long parentCategory;

	public Long getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Long parentCategory) {
		this.parentCategory = parentCategory;
	}
}
