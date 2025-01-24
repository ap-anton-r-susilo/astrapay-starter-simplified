package com.astrapay.starter.controller.advice;

import com.astrapay.starter.dto.AdditionalData;
import com.astrapay.starter.dto.AdditionalDataList;
import com.astrapay.starter.dto.DefaultAttributeError;
import com.astrapay.starter.dto.DetailError;
import com.fasterxml.jackson.core.JsonParseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.validator.engine.HibernateConstraintViolation;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

/**
 * GeneralExceptionAdvice berfungsi sebagai interceptor Exception yang bersifat general yang digunakan oleh banyak project Astrapay
 *
 * @author Raymond Sugiarto
 * @author Astrapay
 * @version 1.8
 * @since 2021-07-25
 */
@RestControllerAdvice()
@Slf4j
@RequiredArgsConstructor
public class GeneralExceptionAdvice {

    private static final String ATTRIBUTE_CODE = "code";

    /**
     * Exception berfungsi sebagai intercept Exception.class.
     * Exception akan membentuk error yang lebih user friendly dengan format <a href="{@see DefaultAttributeError}">DefaultAttributeError</a>
     *
     * @param ex      {@link BindException}
     * @param request {@link HttpServletRequest}
     * @return {@link DefaultAttributeError}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public DefaultAttributeError Exception(Exception ex, HttpServletRequest request) {
        log.error("GeneralExceptionAdvice::Exception: {}", ex);

        DefaultAttributeError defaultError = DefaultAttributeError.builder()
                .timestamp(LocalDateTime.now().toString())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getLocalizedMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .details(new ArrayList<>())
                .build();
        return defaultError;
    }


    /***
     *
     * handleJsonParseException berfungsi sebagai intercept JsonParseException
     * @param ex {@link JsonParseException}
     * @param request {@link HttpServletRequest}
     * @return {@link DefaultAttributeError}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(JsonParseException.class)
    public DefaultAttributeError handleJsonParseException(JsonParseException ex, HttpServletRequest request) {
        log.error("GeneralExceptionAdvice::Exception: {}", ex);

        DefaultAttributeError defaultError = DefaultAttributeError.builder()
                .timestamp(LocalDateTime.now().toString())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getLocalizedMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .details(new ArrayList<>())
                .build();
        return defaultError;
    }

    /**
     * BindException berfungsi sebagai intercept BindException.class.
     * BindException akan membentuk error yang lebih user friendly dengan format <a href="{@see DefaultAttributeError}">DefaultAttributeError</a>
     *
     * @param ex      {@link BindException}
     * @param request {@link HttpServletRequest}
     * @return {@link DefaultAttributeError}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({BindException.class, ConstraintViolationException.class})
    public DefaultAttributeError BindException(BindException ex, HttpServletRequest request) {
        log.error("GeneralExceptionAdvice::" + ex.getClass(), ex);

        BindingResult result = ex.getBindingResult();
        List<ObjectError> objectErrorList = result.getAllErrors();

        List<String> propertyPaths = new ArrayList<>();
        List<DetailError> detailErrorList = objectErrorList.stream().map(error -> {
                    ConstraintViolationImpl<?> constraintViolation = error.unwrap(ConstraintViolationImpl.class);
                    String propertyPath = constraintViolation.getPropertyPath().toString();

                    AdditionalDataList additionalDataList = constraintViolation.getDynamicPayload(AdditionalDataList.class);

                    Optional<List<AdditionalData>> optionalAdditionalData = Optional.ofNullable(additionalDataList)
                            .map(AdditionalDataList::getAdditionalDataList);

                    String errorCode = constraintViolation.getConstraintDescriptor().getAttributes()
                            .getOrDefault(ATTRIBUTE_CODE, error.getCode()).toString();

                    propertyPaths.add(propertyPath);
                    return DetailError.builder()
                            .field(propertyPath)
                            .rejectedValue(constraintViolation.getInvalidValue())
                            .objectName(error.getObjectName())
                            .code(errorCode)
                            .defaultMessage(error.getDefaultMessage())
                            .additionalData(optionalAdditionalData.orElse(null))
                            .build();
                })
                .collect(Collectors.toList());

        return DefaultAttributeError.builder()
                .timestamp(LocalDateTime.now().toString())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Validation failed for " + ex.getObjectName() + "(" + StringUtils.join(propertyPaths, ",") + ")" + ". Error count " + ex.getErrorCount())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .details(detailErrorList)
                .build();
    }


    /***
     *
     * ConstraintViolationException berfungsi sebagau intercept ConstraintViolationException
     * @param ex {@link ConstraintViolationException}
     * @param request {@link HttpServletRequest}
     * @return {@link DefaultAttributeError}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({javax.validation.ConstraintViolationException.class})
    public DefaultAttributeError handleConstraintViolationException(javax.validation.ConstraintViolationException ex, HttpServletRequest request) {
        log.error("GeneralExceptionAdvice::" + ex.getClass(), ex);

        List<DetailError> detailErrors = ex.getConstraintViolations().stream().map(
                error -> {
                    HibernateConstraintViolation<?> dynamicPayload = error.unwrap(HibernateConstraintViolation.class);

                    AdditionalDataList additionalDataList = dynamicPayload.getDynamicPayload(AdditionalDataList.class);

                    Optional<List<AdditionalData>> optionalAdditionalData = Optional.ofNullable(additionalDataList)
                            .map(AdditionalDataList::getAdditionalDataList);

                    String errorCode = error.getConstraintDescriptor().getAttributes().getOrDefault(ATTRIBUTE_CODE,
                            error.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName()).toString();

                    Object invalidError = error.getInvalidValue() instanceof MultipartFile ? ((MultipartFile) error.getInvalidValue()).getOriginalFilename() : error.getInvalidValue();

                    return DetailError.builder()
                            .code(errorCode)
                            .field(
                                    Objects.requireNonNull(StreamSupport.stream(
                                                            error.getPropertyPath().spliterator(), false).
                                                    reduce((first, second) -> second).
                                                    orElse(null)).
                                            toString()
                            )
                            .rejectedValue(invalidError)
                            .defaultMessage(error.getMessage())
                            .additionalData(optionalAdditionalData.orElse(null))
                            .objectName(error.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName())
                            .build();
                }
        ).collect(toList());

        return DefaultAttributeError.builder()
                .timestamp(LocalDateTime.now().toString())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getLocalizedMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .details(detailErrors)
                .build();
    }
}
