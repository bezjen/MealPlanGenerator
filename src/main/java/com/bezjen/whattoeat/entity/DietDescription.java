package com.bezjen.whattoeat.entity;

import javax.persistence.*;

@Entity
@Table(name = "t_diet_description")
public class DietDescription extends LocalizedEntity {
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
    		name = "diet_id",
			nullable = false,
			foreignKey = @ForeignKey(name = "fk_diet_description_diet_id")
	)
	private Diet diet;

	public DietDescription() {
		super();
	}

	public Diet getDiet() {
		return diet;
	}

	public void setDiet(Diet diet) {
		this.diet = diet;
	}

	@Override
	public String toString() {
		return "DietDescription{" +
				"id=" + id +
				", ruLocale='" + ruLocale + '\'' +
				", enLocale='" + enLocale + '\'' +
				'}';
	}
}
