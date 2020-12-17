package com.bezjen.whattoeat.entity;

import java.io.Serializable;

public enum EntityType implements Serializable {
    RECIPE(1),
    FOOD_PRODUCT(2),
    MEASURE(3),
    COOKING_METHOD(4),
    RECIPE_STEP(5),
    RECIPE_DESCRIPTION(6),
    DIET(7),
    FOOD_PRODUCT_CATEGORY(8),
    FOOD_PRODUCT_CATEGORY_GROUP(9),
    RECIPE_CATEGORY(10);
    
    private Integer code;
	
	EntityType(Integer code) {
		this.code = code;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public static EntityType fromCode(Integer code) {
        for (EntityType entityType : EntityType.values()){
            if (entityType.getCode().equals(code)){
                return entityType;
            }
        }
        throw new UnsupportedOperationException("Entity type with code" + code + " is not supported!");
    }
}




