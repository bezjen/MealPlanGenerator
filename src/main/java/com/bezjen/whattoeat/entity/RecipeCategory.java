package com.bezjen.whattoeat.entity;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "t_recipe_category")
public class RecipeCategory extends LocalizedEntity {
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "recipeCategory")
	private List<Recipe> recipes;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_recipe_cat_parent_id"))
	private RecipeCategory parent;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
	private List<RecipeCategory> subCategories;

	public RecipeCategory() {
		super();
		recipes = new ArrayList<>();
		subCategories = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "RecipeCategory{" +
				"parent=" + parent +
				", id=" + id +
				", ruLocale='" + ruLocale + '\'' +
				", enLocale='" + enLocale + '\'' +
				'}';
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

	public RecipeCategory getParent() {
		return parent;
	}

	public void setParent(RecipeCategory parent) {
		this.parent = parent;
	}

	public List<RecipeCategory> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<RecipeCategory> subCategories) {
		this.subCategories = subCategories;
	}

	public String getParentCategories(Locale locale) { 	//TODO: move to variable
		RecipeCategory category = getParent();
		if (category == null) {
			return "";
		}
		StringBuilder categories = new StringBuilder(category.localization(locale));
		category = category.getParent();
		while(category != null) {
			categories.insert(0, " > ");
			categories.insert(0, category.localization(locale));
			category = category.getParent();
		}
		return categories.toString();
	}

	public String getCategoryString(Locale locale) {
		StringBuilder categories = new StringBuilder(localization(locale));
		String parentCategories = getParentCategories(locale);
		if (StringUtils.isNotBlank(parentCategories)) {
			categories.insert(0, " > ");
			categories.insert(0, parentCategories);
		}
		return categories.toString();
	}
}
