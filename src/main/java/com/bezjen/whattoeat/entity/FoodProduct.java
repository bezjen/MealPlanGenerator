package com.bezjen.whattoeat.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "t_food_product")
public class FoodProduct extends LocalizedEntity {
	@Column(name = "image_url", nullable = false, columnDefinition = "varchar(255) default '/img/ingredientThumbnail.png'")
	private String imageUrl;
	@Column(name = "min_age_month", nullable = false, length = 2)
	private Integer minAgeMonth;
	@Column(nullable = false)
	private BigDecimal proteins;
	@Column(nullable = false)
	private BigDecimal fat;
	@Column(nullable = false)
	private BigDecimal carbohydrates;
	@Column(nullable = false)
	private BigDecimal kilocalories;
	@Column(name = "is_common", nullable = false)
	private boolean isCommon;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false, foreignKey = @ForeignKey(name = "fk_food_product_category_id"))
	private FoodProductCategory category;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "foodProduct")
    private List<RecipeIngredient> recipeIngredients;
	
	public FoodProduct() {
		super();
		recipeIngredients = new ArrayList<>();
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public BigDecimal getProteins() {
		return proteins;
	}

	public void setProteins(BigDecimal proteins) {
		this.proteins = proteins;
	}

	public BigDecimal getFat() {
		return fat;
	}

	public void setFat(BigDecimal fat) {
		this.fat = fat;
	}

	public BigDecimal getCarbohydrates() {
		return carbohydrates;
	}

	public void setCarbohydrates(BigDecimal carbohydrates) {
		this.carbohydrates = carbohydrates;
	}

	public List<RecipeIngredient> getRecipeIngredients() {
		return recipeIngredients;
	}

	public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
		this.recipeIngredients = recipeIngredients;
	}

	public BigDecimal getKilocalories() {
		return kilocalories;
	}

	public void setKilocalories(BigDecimal kilocalories) {
		this.kilocalories = kilocalories;
	}

	@Override
	public String toString() {
		return "FoodProduct{" +
				"id=" + id +
				", ruLocale='" + ruLocale + '\'' +
				", enLocale='" + enLocale + '\'' +
				'}';
	}

	public Integer getMinAgeMonth() {
		return minAgeMonth;
	}

	public void setMinAgeMonth(Integer minAgeMonth) {
		this.minAgeMonth = minAgeMonth;
	}
	
	public Integer getMinYear() {
		return minAgeMonth / 12;
	}
	
	public Integer getMinMonth() {
		return minAgeMonth % 12;
	}

	public boolean isCommon() {
		return isCommon;
	}

	public void setCommon(boolean isCommon) {
		this.isCommon = isCommon;
	}

	public FoodProductCategory getCategory() {
		return category;
	}

	public void setCategory(FoodProductCategory category) {
		this.category = category;
	}
}
