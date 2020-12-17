package com.bezjen.whattoeat.service;

import com.bezjen.whattoeat.entity.User;
import com.bezjen.whattoeat.entity.VerificationToken;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Locale;

@Service
public class EmailConfirmationService {
    private VerificationTokenService verificationTokenService;
    private EmailSenderService emailSenderService;
    private MessageSource messageSource;

    public EmailConfirmationService(
            VerificationTokenService verificationTokenService,
            EmailSenderService emailSenderService,
            MessageSource messageSource
    ) {
        this.verificationTokenService = verificationTokenService;
        this.emailSenderService = emailSenderService;
        this.messageSource = messageSource;
    }

    public void sendConfirmationEmail(User user, String serverName, Locale locale) throws MessagingException {
        String token = verificationTokenService.createVerificationToken(user);
        sendConfirmationEmail(user.getEmail(), serverName, token, locale);
    }

    public void reSendConfirmationEmail(User user, String serverName, Locale locale) throws MessagingException {
        VerificationToken verificationToken = verificationTokenService.updateUserVerificationToken(user);
        sendConfirmationEmail(user.getEmail(), serverName, verificationToken.getToken(), locale);
    }

    private void sendConfirmationEmail(String to, String serverName, String token, Locale locale)
            throws MessagingException {
        String subject = messageSource.getMessage("messages.signup.confirm.subject", null, locale);

        StringBuilder confirmationMessage =
                new StringBuilder(messageSource.getMessage("messages.signup.confirm", null, locale))
                        .append("<a href='https://")
                            .append(serverName).append("/signup/confirm?token=").append(token)
                        .append("'>")
                        .append(messageSource.getMessage("messages.signup.confirm.link", null, locale))
                        .append("</a>");

        emailSenderService.sendMimeMessage(to, subject, confirmationMessage.toString());
    }
}