package com.bezjen.whattoeat.repository.localized;

import org.springframework.data.repository.CrudRepository;

import com.bezjen.whattoeat.entity.Diet;

import java.util.List;

public interface DietRepository extends CrudRepository<Diet, Long> {
    boolean existsByRuLocale(String ruLocale);
    boolean existsByEnLocale(String enLocale);
    List<Diet> findByEnLocale(String enLocale);
    List<Diet> findByRuLocale(String ruLocale);
    List<Diet> findByEnLocaleStartingWithIgnoreCase(String enLocale);
    List<Diet> findByRuLocaleStartingWithIgnoreCase(String ruLocale);
}
