package com.bezjen.whattoeat.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.WebUtils;

public class LocaleUrlFilter implements Filter  {
    public static final String LOCALE_ATTRIBUTE_NAME = LocaleAttributeChangeInterceptor.class.getName() + ".LOCALE";
    private static final String[] AVAILABLE_LOCALES = new String[] {"en", "ru"};
	
	private boolean isLocale(String locale) {
	    for(String item : AVAILABLE_LOCALES) {
	    	if (item.equals(locale)) {
	    		return true;
	    	}
	    }
	    return false;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURI().substring(request.getContextPath().length());
        String[] variables = url.split("/");

        if (variables.length > 1 && isLocale(variables[1])) {
            request.getSession().setAttribute(LOCALE_ATTRIBUTE_NAME, variables[1]);
            request.setAttribute(LOCALE_ATTRIBUTE_NAME, variables[1]);
            Cookie cookie = WebUtils.getCookie(request, "lang");
    	    if (cookie != null) {
    	    	cookie.setValue(variables[1]);
    	    } else {
    	    	cookie = new Cookie("lang", variables[1]);
    	    }
    	    cookie.setMaxAge(-1);
            cookie.setPath("/");
    	    response.addCookie(cookie);
            String newUrl = StringUtils.removeStart(url, '/' + variables[1]);
            RequestDispatcher dispatcher = request.getRequestDispatcher(newUrl);
            dispatcher.forward(request, response);
        } else {
			Cookie cookie = WebUtils.getCookie(request, "lang");
			if (cookie == null) {
				Locale locale = servletRequest.getLocale();
				cookie = new Cookie("lang", locale.getLanguage());
				response.addCookie(cookie);
			}
            chain.doFilter(request, response);
        }
	}

}
