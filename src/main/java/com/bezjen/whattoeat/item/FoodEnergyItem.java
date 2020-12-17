package com.bezjen.whattoeat.item;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FoodEnergyItem {
	private BigDecimal proteins;
	private BigDecimal fat;
	private BigDecimal carbohydrates;
	private BigDecimal kilocalories;
	
	//TODO: constructor refactoring (move values to args?)
	public FoodEnergyItem() {
		proteins = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
		fat = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
		carbohydrates = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
		kilocalories = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
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
	public BigDecimal getKilocalories() {
		return kilocalories;
	}
	public void setKilocalories(BigDecimal kilocalories) {
		this.kilocalories = kilocalories;
	}
}
