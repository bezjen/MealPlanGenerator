package com.bezjen.whattoeat.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "t_cooking_method")
public class CookingMethod extends LocalizedEntity {
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "primaryCookingMethod")
    private Set<Recipe> recipesPrimary;
	@ManyToMany(mappedBy = "secondaryCookingMethods")
    private Set<Recipe> recipesSecondary;
	
	public CookingMethod() {
		super();
		recipesPrimary = new HashSet<>();
		recipesSecondary = new HashSet<>();
	}

	public Set<Recipe> getRecipesPrimary() {
		return recipesPrimary;
	}

	public void setRecipesPrimary(Set<Recipe> recipesPrimary) {
		this.recipesPrimary = recipesPrimary;
	}

	public Set<Recipe> getRecipesSecondary() {
		return recipesSecondary;
	}

	public void setRecipesSecondary(Set<Recipe> recipesSecondary) {
		this.recipesSecondary = recipesSecondary;
	}

	@Override
	public String toString() {
		return "CookingMethod{" +
				"id=" + id +
				", ruLocale='" + ruLocale + '\'' +
				", enLocale='" + enLocale + '\'' +
				'}';
	}
}
