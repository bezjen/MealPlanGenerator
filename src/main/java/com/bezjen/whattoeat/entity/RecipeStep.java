package com.bezjen.whattoeat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_recipe_step")
public class RecipeStep extends LocalizedEntity {
	@Column(length = 2, nullable = false)
	private Integer stepIndex;
	@Column(name = "image_url")
	private String imageUrl;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false, foreignKey = @ForeignKey(name = "fk_recipe_step_recipe_id"))
	private Recipe recipe;
	
	public RecipeStep() {
		super();
	}

	public Integer getStepIndex() {
		return stepIndex;
	}
	public void setStepIndex(Integer stepIndex) {
		this.stepIndex = stepIndex;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public String toString() {
		return "RecipeStep{" +
				"stepIndex=" + stepIndex +
				", id=" + id +
				", ruLocale='" + ruLocale + '\'' +
				", enLocale='" + enLocale + '\'' +
				'}';
	}
}
