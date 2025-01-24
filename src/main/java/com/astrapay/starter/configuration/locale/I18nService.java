package com.astrapay.starter.configuration.locale;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@RequiredArgsConstructor
public class I18nService {

    private final MessageSource messageSource;

    @Resource(name = "localHolder")
    LocalHolder localHolder;

    public String getMessage(String code) {
        return messageSource.getMessage(code, null, localHolder.getCurrentLocale());
    }
}