package com.bezjen.whattoeat.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "t_food_product_category_group")
public class FoodProductCategoryGroup extends LocalizedEntity {
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "categoryGroup")
    private List<FoodProductCategory> foodProductCategories;
	
	public FoodProductCategoryGroup() {
		super();
		foodProductCategories = new ArrayList<>();
	}

	public List<FoodProductCategory> getFoodProductCategories() {
		return foodProductCategories;
	}

	public void setFoodProductCategories(List<FoodProductCategory> foodProductCategories) {
		this.foodProductCategories = foodProductCategories;
	}

	@Override
	public String toString() {
		return "FoodProductCategoryGroup{" +
				"id=" + id +
				", ruLocale='" + ruLocale + '\'' +
				", enLocale='" + enLocale + '\'' +
				'}';
	}
}
