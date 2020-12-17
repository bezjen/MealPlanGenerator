package com.bezjen.whattoeat.filter;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.util.WebUtils;

public class CustomLocaleResolver implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest servletRequest) {
	    Locale locale = servletRequest.getLocale();
	    String languageCode = (String)servletRequest.getSession().getAttribute(LocaleUrlFilter.LOCALE_ATTRIBUTE_NAME);
	    if (languageCode != null) {
	    	locale = new Locale(languageCode);
	    }
	    Cookie cookie = WebUtils.getCookie(servletRequest, "lang");
	    if (cookie != null) {
	    	locale = new Locale(cookie.getValue());
	    }
        return locale;
    }

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
	}
}
