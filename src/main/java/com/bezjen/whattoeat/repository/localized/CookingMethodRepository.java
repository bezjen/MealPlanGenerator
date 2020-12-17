package com.bezjen.whattoeat.repository.localized;

import org.springframework.data.repository.CrudRepository;

import com.bezjen.whattoeat.entity.CookingMethod;

import java.util.List;

public interface CookingMethodRepository extends CrudRepository<CookingMethod, Long> {
    boolean existsByRuLocale(String ruLocale);
    boolean existsByEnLocale(String enLocale);
    List<CookingMethod> findByEnLocale(String enLocale);
    List<CookingMethod> findByRuLocale(String ruLocale);
    List<CookingMethod> findByEnLocaleStartingWithIgnoreCase(String enLocale);
    List<CookingMethod> findByRuLocaleStartingWithIgnoreCase(String ruLocale);
}
