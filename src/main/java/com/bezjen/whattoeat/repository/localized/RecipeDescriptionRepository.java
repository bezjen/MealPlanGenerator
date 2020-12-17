package com.bezjen.whattoeat.repository.localized;

import com.bezjen.whattoeat.entity.RecipeDescription;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeDescriptionRepository extends CrudRepository<RecipeDescription, Long> {
	boolean existsByRuLocale(String ruLocale);
	boolean existsByEnLocale(String enLocale);
	List<RecipeDescription> findByEnLocale(String enLocale);
	List<RecipeDescription> findByRuLocale(String ruLocale);
	List<RecipeDescription> findByEnLocaleStartingWithIgnoreCase(String enLocale);
	List<RecipeDescription> findByRuLocaleStartingWithIgnoreCase(String ruLocale);
}
