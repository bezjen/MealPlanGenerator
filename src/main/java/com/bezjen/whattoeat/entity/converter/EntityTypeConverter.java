package com.bezjen.whattoeat.entity.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.bezjen.whattoeat.entity.EntityType;

@Converter
public class EntityTypeConverter implements AttributeConverter<EntityType, Integer> {
	@Override
	public Integer convertToDatabaseColumn(EntityType attribute) {
		if (attribute == null) {
            return null;
        }
        return attribute.getCode();
	}

	@Override
	public EntityType convertToEntityAttribute(Integer dbData) {
		if (dbData == null) {
            return null;
        }
        return EntityType.fromCode(dbData);
	}
}