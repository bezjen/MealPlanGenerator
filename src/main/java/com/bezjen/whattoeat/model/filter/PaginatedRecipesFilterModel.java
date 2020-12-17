package com.bezjen.whattoeat.model.filter;

public class PaginatedRecipesFilterModel extends SimpleRecipeFilterModel {
	private Integer page;

	public PaginatedRecipesFilterModel() {
		super();
	}
	
	public PaginatedRecipesFilterModel(Integer page) {
		super();
		this.page = page;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
