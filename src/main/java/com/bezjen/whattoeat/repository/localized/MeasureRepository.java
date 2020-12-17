package com.bezjen.whattoeat.repository.localized;

import org.springframework.data.repository.CrudRepository;

import com.bezjen.whattoeat.entity.Measure;

import java.util.List;

public interface MeasureRepository extends CrudRepository<Measure, Long> {
    boolean existsByRuLocale(String ruLocale);
    boolean existsByEnLocale(String enLocale);
    List<Measure> findByEnLocale(String enLocale);
    List<Measure> findByRuLocale(String ruLocale);
    List<Measure> findByEnLocaleStartingWithIgnoreCase(String enLocale);
    List<Measure> findByRuLocaleStartingWithIgnoreCase(String ruLocale);
}