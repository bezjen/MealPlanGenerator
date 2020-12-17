package com.bezjen.whattoeat.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_recipe_description")
public class RecipeDescription extends LocalizedEntity {	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
    		name = "recipe_id",
			nullable = false,
			foreignKey = @ForeignKey(name = "fk_recipe_description_recipe_id")
	)
	private Recipe recipe;
	
	public RecipeDescription() {
		super();
	}

	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public String toString() {
		return "RecipeDescription{" +
				"id=" + id +
				", ruLocale='" + ruLocale + '\'' +
				", enLocale='" + enLocale + '\'' +
				'}';
	}
}
