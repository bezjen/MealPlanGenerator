package com.bezjen.whattoeat.repository.localized;

import com.bezjen.whattoeat.entity.RecipeStep;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeStepRepository extends CrudRepository<RecipeStep, Long> {
	boolean existsByRuLocale(String ruLocale);
	boolean existsByEnLocale(String enLocale);
	List<RecipeStep> findByEnLocale(String enLocale);
	List<RecipeStep> findByRuLocale(String ruLocale);
	List<RecipeStep> findByEnLocaleStartingWithIgnoreCase(String enLocale);
	List<RecipeStep> findByRuLocaleStartingWithIgnoreCase(String ruLocale);
}
