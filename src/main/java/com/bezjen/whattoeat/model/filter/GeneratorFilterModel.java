package com.bezjen.whattoeat.model.filter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class GeneratorFilterModel extends BaseGeneratorFilterModel {
	@NotNull
	@Min(value = 1)
	@Max(value = 7)
	private Integer daysAmount;
	@NotNull
	@Min(value = 1)
	@Max(value = 9)
	private Integer mealsNumber;
//	@NotNull
//	@Min(value = 1)
//	@Max(value = 7)
	private Integer foodDiversity;

	public Integer getDaysAmount() {
		return daysAmount;
	}

	public void setDaysAmount(Integer daysAmount) {
		this.daysAmount = daysAmount;
	}
	
	public GeneratorFilterModel() {
		super();
	}

	public Integer getMealsNumber() {
		return mealsNumber;
	}

	public void setMealsNumber(Integer mealsNumber) {
		this.mealsNumber = mealsNumber;
	}

	public Integer getFoodDiversity() {
		return foodDiversity;
	}

	public void setFoodDiversity(Integer foodDiversity) {
		this.foodDiversity = foodDiversity;
	}

}
