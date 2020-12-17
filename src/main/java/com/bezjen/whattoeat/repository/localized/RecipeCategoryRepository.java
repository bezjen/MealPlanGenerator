package com.bezjen.whattoeat.repository.localized;

import com.bezjen.whattoeat.entity.RecipeCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeCategoryRepository extends CrudRepository<RecipeCategory, Long> {
    boolean existsByRuLocale(String ruLocale);
    boolean existsByEnLocale(String enLocale);
    List<RecipeCategory> findByEnLocale(String enLocale);
    List<RecipeCategory> findByRuLocale(String ruLocale);
    List<RecipeCategory> findByEnLocaleStartingWithIgnoreCase(String enLocale);
    List<RecipeCategory> findByRuLocaleStartingWithIgnoreCase(String ruLocale);
}
