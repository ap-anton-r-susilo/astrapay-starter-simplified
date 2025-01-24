package com.astrapay.starter.configuration.authentication;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
@Component
public class AppBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @ExceptionHandler(value = {AccessDeniedException.class})
    public void commence(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "PERMISSION DENIED");
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("astrapay");
        super.afterPropertiesSet();
    }

}
