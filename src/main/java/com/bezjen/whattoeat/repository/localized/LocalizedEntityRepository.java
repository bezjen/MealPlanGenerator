package com.bezjen.whattoeat.repository.localized;

import org.springframework.data.repository.CrudRepository;

import com.bezjen.whattoeat.entity.LocalizedEntity;

import java.util.List;

public interface LocalizedEntityRepository extends CrudRepository<LocalizedEntity, Long> {
    boolean existsByRuLocale(String ruLocale);
    boolean existsByEnLocale(String enLocale);
    List<LocalizedEntity> findByEnLocale(String enLocale);
    List<LocalizedEntity> findByRuLocale(String ruLocale);
    List<LocalizedEntity> findByEnLocaleStartingWithIgnoreCase(String enLocale);
    List<LocalizedEntity> findByRuLocaleStartingWithIgnoreCase(String ruLocale);
}
