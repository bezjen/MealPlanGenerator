package com.bezjen.whattoeat.model.localized;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

public class FoodProductModel extends LocalizedEntityModel {
	private MultipartFile foodProductImage;
	private String foodProductImageUrl;
	private BigDecimal proteins;
	private BigDecimal fat;
	private BigDecimal carbohydrates;
	private BigDecimal kilocalories;
	private int minAgeYear;
	private int minAgeMonth;
	private boolean isCommon;
	private Long category;
	
	public FoodProductModel() {
		minAgeYear = 3;
		minAgeMonth = 0;
	}

	public MultipartFile getFoodProductImage() {
		return foodProductImage;
	}
	public void setFoodProductImage(MultipartFile  foodProductImage) {
		this.foodProductImage = foodProductImage;
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
	public int getMinAgeYear() {
		return minAgeYear;
	}
	public void setMinAgeYear(int minAgeYear) {
		this.minAgeYear = minAgeYear;
	}
	public int getMinAgeMonth() {
		return minAgeMonth;
	}
	public void setMinAgeMonth(int minAgeMonth) {
		this.minAgeMonth = minAgeMonth;
	}
	public boolean isCommon() {
		return isCommon;
	}
	public void setCommon(boolean isCommon) {
		this.isCommon = isCommon;
	}
	public Long getCategory() {
		return category;
	}
	public void setCategory(Long category) {
		this.category = category;
	}

	public String getFoodProductImageUrl() {
		return foodProductImageUrl;
	}

	public void setFoodProductImageUrl(String foodProductImageUrl) {
		this.foodProductImageUrl = foodProductImageUrl;
	}
}
