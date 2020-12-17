package com.bezjen.whattoeat.service.listener;

import com.bezjen.whattoeat.event.OnRegistrationCompleteEvent;
import com.bezjen.whattoeat.service.EmailConfirmationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    private final static Logger LOGGER = LoggerFactory.getLogger(RegistrationListener.class);
    @Autowired
    private EmailConfirmationService emailConfirmationService;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        try {
            emailConfirmationService.sendConfirmationEmail(event.getUser(), event.getAppUrl(), event.getLocale());
        } catch (MessagingException e) {
            LOGGER.error("", e);
        }
    }
}
