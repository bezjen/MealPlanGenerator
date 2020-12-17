package com.bezjen.whattoeat.repository.localized;

import org.springframework.data.repository.CrudRepository;

import com.bezjen.whattoeat.entity.FoodProductCategory;

import java.util.List;

public interface FoodProductCategoryRepository extends CrudRepository<FoodProductCategory, Long> {
    boolean existsByRuLocale(String ruLocale);
    boolean existsByEnLocale(String enLocale);
    List<FoodProductCategory> findByEnLocale(String enLocale);
    List<FoodProductCategory> findByRuLocale(String ruLocale);
    List<FoodProductCategory> findByEnLocaleStartingWithIgnoreCase(String enLocale);
    List<FoodProductCategory> findByRuLocaleStartingWithIgnoreCase(String ruLocale);
}
