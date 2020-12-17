package com.bezjen.whattoeat.model.localized;

public class MeasureModel extends LocalizedEntityModel {
	private int inCanonicalUnits;
	private int measureType;
	
	public int getInCanonicalUnits() {
		return inCanonicalUnits;
	}
	public void setInCanonicalUnits(int inCanonicalUnits) {
		this.inCanonicalUnits = inCanonicalUnits;
	}
	public int getMeasureType() {
		return measureType;
	}
	public void setMeasureType(int measureType) {
		this.measureType = measureType;
	}
}
