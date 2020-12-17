package com.bezjen.whattoeat.controller;

import com.bezjen.whattoeat.entity.User;
import com.bezjen.whattoeat.exception.LocalizedException;
import com.bezjen.whattoeat.item.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public abstract class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected MessageSource messageSource;

    public BaseController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    protected boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.isAuthenticated();
    }

    protected User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user;
    }

    protected String getMessage(String key) {
        try {
            return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return "";
        }
    }

    protected void addMetadataToModel(Model model, String title, String description, String keywords) {
        model.addAttribute("title", title + " | WhatToEat");
        model.addAttribute("description", description);
        model.addAttribute("keywords", keywords);
    }
    
    protected void addMessageToModel(Model model, MessageType messageType, LocalizedException exception) {
        model.addAttribute(messageType.getName(), getMessage(exception.getKey()));
    }


    protected void addMessageToModel(Model model, MessageType messageType, String key) {
        model.addAttribute(messageType.getName(), getMessage(key));
    }

    protected void fillMetaPart(StringBuilder sb, String part) {
        sb.append(part).append(", ");
    }
}
