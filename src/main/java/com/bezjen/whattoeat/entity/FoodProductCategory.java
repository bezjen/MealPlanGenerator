package com.bezjen.whattoeat.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "t_food_product_category")
public class FoodProductCategory extends LocalizedEntity {
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<FoodProduct> foodProducts;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false, foreignKey = @ForeignKey(name = "fk_food_product_cat_group_id"))
	private FoodProductCategoryGroup categoryGroup;
	
	public FoodProductCategory() {
		super();
		foodProducts = new ArrayList<>();
	}

	public List<FoodProduct> getFoodProducts() {
		return foodProducts;
	}

	public void setFoodProducts(List<FoodProduct> foodProducts) {
		this.foodProducts = foodProducts;
	}

	@Override
	public String toString() {
		return "FoodProductCategory{" +
				"id=" + id +
				", ruLocale='" + ruLocale + '\'' +
				", enLocale='" + enLocale + '\'' +
				'}';
	}

	public FoodProductCategoryGroup getCategoryGroup() {
		return categoryGroup;
	}

	public void setCategoryGroup(FoodProductCategoryGroup categoryGroup) {
		this.categoryGroup = categoryGroup;
	}
}
