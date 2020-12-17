package com.bezjen.whattoeat.repository;

import com.bezjen.whattoeat.entity.VerificationToken;
import org.springframework.data.repository.CrudRepository;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
    VerificationToken findByUserId(Long userId);
}
