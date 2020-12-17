package com.bezjen.whattoeat.item.diet;

public enum DietProductCategoryType {
    ALCOHOL("alcohol"),
    BREAD("bread"),
    CEREAL("cereals"),
    EGGS("eggs"),
    FISH("fish"),
    FRUIT("fruit"),
    MEAL_AND_POULTRY("meatAndPoultry"),
    MILK("milk"),
    MUSHROOMS("mushrooms"),
    NUTS("nuts"),
    OIL_AND_BUTTER("oilAndButter"),
    SAUCES("sauces"),
    SOFT_DRINKS("softDrinks"),
    SWEETS("sweets"),
    VEGETABLES("vegetables");

    private String localizationFolder;

    DietProductCategoryType(String localizationFolder) {
        this.localizationFolder = localizationFolder;
    }

    public String getLocalizationFolder() {
        return localizationFolder;
    }
}
