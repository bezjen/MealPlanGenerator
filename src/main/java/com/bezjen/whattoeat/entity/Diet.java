package com.bezjen.whattoeat.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_diet")
public class Diet extends LocalizedEntity {
	@Column(name = "image_url", nullable = false, columnDefinition = "varchar(255) default '/img/dietThumbnail.png'")
	private String imageUrl;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "diet", cascade = CascadeType.ALL)
	private DietDescription description;
	@ManyToMany(mappedBy = "diets")
	private Set<Recipe> recipes;

	public Diet() {
		super();
		recipes = new HashSet<>();
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public DietDescription getDescription() {
		return description;
	}

	public void setDescription(DietDescription description) {
		this.description = description;
	}

	public Set<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(Set<Recipe> recipes) {
		this.recipes = recipes;
	}

	@Override
	public String toString() {
		return "Diet{" +
				"id=" + id +
				", ruLocale='" + ruLocale + '\'' +
				", enLocale='" + enLocale + '\'' +
				'}';
	}
}
