package com.bezjen.whattoeat.item.diet;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

//TODO: REMOVE AFTER FINISHING NEW DIETS PAGE
public enum DietType {
    GLUTEN_FREE("Gluten Free", "glutenFree"),
    DIARY_FREE("Dairy Free", "dairyFree"),
    VEGAN("Vegan", "vegan"),
    VEGETARIAN("Vegetarian", "vegetarian"),
    COOKING_FOR_KIDS("Cooking for Kids", "cookingForKids"),
    DIABETIC("Diabetic", "diabetic"),
    CARDIAC("Cardiac", "cardiac"),
    HEALTHY("Healthy", "healthy"),
    KETOGENIC("Ketogenic", "ketogenic"),
    LOW_CALORIE("Low Calorie", "lowCalorie"),
    LOW_CARB("Low Carb", "lowCarb"),
    PALEO("Paleo", "paleo"),
    MEDITERRANIAN("Mediterranean", "mediterranean");

    private String enLocale;
    private String localizationSuffix;

    private static final Map<String, DietType> dietTypesMap =
            Arrays.stream(DietType.values())
                    .collect(Collectors.toMap(dietType -> dietType.enLocale, dietType -> dietType));

    DietType(String enLocale, String localizationSuffix) {
        this.enLocale = enLocale;
        this.localizationSuffix = localizationSuffix;
    }

    public String getLocalizationSuffix() {
        return localizationSuffix;
    }

    public static DietType valueOfByEnLocale(String enLocale) {
        return dietTypesMap.get(enLocale);
    }
}