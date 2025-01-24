package com.astrapay.starter.fraud.controller.advice;

import com.astrapay.starter.dto.DefaultAttributeError;
import com.astrapay.starter.fraud.exception.FraudSecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;

@RestControllerAdvice()
@Slf4j
@RequiredArgsConstructor
public class FraudExceptionAdvice {
    /**
     * fraudExceptionAdvice berfungsi sebagai intercept FraudSecurityException.class.
     * fraudExceptionAdvice akan membentuk error yang lebih user friendly dengan format <a href="{@see DefaultAttributeError}">DefaultAttributeError</a>
     *
     * @param ex      {@link FraudSecurityException}
     * @param request {@link HttpServletRequest}
     * @return {@link DefaultAttributeError}
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    @ExceptionHandler(FraudSecurityException.class)
    public DefaultAttributeError fraudSecurityHandleException(FraudSecurityException ex, HttpServletRequest request) {

        log.error("FraudSecurityExceptionAdvice::" + ex.getClass(), ex);

        return DefaultAttributeError.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .message(ex.getMessage())
                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now().toString())
                .details(Collections.emptyList())
                .build();
    }
}
