package com.bezjen.whattoeat.repository.localized;

import org.springframework.data.repository.CrudRepository;

import com.bezjen.whattoeat.entity.FoodProductCategoryGroup;

import java.util.List;

public interface FoodProductCategoryGroupRepository extends CrudRepository<FoodProductCategoryGroup, Long> {
    boolean existsByRuLocale(String ruLocale);
    boolean existsByEnLocale(String enLocale);
    List<FoodProductCategoryGroup> findByEnLocale(String enLocale);
    List<FoodProductCategoryGroup> findByRuLocale(String ruLocale);
    List<FoodProductCategoryGroup> findByEnLocaleStartingWithIgnoreCase(String enLocale);
    List<FoodProductCategoryGroup> findByRuLocaleStartingWithIgnoreCase(String ruLocale);
}
