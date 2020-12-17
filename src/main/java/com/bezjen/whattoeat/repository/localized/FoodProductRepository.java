package com.bezjen.whattoeat.repository.localized;

import org.springframework.data.repository.CrudRepository;

import com.bezjen.whattoeat.entity.FoodProduct;

import java.util.List;

public interface FoodProductRepository extends CrudRepository<FoodProduct, Long> {
    boolean existsByRuLocale(String ruLocale);
    boolean existsByEnLocale(String enLocale);
    List<FoodProduct> findByEnLocale(String enLocale);
    List<FoodProduct> findByRuLocale(String ruLocale);
    List<FoodProduct> findByEnLocaleStartingWithIgnoreCase(String enLocale);
    List<FoodProduct> findByRuLocaleStartingWithIgnoreCase(String ruLocale);
}
