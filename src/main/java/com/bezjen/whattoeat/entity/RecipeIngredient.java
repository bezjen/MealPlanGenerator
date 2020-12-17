package com.bezjen.whattoeat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_recipe_ingredient")
public class RecipeIngredient {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Double amount;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
    		name = "recipe_id",
			nullable = false,
			foreignKey = @ForeignKey(name = "fk_recipe_ingredient_recipe_id")
	)
	private Recipe recipe;
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
    		name = "food_product_id",
			nullable = false,
			foreignKey = @ForeignKey(name = "fk_recipe_ingredient_food_id")
	)
	private FoodProduct foodProduct;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
    		name = "measure_id",
			nullable = false,
			foreignKey = @ForeignKey(name = "fk_recipe_ingredient_measure_id")
	)
	private Measure measure;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public FoodProduct getFoodProduct() {
		return foodProduct;
	}

	public void setFoodProduct(FoodProduct foodProduct) {
		this.foodProduct = foodProduct;
	}

	public Measure getMeasure() {
		return measure;
	}

	public void setMeasure(Measure measure) {
		this.measure = measure;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "RecipeIngridient [id=" + id + ", amount=" + amount + ", foodProduct="
				+ foodProduct + ", measure=" + measure + "]";
	}
}
