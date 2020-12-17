package com.bezjen.whattoeat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bezjen.whattoeat.entity.FoodProduct;
import com.bezjen.whattoeat.entity.RecipeIngredient;
import com.bezjen.whattoeat.item.FoodEnergyItem;

@Service
public class FoodEnergyService {
	public FoodEnergyItem getFoodEnergy(List<RecipeIngredient> recipeIngredients) {
		FoodEnergyItem foodEnergy = new FoodEnergyItem();
		for (RecipeIngredient recipeIngredient : recipeIngredients) {	//TODO: fix calculating
			FoodProduct foodProduct = recipeIngredient.getFoodProduct();
			if (foodProduct.getProteins() != null) {
				foodEnergy.getProteins().add(foodProduct.getProteins());
			}
			if (foodProduct.getFat() != null) {
				foodEnergy.getFat().add(foodProduct.getFat());
			}
			if (foodProduct.getCarbohydrates() != null) {
				foodEnergy.getCarbohydrates().add(foodProduct.getCarbohydrates());
			}
			if (foodProduct.getKilocalories() != null) {
				foodEnergy.getKilocalories().add(foodProduct.getKilocalories());
			}
		}
		return foodEnergy;
	}
}
