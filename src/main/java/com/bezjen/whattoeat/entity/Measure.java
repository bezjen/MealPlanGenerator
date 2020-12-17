package com.bezjen.whattoeat.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bezjen.whattoeat.entity.converter.MeasureTypeConverter;

@Entity
@Table(name = "t_measure")
public class Measure extends LocalizedEntity {
	@Column(name = "in_canonical_units", nullable = false)
	private Integer inCanonicalUnits;
	@Column(name = "measure_type", nullable = false)
	@Convert(converter = MeasureTypeConverter.class)
	private MeasureType measureType;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "measure")
    private List<RecipeIngredient> recipeIngredients;
	
	public Measure() {
		super();
		recipeIngredients = new ArrayList<>();
	}

	public Integer getInCanonicalUnits() {
		return inCanonicalUnits;
	}

	public void setInCanonicalUnits(Integer inCanonicalUnits) {
		this.inCanonicalUnits = inCanonicalUnits;
	}

	public MeasureType getMeasureType() {
		return measureType;
	}

	public void setMeasureType(MeasureType measureType) {
		this.measureType = measureType;
	}

	public List<RecipeIngredient> getRecipeIngredients() {
		return recipeIngredients;
	}

	public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
		this.recipeIngredients = recipeIngredients;
	}

	@Override
	public String toString() {
		return "Measure{" +
				"id=" + id +
				", ruLocale='" + ruLocale + '\'' +
				", enLocale='" + enLocale + '\'' +
				'}';
	}
}
