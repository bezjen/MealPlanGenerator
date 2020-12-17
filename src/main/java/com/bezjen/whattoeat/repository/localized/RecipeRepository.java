package com.bezjen.whattoeat.repository.localized;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bezjen.whattoeat.entity.Recipe;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long> {
	boolean existsByRuLocale(String ruLocale);
	boolean existsByEnLocale(String enLocale);
	List<Recipe> findByEnLocale(String enLocale);
	List<Recipe> findByRuLocale(String ruLocale);

	List<Recipe> findByEnLocaleIsNullOrderByDateDesc();

	List<Recipe> findByEnLocaleStartingWithIgnoreCase(String enLocale);
	List<Recipe> findByRuLocaleStartingWithIgnoreCase(String ruLocale);
	List<Recipe> findByAuthorIdOrderByDateDesc(Long id);

	List<Recipe> findByIsApprovedAndRuLocaleNotNullAndEnLocaleNotNullOrderByDateDesc(Boolean isApproved);
	List<Recipe> findByIsApprovedAndRuLocaleIsNullOrderByDateDesc(Boolean isApproved);
	List<Recipe> findByIsApprovedAndEnLocaleIsNullOrderByDateDesc(Boolean isApproved);

	List<Recipe> findByIsApprovedAndRuLocaleNotNullOrderByDateDesc(Boolean isApproved);
	List<Recipe> findByIsApprovedAndEnLocaleNotNullOrderByDateDesc(Boolean isApproved);
	List<Recipe> findByIsApprovedAndRuLocaleNotNullOrderByDateDesc(Boolean isApproved, Pageable pageable);
	List<Recipe> findByIsApprovedAndEnLocaleNotNullOrderByDateDesc(Boolean isApproved, Pageable pageable);
	List<Recipe> findTop10ByIsApprovedAndRuLocaleNotNullOrderByDateDesc(Boolean isApproved);
	List<Recipe> findTop10ByIsApprovedAndEnLocaleNotNullOrderByDateDesc(Boolean isApproved);
	List<Recipe> findTop10ByIsApprovedAndIsBreakfastAndRuLocaleNotNull(Boolean isApproved, Boolean isBreakfast);
	List<Recipe> findTop10ByIsApprovedAndIsBreakfastAndEnLocaleNotNull(Boolean isApproved, Boolean isBreakfast);

	String QUERY_WITH_FILTERS =
			"SELECT DISTINCT " +
				"r " +
			"FROM " +
				"Recipe r " +
				"JOIN r.recipeIngredients ri " +
				"LEFT JOIN r.diets d " +
			"WHERE " +
				"r.isApproved = 1 AND " +
				"('en' = :locale AND r.enLocale != null OR 'ru' = :locale AND r.ruLocale != null) AND " +
				"(:#{#minPrepTime == null} = true OR :#{#maxPrepTime == null} = true OR r.prepTime BETWEEN :minPrepTime AND :maxPrepTime) AND " +
				"(:#{#minCookingTime == null} = true OR :#{#maxCookingTime == null} = true OR r.cookingTime BETWEEN :minCookingTime AND :maxCookingTime) AND " +
				"(:#{#cookingMethodIds == null} = true OR r.primaryCookingMethod.id IN :cookingMethodIds) AND " +
				"(:#{#dietIds == null} = true OR d.id IN :dietIds) AND " +
				"(:#{#includeIngredientIds == null} = true OR ri.foodProduct.id IN :includeIngredientIds) AND " +
				"(:#{#isBreakfast == null} = true OR r.isBreakfast = :isBreakfast) AND " +
				"(:#{#isSnack == null} = true OR r.isSnack = :isSnack) " +
			"ORDER BY r.date DESC";
	@Query(QUERY_WITH_FILTERS)
	List<Recipe> findFiltered(@Param("locale") String locale,
							  @Param("minPrepTime") Integer minPrepTime,
							  @Param("maxPrepTime") Integer maxPrepTime,
							  @Param("minCookingTime") Integer minCookingTime,
							  @Param("maxCookingTime") Integer maxCookingTime,
							  @Param("cookingMethodIds") List<Long> cookingMethodIds,
							  @Param("dietIds") List<Long> dietIds,
							  @Param("includeIngredientIds") List<Long> includeIngredientIds,
							  @Param("isBreakfast") Boolean isBreakfast,
							  @Param("isSnack") Boolean isSnack);
	@Query(QUERY_WITH_FILTERS)
	List<Recipe> findFiltered(@Param("locale") String locale,
							  @Param("minPrepTime") Integer minPrepTime,
							  @Param("maxPrepTime") Integer maxPrepTime,
							  @Param("minCookingTime") Integer minCookingTime,
							  @Param("maxCookingTime") Integer maxCookingTime,
							  @Param("cookingMethodIds") List<Long> cookingMethodIds,
							  @Param("dietIds") List<Long> dietIds,
							  @Param("includeIngredientIds") List<Long> includeIngredientIds,
							  @Param("isBreakfast") Boolean isBreakfast,
							  @Param("isSnack") Boolean isSnack,
							  Pageable pageable);

	String QUERY_COUNT_WITH_FILTERS =
			"SELECT count(DISTINCT r.id) " +
					"FROM " +
					"Recipe r " +
					"JOIN r.recipeIngredients ri " +
					"LEFT JOIN r.diets d " +
					"WHERE " +
					"r.isApproved = 1 AND " +
					"('en' = :locale AND r.enLocale != null OR 'ru' = :locale AND r.ruLocale != null) AND " +
					"r.prepTime BETWEEN :minPrepTime AND :maxPrepTime AND " +
					"r.cookingTime BETWEEN :minCookingTime AND :maxCookingTime AND " +
					"(:#{#cookingMethodIds == null} = true OR r.primaryCookingMethod.id IN :cookingMethodIds) AND " +
					"(:#{#dietIds == null} = true OR d.id IN :dietIds) AND " +
					"(:#{#includeIngredientIds == null} = true OR ri.foodProduct.id IN :includeIngredientIds) AND " +
					"(:#{#isBreakfast == null} = true OR r.isBreakfast = :isBreakfast) AND " +
					"(:#{#isSnack == null} = true OR r.isSnack = :isSnack) " +
					"ORDER BY r.date DESC";
	@Query(QUERY_COUNT_WITH_FILTERS)
	long countFiltered(@Param("locale") String locale,
					   @Param("minPrepTime") Integer minPrepTime,
					   @Param("maxPrepTime") Integer maxPrepTime,
					   @Param("minCookingTime") Integer minCookingTime,
					   @Param("maxCookingTime") Integer maxCookingTime,
					   @Param("cookingMethodIds") List<Long> cookingMethodIds,
					   @Param("dietIds") List<Long> dietIds,
					   @Param("includeIngredientIds") List<Long> includeIngredientIds,
					   @Param("isBreakfast") Boolean isBreakfast,
					   @Param("isSnack") Boolean isSnack);
	
	long countByIsApproved(Boolean isApproved);
}
