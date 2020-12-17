package com.bezjen.whattoeat.service;

import com.bezjen.whattoeat.entity.User;
import com.bezjen.whattoeat.entity.VerificationToken;
import com.bezjen.whattoeat.repository.VerificationTokenRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class VerificationTokenService {
    private int EXP_TIME_IN_MINUTES = 60 * 24;

    private VerificationTokenRepository verificationTokenRepository;

    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public String createVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        Date expiryDate = calculateExpiryDate(EXP_TIME_IN_MINUTES);
        VerificationToken verificationToken = new VerificationToken(user, token, expiryDate);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public VerificationToken getVerificationToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    public VerificationToken updateUserVerificationToken(User user) {
        VerificationToken verificationToken = verificationTokenRepository.findByUserId(user.getId());
        if (verificationToken == null) {
            verificationToken = new VerificationToken();
            verificationToken.setToken(UUID.randomUUID().toString());
            verificationToken.setUser(user);
        } else {
            if (verificationToken.getExpiryDate().getTime() < System.currentTimeMillis()) {
                String token = UUID.randomUUID().toString();
                verificationToken.setToken(token);
            }
        }
        verificationToken.setExpiryDate(calculateExpiryDate(EXP_TIME_IN_MINUTES));
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }
}