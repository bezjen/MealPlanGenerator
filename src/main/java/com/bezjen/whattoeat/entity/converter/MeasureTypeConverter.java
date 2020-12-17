package com.bezjen.whattoeat.entity.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.bezjen.whattoeat.entity.MeasureType;

@Converter
public class MeasureTypeConverter implements AttributeConverter<MeasureType, Integer> {
	@Override
	public Integer convertToDatabaseColumn(MeasureType attribute) {
		if (attribute == null) {
            return null;
        }
        return attribute.getCode();
	}

	@Override
	public MeasureType convertToEntityAttribute(Integer dbData) {
		if (dbData == null) {
            return null;
        }
        return MeasureType.fromCode(dbData);
	}
}