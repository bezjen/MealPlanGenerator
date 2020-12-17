package com.bezjen.whattoeat.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_recipe")
public class Recipe extends LocalizedEntity {
	@Column(name = "image_url", nullable = false, columnDefinition = "varchar(255) default '/img/recipeThumbnail.png'")
	private String imageUrl;
	@Column(name = "prep_time", nullable = false, length = 3)
	private Integer prepTime;
	@Column(name = "cooking_time", nullable = false, length = 3)
	private Integer cookingTime;
	@Column(name = "date_of_creation", nullable = false)
	private Date date;
	@Column(name = "is_approved", nullable = false)
	private boolean isApproved;
	@Column(name = "min_age_month", nullable = false, length = 2)
	private Integer minAgeMonth;
	@Column(name = "servings_number", nullable = false, length = 2)
	private Integer servingsNumber;
	@Column(name = "is_snack", nullable = false)
	private boolean isSnack;
	@Column(name = "is_breakfast_or_brunch", nullable = false)
	private boolean isBreakfast;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_recipe_user_id"))
	private User author;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", nullable = false, foreignKey = @ForeignKey(name = "fk_recipe_category_id"))
	private RecipeCategory recipeCategory;
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "recipe", cascade = CascadeType.ALL)
	private RecipeDescription description;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeIngredient> recipeIngredients;
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
    		name = "primary_cooking_method",
			nullable = false,
			foreignKey = @ForeignKey(name = "fk_recipe_cooking_method_id")
	)
	private CookingMethod primaryCookingMethod;
	@ManyToMany
    @JoinTable(
        name = "T_RECIPE_SECONDARY_COOKING_METHOD", 
        joinColumns = { @JoinColumn(name = "recipe_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "cooking_method_id") },
		foreignKey = @ForeignKey(name = "fk_recipe_cooking_method_rec_id"), 
        inverseForeignKey = @ForeignKey(name = "fk_recipe_cooking_method_cm_id")
    )
    private Set<CookingMethod> secondaryCookingMethods;
	@ManyToMany
	@JoinTable(
			name = "T_RECIPE_DIET",
			joinColumns = { @JoinColumn(name = "recipe_id") },
			inverseJoinColumns = { @JoinColumn(name = "diet_id") },
			foreignKey = @ForeignKey(name = "fk_recipe_diet_recipe_id"),
			inverseForeignKey = @ForeignKey(name = "fk_recipe_diet_diet_id")
	)
	private Set<Diet> diets;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeStep> steps;
	
	public Recipe() {
		super();
		recipeIngredients = new ArrayList<RecipeIngredient>();
		secondaryCookingMethods = new HashSet<>();
		steps = new ArrayList<>();
		diets = new HashSet<>();
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public Integer getPrepTime() {
		return prepTime;
	}

	public void setPrepTime(Integer prepTime) {
		this.prepTime = prepTime;
	}

	public Integer getCookingTime() {
		return cookingTime;
	}

	public void setCookingTime(Integer cookingTime) {
		this.cookingTime = cookingTime;
	}

	public RecipeDescription getDescription() {
		return description;
	}

	public void setDescription(RecipeDescription description) {
		this.description = description;
	}

	public List<RecipeIngredient> getRecipeIngredients() {
		return recipeIngredients;
	}

	public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
		this.recipeIngredients = recipeIngredients;
	}

	public Integer getServingsNumber() {
		return servingsNumber;
	}

	public void setServingsNumber(Integer servingsNumber) {
		this.servingsNumber = servingsNumber;
	}

	public CookingMethod getPrimaryCookingMethod() {
		return primaryCookingMethod;
	}

	public void setPrimaryCookingMethod(CookingMethod primaryCookingMethod) {
		this.primaryCookingMethod = primaryCookingMethod;
	}

	public Set<CookingMethod> getSecondaryCookingMethods() {
		return secondaryCookingMethods;
	}

	public void setSecondaryCookingMethods(Set<CookingMethod> secondaryCookingMethods) {
		this.secondaryCookingMethods = secondaryCookingMethods;
	}

	public List<RecipeStep> getSteps() {
		return steps;
	}

	public void setSteps(List<RecipeStep> steps) {
		this.steps = steps;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Recipe{" +
				"id=" + id +
				", ruLocale='" + ruLocale + '\'' +
				", enLocale='" + enLocale + '\'' +
				'}';
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	
	public Integer getMinAgeMonth() {
		return minAgeMonth;
	}
	
	public void setMinAgeMonth(Integer minAgeMonth) {
		this.minAgeMonth = minAgeMonth;
	}

	public boolean isSnack() {
		return isSnack;
	}

	public void setSnack(boolean isSnack) {
		this.isSnack = isSnack;
	}

	public boolean isBreakfast() {
		return isBreakfast;
	}

	public void setBreakfast(boolean breakfast) {
		isBreakfast = breakfast;
	}

	public Integer getMinYear() {
		return minAgeMonth / 12;
	}
	
	public Integer getMinMonth() {
		return minAgeMonth % 12;
	}

	public RecipeCategory getRecipeCategory() {
		return recipeCategory;
	}

	public void setRecipeCategory(RecipeCategory recipeCategory) {
		this.recipeCategory = recipeCategory;
	}

	public Set<Diet> getDiets() {
		return diets;
	}

	public void setDiets(Set<Diet> diets) {
		this.diets = diets;
	}
}
