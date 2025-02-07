package com.astrapay.starter.configuration.locale;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LocaleInterceptor implements HandlerInterceptor {

    @Resource(name = "localHolder")
    LocalHolder localHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        if (localeResolver == null) {
            throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
        }

        if (localeResolver instanceof AcceptHeaderLocaleResolver) {
            AcceptHeaderLocaleResolver headerLocaleResolver = (AcceptHeaderLocaleResolver) localeResolver;
            localHolder.setCurrentLocale(headerLocaleResolver.resolveLocale(request));
        } else {
            throw new IllegalStateException("Resolver should be of AcceptHeaderLocaleResolver type");
        }

        return true;
    }
}