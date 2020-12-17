package com.bezjen.whattoeat.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service
public class EmailSenderService {
    @Value("${spring.mail.username}")
    private String from;

    private VerificationTokenService verificationTokenService;
    private JavaMailSender javaMailSender;

    public EmailSenderService(
            VerificationTokenService verificationTokenService,
            JavaMailSender javaMailSender
    ) {
       this.verificationTokenService = verificationTokenService;
       this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMailMessage(String to, String subject, String message) {
        Date sentDate = new Date();
        sendSimpleMailMessage(to, subject, message, sentDate);
    }

    public void sendSimpleMailMessage(String to, String subject, String message, Date sentDate) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSentDate(sentDate);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        javaMailSender.send(simpleMailMessage);
    }

    public void sendMimeMessage(String to, String subject, String message) throws MessagingException {
        MimeMessage email = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(email, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(message, true);
        javaMailSender.send(email);
    }

    public String getFrom() {
        return from;
    }
}