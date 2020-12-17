package com.bezjen.whattoeat.entity;

import java.io.Serializable;

public enum MeasureType implements Serializable {
    WEIGHT(1, "measure.type.weight", "measure.type.weight.g"),
    VOLUME(2, "measure.type.volume", "measure.type.volume.ml");
    
    private Integer code;
    private String localizationKey;
    private String canonicalLocalizationKey;
	
	MeasureType(Integer code, String localizationKey, String canonicalLocalizationKey) {
		this.code = code;
		this.localizationKey = localizationKey;
		this.canonicalLocalizationKey = canonicalLocalizationKey;
	}
	
	public Integer getCode() {
		return code;
	}

	public String getLocalizationKey() {
		return localizationKey;
	}

	public String getCanonicalLocalizationKey() {
		return canonicalLocalizationKey;
	}
	
	public static MeasureType fromCode(Integer code) {
        for (MeasureType entityType : MeasureType.values()){
            if (entityType.getCode().equals(code)){
                return entityType;
            }
        }
        throw new UnsupportedOperationException("Measure type with code" + code + " is not supported!");
    }
}




