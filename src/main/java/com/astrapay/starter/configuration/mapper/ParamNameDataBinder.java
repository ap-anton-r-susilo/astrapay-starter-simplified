package com.astrapay.starter.configuration.mapper;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.ServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * ParamNameDataBinder
 *
 * @author  Fawwaazrahman Arandhana W
 * @author  Astrapay
 * @version 1.12
 * @since   2021-09-01
 */
public class ParamNameDataBinder extends ExtendedServletRequestDataBinder {

    private final Map<String, String> paramMappings;

    /**
     * Set parameter mapping for target class
     *
     * @param target
     * @param objectName
     * @param paramMappings
     */
    public ParamNameDataBinder(Object target, String objectName, Map<String, String> paramMappings) {
        super(target, objectName);
        this.paramMappings = paramMappings;
    }

    /**
     * Set param binding
     *
     * @param mutablePropertyValues
     * @param request
     */
    @Override
    protected void addBindValues(MutablePropertyValues mutablePropertyValues, ServletRequest request) {
        super.addBindValues(mutablePropertyValues, request);
        for (Map.Entry<String, String> entry : paramMappings.entrySet()) {
            String paramName = entry.getKey(); // incoming request param
            String fieldName = entry.getValue(); // field name in dto
            if (mutablePropertyValues.contains(paramName)) {
                mutablePropertyValues.add(fieldName, Objects.requireNonNull(mutablePropertyValues.getPropertyValue(paramName)).getValue());
            }
        }
    }

}
